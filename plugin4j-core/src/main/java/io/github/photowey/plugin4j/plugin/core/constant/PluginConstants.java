package io.github.photowey.plugin4j.plugin.core.constant;

/**
 * {@code PluginConstants}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
public interface PluginConstants {

    interface Configuration {
        String PLUGIN_JSON = "plugin.json";
    }

    interface Jar {
        String SUFFIX = ".jar";
        String PATTERN = "*.jar";
    }

    interface Attribute {
        String LIBS = "libs";
    }
}
