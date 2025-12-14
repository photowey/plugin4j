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
package io.github.photowey.plugin4j.plugin.core.converter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Json converter interface.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/12/14
 */
@SuppressWarnings("all")
public interface JsonConverter {

    /**
     * Convert target object to JSON string.
     *
     * @param target the target object to convert
     * @param <T>    the type of target object
     * @return the JSON string representation of target object
     */
    default <T> String toJSONString(T target) {
        return null;
    }

    /**
     * Parse JSON string to object of specified class.
     *
     * @param json  the JSON string to parse
     * @param clazz the class of target object
     * @param <T>   the type of target object
     * @return the parsed object
     */
    default <T> T parseObject(String json, Class<T> clazz) {
        return null;
    }

    /**
     * Parse JSON input stream to object of specified class.
     *
     * @param json  the JSON input stream to parse
     * @param clazz the class of target object
     * @param <T>   the type of target object
     * @return the parsed object
     */
    <T> T parseObject(InputStream json, Class<T> clazz);

    /**
     * Parse JSON byte array to object of specified class.
     *
     * @param json  the JSON byte array to parse
     * @param clazz the class of target object
     * @param <T>   the type of target object
     * @return the parsed object
     */
    default <T> T parseObject(byte[] json, Class<T> clazz) {
        return null;
    }

    /**
     * Parse JSON string to list of objects of specified class.
     *
     * @param json  the JSON string to parse
     * @param clazz the class of target objects
     * @param <T>   the type of target objects
     * @return the list of parsed objects
     */
    default <T> List<T> parseArray(String json, Class<T> clazz) {
        return new ArrayList<>(0);
    }

    /**
     * Parse JSON input stream to list of objects of specified class.
     *
     * @param json  the JSON input stream to parse
     * @param clazz the class of target objects
     * @param <T>   the type of target objects
     * @return the list of parsed objects
     */
    default <T> List<T> parseArray(InputStream json, Class<T> clazz) {
        return new ArrayList<>(0);
    }

    /**
     * Parse JSON byte array to list of objects of specified class.
     *
     * @param json  the JSON byte array to parse
     * @param clazz the class of target objects
     * @param <T>   the type of target objects
     * @return the list of parsed objects
     */
    default <T> List<T> parseArray(byte[] json, Class<T> clazz) {
        return new ArrayList<>(0);
    }
}
