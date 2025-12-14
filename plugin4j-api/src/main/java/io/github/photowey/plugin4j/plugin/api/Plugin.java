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
package io.github.photowey.plugin4j.plugin.api;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import io.github.photowey.plugin4j.plugin.core.context.PluginContext;
import io.github.photowey.plugin4j.plugin.core.domain.model.Metadata;
import io.github.photowey.plugin4j.plugin.core.domain.model.PluginResult;
import io.github.photowey.plugin4j.plugin.core.domain.ordered.Ordered;
import io.github.photowey.plugin4j.plugin.core.exception.PluginException;

/**
 * {@code Plugin}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
public interface Plugin extends Ordered, Serializable {

    /**
     * Returns the metadata associated with this plugin.
     *
     * <p>
     * Metadata may include author information, dependencies, capabilities,
     * or other descriptive attributes that support plugin management and discovery.
     *
     * @return a non-null {@link Metadata} object containing plugin details
     */
    Metadata metadata();

    /**
     * Initialize the plugin with the given context and properties.
     *
     * <p>
     * This method is called once when the plugin is loaded, allowing it to
     * perform any necessary setup operations before execution.
     *
     * @param context the plugin context providing access to the runtime environment
     */
    default void init(PluginContext context) {
        this.init(context, new HashMap<>(0));
    }

    /**
     * Initialize the plugin with the given context and properties.
     *
     * <p>
     * This method is called once when the plugin is loaded, allowing it to
     * perform any necessary setup operations before execution.
     *
     * @param context the plugin context providing access to the runtime environment
     * @param props   configuration properties for the plugin initialization
     */
    default void init(PluginContext context, Map<String, Object> props) {

    }

    /**
     * Execute the plugin's main logic using the provided context.
     *
     * <p>
     * This method contains the core functionality of the plugin and is
     * invoked when the plugin is triggered for execution.
     *
     * @param context the plugin context providing access to the runtime environment
     * @return a {@link PluginResult} representing the outcome of the execution
     * @throws PluginException if an error occurs during plugin execution
     */
    PluginResult execute(PluginContext context) throws PluginException;

    /**
     * Clean up resources used by the plugin.
     *
     * <p>
     * This method is called when the plugin is being unloaded or shut down,
     * allowing it to release any held resources.
     *
     * @throws IOException if an I/O error occurs during cleanup
     */
    default void close() throws IOException {

    }
}
