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

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;

import io.github.photowey.plugin4j.plugin.core.exception.PluginException;
import io.github.photowey.plugin4j.plugin.manager.session.PluginSession;

/**
 * {@code PluginManager} is an interface that defines the contract for managing plugins.
 * It provides methods for discovering plugins and acquiring plugin sessions.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
public interface PluginManager extends Closeable {

    /**
     * Discover plugins
     *
     * @throws Exception when an error occurs during plugin discovery
     */
    void discover() throws Exception;

    /**
     * Acquire a plugin session
     *
     * @param name    the plugin name
     * @param version the plugin version
     * @return PluginSession the plugin session object
     * @throws PluginException when acquiring plugin session fails
     */
    default PluginSession acquire(String name, String version) throws PluginException {
        return this.acquire(name, version, new HashMap<>(0));
    }

    /**
     * Acquire a plugin session
     *
     * @param name    the plugin name
     * @param version the plugin version
     * @param props   the plugin properties configuration
     * @return PluginSession the plugin session object
     * @throws PluginException when acquiring plugin session fails
     */
    PluginSession acquire(String name, String version, Map<String, Object> props) throws PluginException;
}
