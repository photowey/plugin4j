package io.github.photowey.plugin4j.plugin.manager.session;

import java.io.Closeable;
import java.io.Serializable;

import io.github.photowey.plugin4j.plugin.api.Plugin;
import io.github.photowey.plugin4j.plugin.core.context.PluginContext;
import io.github.photowey.plugin4j.plugin.core.domain.model.PluginDescriptor;
import io.github.photowey.plugin4j.plugin.core.domain.model.PluginResult;
import io.github.photowey.plugin4j.plugin.core.exception.PluginException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * {@code PluginSession}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
@Data
@AllArgsConstructor
@Accessors(fluent = true)
public class PluginSession implements Serializable, Closeable {

    private static final long serialVersionUID = 3735013393104298368L;

    private final Plugin plugin;
    private final PluginContext context;

    private final PluginDescriptor descriptor;

    public PluginResult execute() throws PluginException {
        return this.plugin.execute(this.context);
    }

    @Override
    public void close() {
        try {
            this.plugin.close();
        } catch (Exception ignored) {
            // ignore
        }
    }
}
