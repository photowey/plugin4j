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
