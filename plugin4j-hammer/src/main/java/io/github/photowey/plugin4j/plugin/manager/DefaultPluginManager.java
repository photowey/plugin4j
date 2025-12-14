/*
 * Copyright (c) 2025-present The Plugin4j Authors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.photowey.plugin4j.plugin.manager;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.photowey.plugin4j.plugin.api.Plugin;
import io.github.photowey.plugin4j.plugin.core.constant.PluginConstants;
import io.github.photowey.plugin4j.plugin.core.context.DefaultPluginContext;
import io.github.photowey.plugin4j.plugin.core.context.PluginContext;
import io.github.photowey.plugin4j.plugin.core.domain.model.PluginDescriptor;
import io.github.photowey.plugin4j.plugin.core.exception.PluginException;
import io.github.photowey.plugin4j.plugin.core.util.Jsons;
import io.github.photowey.plugin4j.plugin.manager.holder.PluginHolder;
import io.github.photowey.plugin4j.plugin.manager.session.PluginSession;

/**
 * {@code DefaultPluginManager}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
public class DefaultPluginManager implements PluginManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPluginManager.class);

    private static final String PLUGIN_JSON = PluginConstants.Configuration.PLUGIN_JSON;

    private final Path pluginsDir;
    private final ExecutorService executor;
    private final Map<String, PluginHolder> registry = new ConcurrentHashMap<>();

    public DefaultPluginManager(Path pluginsDir, ExecutorService executor) {
        this.pluginsDir = pluginsDir;
        this.executor = executor;
    }

    @Override
    public void discover() throws Exception {
        if (!Files.isDirectory(this.pluginsDir)) {
            throw new IllegalArgumentException("pluginsDir not exists: " + this.pluginsDir);
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.pluginsDir)) {
            for (Path path : stream) {
                if (Files.isDirectory(path)) {
                    this.loadFromDir(path);
                } else if (path.toString().endsWith(PluginConstants.Jar.SUFFIX)) {
                    this.loadFromJarDirect(path);
                }

                // Skip...
            }
        }
    }

    @Override
    public PluginSession acquire(String name, String version, Map<String, Object> props) throws PluginException {
        String key = this.key(name, version);
        PluginHolder holder = this.registry.get(key);
        if (Objects.isNull(holder)) {
            throw new PluginException("NOT_FOUND", "Plugin not found: " + key);
        }

        PluginContext context = new DefaultPluginContext(name, holder.classLoader(), this.executor);
        holder.plugin().init(context, props);

        return new PluginSession(holder.plugin(), context, holder.descriptor());
    }

    @Override
    public void close() {
        this.registry.values().forEach(PluginHolder::closeQuietly);
        this.registry.clear();
    }

    private void loadFromDir(Path dir) throws Exception {
        Path json = dir.resolve(PLUGIN_JSON);
        if (!Files.exists(json)) {
            LOGGER.warn("Skip {}, missing {}", dir, PLUGIN_JSON);
            return;
        }

        PluginDescriptor descriptor = Jsons.read(json, PluginDescriptor.class);
        this.checkProperties(descriptor, json);

        List<URL> urls = this.collectUrls(dir);
        descriptor.classpath(urls);

        URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[0]), this.getClass().getClassLoader());
        Plugin plugin = this.instantiate(descriptor, classLoader);
        String key = this.key(descriptor.metadata().name(), descriptor.metadata().version());

        this.registry.put(key, new PluginHolder(plugin, descriptor, classLoader));
        LOGGER.info("Loaded plugins: {} ({})", key, descriptor.metadata().description());
    }

    private void loadFromJarDirect(Path jar) throws Exception {
        Path json = this.findSiblingJson(jar);
        if (Objects.isNull(json)) {
            throw new IllegalArgumentException(
                String.format("Jar requires %s for className: %s", PLUGIN_JSON, jar)
            );
        }

        PluginDescriptor descriptor = Jsons.read(json, PluginDescriptor.class);
        this.checkProperties(descriptor, json);

        List<URL> urls = Collections.singletonList(jar.toUri().toURL());
        descriptor.classpath(urls);

        URLClassLoader classLoader = new URLClassLoader(new URL[] {jar.toUri().toURL()}, getClass().getClassLoader());
        Plugin plugin = this.instantiate(descriptor, classLoader);
        String key = this.key(descriptor.metadata().name(), descriptor.metadata().version());
        this.registry.put(key, new PluginHolder(plugin, descriptor, classLoader));
        LOGGER.info("Loaded plugin: {} ({})", key, descriptor.metadata().description());
    }

    private List<URL> collectUrls(Path dir) throws IOException {
        List<URL> urls = new ArrayList<>();
        try (DirectoryStream<Path> jarStream = Files.newDirectoryStream(dir, PluginConstants.Jar.PATTERN)) {
            for (Path jar : jarStream) {
                urls.add(jar.toUri().toURL());
            }
        }
        Path libs = dir.resolve(PluginConstants.Attribute.LIBS);
        if (Files.isDirectory(libs)) {
            try (DirectoryStream<Path> jarStream = Files.newDirectoryStream(libs, PluginConstants.Jar.PATTERN)) {
                for (Path jar : jarStream) {
                    urls.add(jar.toUri().toURL());
                }
            }
        }

        return urls;
    }

    private Plugin instantiate(PluginDescriptor descriptor, ClassLoader classLoader) throws Exception {
        Class<?> clazz = Class.forName(descriptor.className(), true, classLoader);
        this.checkPlugin(descriptor, clazz);

        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);

        return (Plugin) constructor.newInstance();
    }

    private Path findSiblingJson(Path jar) {
        Path parent = jar.getParent();
        if (Objects.isNull(parent)) {
            return null;
        }

        Path json = parent.resolve(PLUGIN_JSON);
        return Files.exists(json) ? json : null;
    }

    private String key(String name, String version) {
        return name + ":" + version;
    }

    // ----------------------------------------------------------------

    private void checkPlugin(PluginDescriptor descriptor, Class<?> clazz) {
        if (!Plugin.class.isAssignableFrom(clazz)) {
            throw new PluginException("CLASS_NOT_PLUGIN",
                "Class %s not implement Plugin",
                descriptor.className()
            );
        }
    }

    private void checkProperties(PluginDescriptor descriptor, Path json) {
        this.requireName(descriptor, json);
        this.requireVersion(descriptor, json);
        this.requireClassName(descriptor, json);
    }

    private void requireName(PluginDescriptor descriptor, Path json) {
        String name = descriptor.metadata().name();
        if (isBlank(name)) {
            throw new IllegalArgumentException("name required in " + json);
        }
    }

    private void requireVersion(PluginDescriptor descriptor, Path json) {
        String version = descriptor.metadata().version();
        if (isBlank(version)) {
            throw new IllegalArgumentException("version required in " + json);
        }
    }

    private void requireClassName(PluginDescriptor descriptor, Path json) {
        String className = descriptor.className();
        if (isBlank(className)) {
            throw new IllegalArgumentException("className required in " + json);
        }
    }

    // ----------------------------------------------------------------

    private static boolean isBlank(String txt) {
        return Objects.isNull(txt) || txt.trim().isEmpty();
    }
}
