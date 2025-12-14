package io.github.photowey.plugin4j.plugin.core.context;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * {@code DefaultPluginContext}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
@Data
@AllArgsConstructor
public class DefaultPluginContext implements PluginContext {

    private static final long serialVersionUID = 240042620575524587L;

    private final Logger logger;
    private final Path tempDir;
    private final ClassLoader classLoader;

    private final ExecutorService executor;

    private final Map<String, Object> metadata = new ConcurrentHashMap<>();

    public DefaultPluginContext(String name, ClassLoader classLoader, ExecutorService executor) {
        this.classLoader = classLoader;
        this.executor = executor;
        this.logger = LoggerFactory.getLogger("plugin." + name);
        try {
            this.tempDir = Files.createTempDirectory("plugin-" + name + "-");
        } catch (IOException e) {
            throw new IllegalStateException("Cannot create temp dir", e);
        }
    }
}
