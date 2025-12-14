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

    public static PluginException invalidConfig(String message) {
        return new PluginException("CONFIG_ERROR", message);
    }

    public static PluginException execError(String message, Throwable cause) {
        return new PluginException("EXEC_ERROR", message, cause);
    }

    // ----------------------------------------------------------------

    public String code() {
        return this.getCode();
    }
}
