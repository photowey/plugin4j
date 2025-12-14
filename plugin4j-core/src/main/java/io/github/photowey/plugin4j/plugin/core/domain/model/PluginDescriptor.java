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
     *   "metadata": {
     *     "name": "echo",
     *     "version": "1.0.0",
     *     "description": "Echo message plugin"
     *   },
     *   "className": "io.github.photowey.plugin.samples.EchoPlugin",
     *   "capabilities": [
     *     "sample",
     *     "echo"
     *   ],
     *   "classpath": []
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
}
