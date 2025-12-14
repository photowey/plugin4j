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
package io.github.photowey.plugin4j.plugin.core.domain.model;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * {@code Metadata}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class Metadata implements Serializable {

    private static final long serialVersionUID = 4825831164250101797L;

    /**
     * The plugin name, which must be globally unique.
     *
     * <p>
     * The name should be consistent across different versions of the same plugin
     * and is typically used for identification and lookup purposes.
     */
    private String name;
    /**
     * The version of this plugin instance.
     *
     * <p>
     * The version string should follow semantic versioning (e.g., "1.0.0")
     * or another consistent format agreed upon by the plugin ecosystem.
     */
    private String version;
    /**
     * A brief description of the plugin.
     */
    private String description;
    /**
     * Additional metadata about the plugin.
     */
    private Map<String, Object> extensions;

    // ----------------------------------------------------------------

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Object> getExtensions() {
        return extensions;
    }
}
