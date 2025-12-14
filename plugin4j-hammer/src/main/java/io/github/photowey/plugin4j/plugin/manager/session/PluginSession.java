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
