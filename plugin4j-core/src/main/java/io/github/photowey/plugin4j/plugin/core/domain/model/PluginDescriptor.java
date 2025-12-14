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
import java.net.URL;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * {@code PluginDescriptor}.
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
public class PluginDescriptor implements Serializable {

    private static final long serialVersionUID = 8315175190110602345L;

    // plugin.json
    // plugin.yml | plugin.yaml (Unsupported now.)

    /**
     * <pre>
     * {
     *     "metadata": {
     *         "name": "echo",
     *         "version": "1.0.0",
     *         "description": "Echo message plugin",
     *         "extensions": {}
     *     },
     *     "className": "io.github.photowey.plugin4j.plugin.manager.samples.EchoPlugin",
     *     "capabilities": [
     *         "sample",
     *         "echo"
     *     ],
     *     "classpath": []
     * }
     * </pre>
     */
    private Metadata metadata;
    /**
     * The fully-qualified class name of the plugin's entry point.
     */
    private String className;

    /**
     * The capabilities of the plugin.
     *
     * <p>
     * Each capability represents a specific functionality or feature provided by the plugin.
     * The capabilities can be used to query and discover the capabilities of the plugin.
     */
    private List<String> capabilities;
    private List<URL> classpath;

    // ----------------------------------------------------------------

    public Metadata getMetadata() {
        return metadata;
    }

    public String getClassName() {
        return className;
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public List<URL> getClasspath() {
        return classpath;
    }
}
