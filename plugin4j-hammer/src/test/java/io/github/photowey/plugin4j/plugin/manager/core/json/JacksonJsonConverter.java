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
package io.github.photowey.plugin4j.plugin.manager.core.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

import io.github.photowey.plugin4j.plugin.core.converter.JsonConverter;

/**
 * {@code JacksonJsonConverter}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
public class JacksonJsonConverter implements JsonConverter {

    private static final ObjectMapper OBJECT_MAPPER = initDefaultObjectMapper();

    @Override
    public <T> String toJSONString(T target) {
        try {
            return OBJECT_MAPPER.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T parseObject(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T parseObject(InputStream json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T parseObject(byte[] json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> parseArray(String json, Class<T> clazz) {
        throw new UnsupportedOperationException("parseArray is not supported");
    }

    @Override
    public <T> List<T> parseArray(InputStream json, Class<T> clazz) {
        throw new UnsupportedOperationException("parseArray is not supported");
    }

    @Override
    public <T> List<T> parseArray(byte[] json, Class<T> clazz) {
        throw new UnsupportedOperationException("parseArray is not supported");
    }

    private static ObjectMapper initDefaultObjectMapper() {
        JsonMapper.Builder builder = JsonMapper.builder()
            .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
            .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
            .configure(JsonParser.Feature.IGNORE_UNDEFINED, true)
            .configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true)

            //.configure(DeserializationFeature.USE_LONG_FOR_INTS, true)

            .configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)

            // Exclude properties not annotated with @JsonView
            .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
            /*.addModule(new JavaTimeModule())*/;

        JsonMapper jsonMapper = builder.build();
        jsonMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);

        return jsonMapper;
    }
}
