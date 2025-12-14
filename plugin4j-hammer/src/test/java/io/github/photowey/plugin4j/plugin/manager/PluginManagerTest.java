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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.photowey.plugin4j.plugin.core.converter.JsonConverter;
import io.github.photowey.plugin4j.plugin.core.domain.model.PluginResult;
import io.github.photowey.plugin4j.plugin.core.util.Jsons;
import io.github.photowey.plugin4j.plugin.manager.core.json.JacksonJsonConverter;
import io.github.photowey.plugin4j.plugin.manager.samples.EchoPlugin;
import io.github.photowey.plugin4j.plugin.manager.session.PluginSession;

/**
 * {@code PluginManagerTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
class PluginManagerTest {

    private ExecutorService executor;

    @BeforeEach
    void setUp() {
        this.executor = Executors.newFixedThreadPool(2);
    }

    @AfterEach
    void tearDown() {
        executor.shutdown();
    }

    @Test
    void testLoadAndExecuteEchoPlugin() throws Exception {
        Path pluginsRoot = Files.createTempDirectory("plugins-root-");
        Path echoDir = Files.createDirectories(pluginsRoot.resolve("echo"));
        Path jarPath = echoDir.resolve("echo-1.0.0.jar");

        this.init(jarPath, echoDir);

        JsonConverter converter = new JacksonJsonConverter();
        Jsons.register(converter);

        PluginManager manager = new DefaultPluginManager(pluginsRoot, this.executor);
        manager.discover();

        Map<String, Object> props = new HashMap<>();
        props.put("message", "hello");

        try (PluginSession session = manager.acquire("echo", "1.0.0", props)) {
            PluginResult result = session.execute();
            Assertions.assertTrue(result.determineIfSuccessful(), "plugin should execute successfully");
            Assertions.assertEquals("hello", result.metrics().get("message"));
        } finally {
            manager.close();
        }
    }

    private void init(Path jarPath, Path echoDir) throws IOException {
        this.packPluginClass(EchoPlugin.class, jarPath);

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("dev/json/plugin.json");
        Assertions.assertNotNull(in);
        String json = this.readInputStreamWithBufferedReader(in);
        Files.writeString(echoDir.resolve("plugin.json"), json, StandardCharsets.UTF_8);
    }

    private void packPluginClass(Class<?> clazz, Path jarPath) throws IOException {
        String entryName = clazz.getName().replace('.', '/') + ".class";
        try (JarOutputStream jos = new JarOutputStream(
            new BufferedOutputStream(new FileOutputStream(jarPath.toFile())))) {
            JarEntry entry = new JarEntry(entryName);
            jos.putNextEntry(entry);
            try (InputStream is = clazz.getClassLoader().getResourceAsStream(entryName)) {
                if (is == null) {
                    throw new IllegalStateException("Class bytes not found for " + clazz.getName());
                }

                is.transferTo(jos);
            }

            jos.closeEntry();
        }
    }

    @Deprecated
    public String read(InputStream in) {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = in.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            return buffer.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read InputStream", e);
        }
    }

    public String readInputStreamWithBufferedReader(InputStream in) {
        StringBuilder buf = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                buf.append(line).append("\n");
            }

            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read InputStream", e);
        }

        return buf.toString();
    }
}
