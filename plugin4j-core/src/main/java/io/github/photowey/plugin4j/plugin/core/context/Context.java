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

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Map;

import org.slf4j.Logger;

/**
 * {@code Context}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
public interface Context extends Serializable {

    /**
     * Logger dedicated to the plugin; it is recommended that its prefix be {@code plugin.<name>}.
     *
     * @return the logger
     */
    Logger getLogger();

    /**
     * Temporary directory for the plugin to store runtime files.
     *
     * @return the temporary directory
     */
    Path getTempDir();

    /**
     * Class loader used to load the plugin.
     *
     * @return the class loader
     */
    ClassLoader getClassLoader();

    /**
     * Extension context fields, allowing the host to pass through custom capabilities transparently.
     *
     * @return extension metadata
     */
    Map<String, Object> getMetadata();

    default Logger logger() {
        return this.getLogger();
    }

    default Path tempDir() {
        return this.getTempDir();
    }

    default ClassLoader classLoader() {
        return this.getClassLoader();
    }

    default Map<String, Object> metadata() {
        return this.getMetadata();
    }
}
