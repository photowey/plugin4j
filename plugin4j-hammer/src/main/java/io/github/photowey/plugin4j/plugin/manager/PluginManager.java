package io.github.photowey.plugin4j.plugin.manager;

import java.io.Closeable;
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
     * @param props   the plugin properties configuration
     * @return PluginSession the plugin session object
     * @throws PluginException when acquiring plugin session fails
     */
    PluginSession acquire(String name, String version, Map<String, Object> props) throws PluginException;
}
