/*
 * Copyright 2002-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.photowey.plugin4j.plugin.core.shaded.org.springframework.core;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import io.github.photowey.plugin4j.plugin.core.domain.ordered.Ordered;
import io.github.photowey.plugin4j.plugin.core.domain.ordered.PriorityOrdered;

/**
 * Copy from {@code org.springframework.core.OrderComparator} in Spring Framework.
 *
 * <p>
 * {@link Comparator} implementation for {@link Ordered} objects, sorting
 * by order value ascending, respectively by priority descending.
 *
 * <h3>{@code PriorityOrdered} Objects</h3>
 *
 * <p>
 * {@link PriorityOrdered} objects will be sorted with higher priority than
 * <em>plain</em> {@code Ordered} objects.
 *
 * <h3>Same Order Objects</h3>
 *
 * <p>
 * Objects that have the same order value will be sorted with arbitrary
 * ordering with respect to other objects with the same order value.
 *
 * <h3>Non-ordered Objects</h3>
 *
 * <p>
 * Any object that does not provide its own order value is implicitly
 * assigned a value of {@link Ordered#LOWEST_PRECEDENCE}, thus ending up
 * at the end of a sorted collection in arbitrary order with respect to
 * other objects with the same order value.
 *
 * <p>
 * org.springframework.core.annotation.AnnotationAwareOrderComparator
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @see Ordered
 * @see PriorityOrdered
 * @see java.util.List#sort(java.util.Comparator)
 * @see java.util.Arrays#sort(Object[], java.util.Comparator)
 * @since 07.04.2003
 */
public class OrderComparator implements Comparator<Object> {

    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    /**
     * Shared default instance of {@code OrderComparator}.
     */
    public static final OrderComparator INSTANCE = new OrderComparator();


    /**
     * Build an adapted order comparator with the given source provider.
     *
     * @param sourceProvider the order source provider to use
     * @return the adapted comparator
     * @since 4.1
     */
    public Comparator<Object> withSourceProvider(OrderSourceProvider sourceProvider) {
        return (o1, o2) -> doCompare(o1, o2, sourceProvider);
    }

    @Override
    public int compare(Object o1, Object o2) {
        return doCompare(o1, o2, null);
    }

    private int doCompare(Object o1, Object o2, OrderSourceProvider sourceProvider) {
        boolean p1 = (o1 instanceof PriorityOrdered);
        boolean p2 = (o2 instanceof PriorityOrdered);
        if (p1 && !p2) {
            return -1;
        } else if (p2 && !p1) {
            return 1;
        }

        int i1 = getOrder(o1, sourceProvider);
        int i2 = getOrder(o2, sourceProvider);
        return Integer.compare(i1, i2);
    }

    /**
     * Determine the order value for the given object.
     *
     * <p>The default implementation checks against the given {@link OrderSourceProvider}
     * using {@link #findOrder} and falls back to a regular {@link #getOrder(Object)} call.
     *
     * @param obj the object to check
     * @return the order value, or {@code Ordered.LOWEST_PRECEDENCE} as fallback
     */
    private int getOrder(Object obj, OrderSourceProvider sourceProvider) {
        Integer order = null;
        if (obj != null && sourceProvider != null) {
            Object orderSource = sourceProvider.getOrderSource(obj);
            if (orderSource != null) {
                if (orderSource.getClass().isArray()) {
                    for (Object source : toObjectArray(orderSource)) {
                        order = findOrder(source);
                        if (order != null) {
                            break;
                        }
                    }
                } else {
                    order = findOrder(orderSource);
                }
            }
        }
        return (order != null ? order : getOrder(obj));
    }

    /**
     * Determine the order value for the given object.
     *
     * <p>The default implementation checks against the {@link Ordered} interface
     * through delegating to {@link #findOrder}. Can be overridden in subclasses.
     *
     * @param obj the object to check
     * @return the order value, or {@code Ordered.LOWEST_PRECEDENCE} as fallback
     */
    protected int getOrder(Object obj) {
        if (obj != null) {
            Integer order = findOrder(obj);
            if (order != null) {
                return order;
            }
        }
        return Ordered.LOWEST_PRECEDENCE;
    }

    /**
     * Find an order value indicated by the given object.
     *
     * <p>The default implementation checks against the {@link Ordered} interface.
     * Can be overridden in subclasses.
     *
     * @param obj the object to check
     * @return the order value, or {@code null} if none found
     */
    protected Integer findOrder(Object obj) {
        return obj instanceof Ordered
            ? ((Ordered) obj).getOrder()
            : null;
    }

    /**
     * Determine a priority value for the given object, if any.
     *
     * <p>The default implementation always returns {@code null}.
     * Subclasses may override this to give specific kinds of values a
     * 'priority' characteristic, in addition to their 'order' semantics.
     * A priority indicates that it may be used for selecting one object over
     * another, in addition to serving for ordering purposes in a list/array.
     *
     * @param obj the object to check
     * @return the priority value, or {@code null} if none
     * @since 4.1
     */
    public Integer getPriority(Object obj) {
        return null;
    }

    /**
     * Sort the given List with a default OrderComparator.
     *
     * <p>Optimized to skip sorting for lists with size 0 or 1,
     * in order to avoid unnecessary array extraction.
     *
     * @param list the List to sort
     * @see java.util.List#sort(java.util.Comparator)
     */
    public static void sort(List<?> list) {
        if (list.size() > 1) {
            list.sort(INSTANCE);
        }
    }

    /**
     * Sort the given array with a default OrderComparator.
     *
     * <p>Optimized to skip sorting for lists with size 0 or 1,
     * in order to avoid unnecessary array extraction.
     *
     * @param array the array to sort
     * @see java.util.Arrays#sort(Object[], java.util.Comparator)
     */
    public static void sort(Object[] array) {
        if (array.length > 1) {
            Arrays.sort(array, INSTANCE);
        }
    }

    /**
     * Sort the given array or List with a default OrderComparator,
     * if necessary. Simply skips sorting when given any other value.
     *
     * <p>Optimized to skip sorting for lists with size 0 or 1,
     * in order to avoid unnecessary array extraction.
     *
     * @param value the array or List to sort
     * @see java.util.Arrays#sort(Object[], java.util.Comparator)
     */
    public static void sortIfNecessary(Object value) {
        if (value instanceof Object[]) {
            Object[] objects = (Object[]) value;
            sort(objects);
        } else if (value instanceof List<?>) {
            List<?> objects = (List<?>) value;
            sort(objects);
        }
    }

    /**
     * Strategy interface to provide an order source for a given object.
     *
     * @since 4.1
     */
    @FunctionalInterface
    public interface OrderSourceProvider {

        /**
         * Return an order source for the specified object, i.e. an object that
         * should be checked for an order value as a replacement to the given object.
         *
         * <p>
         * Can also be an array of order source objects.
         *
         * <p>
         * If the returned object does not indicate any order, the comparator
         * will fall back to checking the original object.
         *
         * @param obj the object to find an order source for
         * @return the order source for that object, or {@code null} if none found
         */
        Object getOrderSource(Object obj);
    }

    /**
     * Convert the given array (which may be a primitive array) to an object array (if
     * necessary, to an array of primitive wrapper objects).
     *
     * <p>A {@code null} source value or empty primitive array will be converted to an
     * empty Object array.
     *
     * @param source the (potentially primitive) array
     * @return the corresponding object array (never {@code null})
     * @throws IllegalArgumentException if the parameter is not an array
     */
    public static Object[] toObjectArray(Object source) {
        if (source instanceof Object[]) {
            return (Object[]) source;
        }
        if (source == null) {
            return EMPTY_OBJECT_ARRAY;
        }
        if (!source.getClass().isArray()) {
            throw new IllegalArgumentException("Source is not an array: " + source);
        }
        int length = Array.getLength(source);
        if (length == 0) {
            return EMPTY_OBJECT_ARRAY;
        }
        Class<?> wrapperType = Array.get(source, 0).getClass();
        Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
        for (int i = 0; i < length; i++) {
            newArray[i] = Array.get(source, i);
        }
        return newArray;
    }
}
