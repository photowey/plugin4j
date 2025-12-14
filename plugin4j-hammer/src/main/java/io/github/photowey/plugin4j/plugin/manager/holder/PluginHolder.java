package io.github.photowey.plugin4j.plugin.manager.holder;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLClassLoader;

import io.github.photowey.plugin4j.plugin.api.Plugin;
import io.github.photowey.plugin4j.plugin.core.domain.model.PluginDescriptor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * {@code PluginHolder}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
@Data
@AllArgsConstructor
@Accessors(fluent = true)
public class PluginHolder implements Serializable {

    private static final long serialVersionUID = 8715589897147656509L;

    private final Plugin plugin;
    private final PluginDescriptor descriptor;
    private final URLClassLoader classLoader;

    public void closeQuietly() {
        try {
            this.plugin.close();
        } catch (Exception ignored) {
            // ignore
        }
        try {
            this.classLoader.close();
        } catch (IOException ignored) {
            // ignore
        }
    }
}
