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
package io.github.photowey.plugin4j.plugin.core.util;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import io.github.photowey.plugin4j.plugin.core.converter.JsonConverter;

/**
 * {@code Jsons}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
public final class Jsons {

    private static JsonConverter jsonConverter;

    private Jsons() {
        AssertionErrors.throwz(Jsons.class);
    }

    public static void register(JsonConverter converter) {
        jsonConverter = converter;
    }

    // ----------------------------------------------------------------

    public static <T> T read(String json, Class<T> clazz) {
        JsonConverter converter = getJsonConverter();

        return converter.parseObject(json, clazz);
    }

    public static <T> T read(InputStream json, Class<T> clazz) {
        JsonConverter converter = getJsonConverter();

        return converter.parseObject(json, clazz);
    }

    public static <T> T read(byte[] json, Class<T> clazz) {
        JsonConverter converter = getJsonConverter();
        return converter.parseObject(json, clazz);
    }

    public static <T> T read(Path path, Class<T> clazz) {
        try (InputStream input = Files.newInputStream(path)) {
            return read(input, clazz);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonConverter getJsonConverter() {
        if (Objects.isNull(jsonConverter)) {
            throw new IllegalStateException("JsonConverter not registered");
        }

        return jsonConverter;
    }
}
