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
package io.github.photowey.plugin4j.plugin.manager.samples;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.github.photowey.plugin4j.plugin.api.Plugin;
import io.github.photowey.plugin4j.plugin.core.context.PluginContext;
import io.github.photowey.plugin4j.plugin.core.domain.model.Metadata;
import io.github.photowey.plugin4j.plugin.core.domain.model.PluginResult;
import io.github.photowey.plugin4j.plugin.core.exception.PluginException;

/**
 * {@code EchoPlugin}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
public class EchoPlugin implements Plugin {

    private String message;

    private static final Metadata fixed = Metadata.builder()
        .name("io.github.photowey.plugin4j.plugin.manager.samples.EchoPlugin")
        .version("1.0.0")
        .description("Echo message plugin")
        .extensions(Map.of())
        .build();

    @Override
    public Metadata metadata() {
        return fixed;
    }

    @Override
    public void init(PluginContext context, Map<String, Object> props) {
        Object messageProp = props == null ? null : props.get("message");
        if (!(messageProp instanceof String) || ((String) messageProp).isBlank()) {
            throw PluginException.invalidConfig("message is required");
        }

        this.message = (String) messageProp;
    }

    @Override
    public PluginResult execute(PluginContext context) throws PluginException {
        long start = System.currentTimeMillis();
        context.getLogger().info("echo plugin executing");
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("message", message);
        metrics.put("costMs", System.currentTimeMillis() - start);

        return PluginResult.ok(metrics);
    }

    @Override
    public void close() throws IOException {
        // nothing to close.
    }
}
