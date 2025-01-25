package com.penta.security.search.filter;

import com.penta.security.search.dto.FilterDto;
import com.penta.security.search.type.FilterConditionType;
import com.penta.security.search.type.FilterType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * The NumberFilter class provides methods for filtering numeric expressions based on filter conditions.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberFilter {

    public static final FilterType FILTER_TYPE = FilterType.NUMBER;

    /**
     * Filters the given expression based on the provided filter condition.
     *
     * @param expression The expression to filter.
     * @param filter     The filter condition to apply.
     * @param <T>        The type of the expression.
     * @return The filtered expression.
     * @throws IllegalArgumentException If the expression is null or if an unexpected condition type is provided.
     */
    public static <T extends Number & Comparable<?>> BooleanExpression filter(
        @NonNull NumberExpression<T> expression,
        FilterDto filter)
        throws IllegalArgumentException {

        Objects.requireNonNull(expression);

        if (filter == null) {
            return null;
        }

        FilterConditionType condition = filter.getCondition();

        // convert value type from string to number
        Class<?> type = expression.getType();
        List<T> values = filter.getValues() != null
            ? filter.getValues().stream()
            .map(StringFilter::convertValue)
            .filter(v -> !v.isEmpty())
            .map(v -> (T) convertValue(v, type))
            .toList()
            : Collections.emptyList();

        // all
        if (!FilterConditionType.IS_EMPTY.equals(condition) &&
            !FilterConditionType.IS_NOT_EMPTY.equals(condition) &&
            (values.isEmpty())) {
            return Expressions.ONE.eq(Expressions.ONE);
        }

        return switch (condition) {
            case EQUAL, IS -> {
                // is
                if (values.size() == 1) {
                    yield expression.eq(values.get(0));

                    // isAnyOf
                } else { // values.size() > 1
                    yield expression.in(values);
                }
            }
            case NOT_EQUAL, IS_NOT -> {
                // isNot
                if (values.size() == 1) {
                    yield expression.ne(values.get(0))
                        .or(expression.isNull());

                    // isNoneOf
                } else { // values.size() > 1
                    yield expression.notIn(values)
                        .or(expression.isNull());
                }
            }
            case GREATER_THAN -> {
                // greater than
                if (values.size() == 1) {
                    yield expression.gt(values.get(0));

                    // greater than the min value
                } else { // values.size() > 1
                    yield expression.gt(min(values, type));
                }
            }
            case GREATER_THAN_OR_EQUAL -> {
                // greater than or equal
                if (values.size() == 1) {
                    yield expression.goe(values.get(0));

                    // greater than or equal the min value
                } else { // values.size() > 1
                    yield expression.goe(min(values, type));
                }
            }
            case LESS_THAN -> {
                // less than
                if (values.size() == 1) {
                    yield expression.lt(values.get(0));

                    // less than the max value
                } else { // values.size() > 1
                    yield expression.lt(max(values, type));
                }
            }
            case LESS_THAN_OR_EQUAL -> {
                // less than or equal
                if (values.size() == 1) {
                    yield expression.loe(values.get(0));

                    // less than or equal the max value
                } else { // values.size() > 1
                    yield expression.loe(max(values, type));
                }
            }
            case IS_EMPTY -> expression.isNull();
            case IS_NOT_EMPTY -> expression.isNotNull();
            default -> throw new IllegalArgumentException(
                "Unexpected condition type: " + condition);
        };
    }

    /**
     * Converts a string value to the specified number type.
     *
     * @param value The value to be converted.
     * @param type  The target type to convert the value to.
     * @param <T>   The type of the target value.
     * @return The converted value of type T.
     * @throws IllegalArgumentException If an unexpected expression type is provided.
     */
    public static <T extends Number & Comparable<?>> T convertValue(
        String value, Class<?> type)
        throws IllegalArgumentException {

        if (type.equals(BigDecimal.class)) {
            return (T) new BigDecimal(value);
        } else if (type.equals(BigInteger.class)) {
            return (T) new BigInteger(value);

        } else if (type.equals(Byte.class)) {
            return (T) Byte.valueOf(value);
        } else if (type.equals(Short.class)) {
            return (T) Short.valueOf(value);
        } else if (type.equals(Integer.class)) {
            return (T) Integer.valueOf(value);
        } else if (type.equals(Long.class)) {
            return (T) Long.valueOf(value);

        } else if (type.equals(Float.class)) {
            return (T) Float.valueOf(value);
        } else if (type.equals(Double.class)) {
            return (T) Double.valueOf(value);

        } else {
            throw new IllegalArgumentException(
                "Unexpected expression type: " + type);
        }
    }

    /**
     * Finds the minimum value in the given list of values of the specified type.
     *
     * @param values The list of values to find the minimum from.
     * @param type   The target type of the values.
     * @param <T>    The type of the values.
     * @return The minimum value of type T, or null if the list is empty or null.
     * @throws IllegalArgumentException If an unexpected expression type is provided.
     */
    public static <T extends Number & Comparable<?>> T min(
        List<T> values, Class<?> type)
        throws IllegalArgumentException {

        if (values == null || values.isEmpty()) {
            return null;
        }

        if (type.equals(BigDecimal.class)) {
            return values.stream()
                .min(Comparator.comparing(v -> ((BigDecimal) v)))
                .orElse(null);

        } else if (type.equals(BigInteger.class)) {
            return values.stream()
                .min(Comparator.comparing(v -> ((BigInteger) v)))
                .orElse(null);


        } else if (type.equals(Byte.class)) {
            return values.stream()
                .min(Comparator.comparing(v -> ((Byte) v)))
                .orElse(null);

        } else if (type.equals(Short.class)) {
            return values.stream()
                .min(Comparator.comparing(v -> ((Short) v)))
                .orElse(null);

        } else if (type.equals(Integer.class)) {
            return values.stream()
                .min(Comparator.comparing(v -> (Integer) v))
                .orElse(null);

        } else if (type.equals(Long.class)) {
            return values.stream()
                .min(Comparator.comparing(v -> (Long) v))
                .orElse(null);


        } else if (type.equals(Float.class)) {
            return values.stream()
                .min(Comparator.comparing(v -> (Float) v))
                .orElse(null);

        } else if (type.equals(Double.class)) {
            return values.stream()
                .min(Comparator.comparing(v -> (Double) v))
                .orElse(null);


        } else {
            throw new IllegalArgumentException(
                "Unexpected expression type: " + type);
        }
    }

    /**
     * Finds the maximum value in the given list of values of the specified type.
     *
     * @param values The list of values to find the maximum from.
     * @param type   The target type of the values.
     * @param <T>    The type of the values.
     * @return The maximum value of type T, or null if the list is empty or null.
     * @throws IllegalArgumentException If an unexpected expression type is provided.
     */
    public static <T extends Number & Comparable<?>> T max(
        List<T> values, Class<?> type)
        throws IllegalArgumentException {

        if (values == null || values.isEmpty()) {
            return null;
        }

        if (type.equals(BigDecimal.class)) {
            return values.stream()
                .max(Comparator.comparing(v -> ((BigDecimal) v)))
                .orElse(null);

        } else if (type.equals(BigInteger.class)) {
            return values.stream()
                .max(Comparator.comparing(v -> ((BigInteger) v)))
                .orElse(null);


        } else if (type.equals(Byte.class)) {
            return values.stream()
                .max(Comparator.comparing(v -> ((Byte) v)))
                .orElse(null);

        } else if (type.equals(Short.class)) {
            return values.stream()
                .max(Comparator.comparing(v -> ((Short) v)))
                .orElse(null);

        } else if (type.equals(Integer.class)) {
            return values.stream()
                .max(Comparator.comparing(v -> (Integer) v))
                .orElse(null);

        } else if (type.equals(Long.class)) {
            return values.stream()
                .max(Comparator.comparing(v -> (Long) v))
                .orElse(null);


        } else if (type.equals(Float.class)) {
            return values.stream()
                .max(Comparator.comparing(v -> (Float) v))
                .orElse(null);

        } else if (type.equals(Double.class)) {
            return values.stream()
                .max(Comparator.comparing(v -> (Double) v))
                .orElse(null);


        } else {
            throw new IllegalArgumentException(
                "Unexpected expression type: " + type);
        }
    }
}
