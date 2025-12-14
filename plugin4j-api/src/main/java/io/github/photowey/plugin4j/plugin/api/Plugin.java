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

import java.io.Serializable;

import io.github.photowey.plugin4j.plugin.core.domain.model.Metadata;
import io.github.photowey.plugin4j.plugin.core.domain.ordered.Ordered;

/**
 * {@code Plugin}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
public interface Plugin extends Ordered, Serializable {

    /**
     * Returns the unique name of this plugin.
     *
     * <p>
     * The name should be consistent across different versions of the same plugin
     * and is typically used for identification and lookup purposes.
     *
     * @return the non-null, non-empty name of the plugin
     */
    String name();

    /**
     * Returns the version of this plugin instance.
     *
     * <p>
     * The version string should follow semantic versioning (e.g., "1.2.0")
     * or another consistent format agreed upon by the plugin ecosystem.
     *
     * @return the non-null, non-empty version string of the plugin
     */
    String version();

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
}
