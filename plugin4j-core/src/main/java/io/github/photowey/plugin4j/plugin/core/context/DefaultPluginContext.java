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
package io.github.photowey.plugin4j.plugin.core.context;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * {@code DefaultPluginContext}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
@Data
@AllArgsConstructor
public class DefaultPluginContext implements PluginContext {

    private static final long serialVersionUID = 240042620575524587L;

    private final Logger logger;
    private final Path tempDir;
    private final ClassLoader classLoader;

    private final ExecutorService executor;

    private final Map<String, Object> metadata = new ConcurrentHashMap<>();

    public DefaultPluginContext(String name, ClassLoader classLoader, ExecutorService executor) {
        this.classLoader = classLoader;
        this.executor = executor;
        this.logger = LoggerFactory.getLogger("plugin." + name);
        try {
            this.tempDir = Files.createTempDirectory("plugin-" + name + "-");
        } catch (IOException e) {
            throw new IllegalStateException("Cannot create temp dir", e);
        }
    }
}
