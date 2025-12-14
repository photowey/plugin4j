package io.github.photowey.plugin4j.plugin.core.exception;

import lombok.Getter;

/**
 * {@code PluginException}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
public class PluginException extends RuntimeException {

    @Getter
    private final String code;

    public PluginException(String code, String message, Object... args) {
        super(String.format(message, args));
        this.code = code;
    }

    public PluginException(Throwable cause, String code, String message, Object... args) {
        super(String.format(message, args), cause);
        this.code = code;
    }

    public PluginException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public PluginException(
        Throwable cause,
        boolean enableSuppression, boolean writableStackTrace,
        String code, String message, Object... args) {
        super(String.format(message, args), cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    // ----------------------------------------------------------------

    public String code() {
        return this.getCode();
    }
}
