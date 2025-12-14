package io.github.photowey.plugin4j.plugin.core.domain.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * {@code PluginResult}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
@Data
@Builder
@NoArgsConstructor
@Accessors(fluent = true)
public class PluginResult implements Serializable {

    private static final long serialVersionUID = -2851970065537578012L;

    private boolean success;
    private String code;
    private String message;
    private Map<String, Object> metrics;

    public PluginResult(
        boolean success,
        String code,
        String message) {
        this(success, code, message, null);
    }

    public PluginResult(
        boolean success,
        String code,
        String message,
        Map<String, Object> metrics) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.metrics = Objects.isNull(metrics) ? Collections.emptyMap() : Collections.unmodifiableMap(metrics);
    }

    public static PluginResult ok(Map<String, Object> metrics) {
        return new PluginResult(true, "OK", "OK", metrics);
    }

    public static PluginResult fail(String code, String message) {
        return new PluginResult(false, code, message, Collections.emptyMap());
    }

    public boolean determineIfSuccessful() {
        return this.success();
    }
}
