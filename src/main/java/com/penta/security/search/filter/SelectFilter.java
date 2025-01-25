package com.penta.security.search.filter;

import com.penta.security.search.dto.FilterDto;
import com.penta.security.search.type.FilterConditionType;
import com.penta.security.search.type.FilterType;
import com.penta.security.search.type.FilterValue;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * The SelectFilter class provides methods for filtering various types of expressions based on a FilterDto.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SelectFilter {

    public static final FilterType FILTER_TYPE = FilterType.SELECT;

    /**
     * Filters an EnumExpression based on the given FilterDto.
     *
     * @param expression the EnumExpression to filter
     * @param filter     the FilterDto containing the filter condition and values
     * @param enumType   the Class of the Enum
     * @return a BooleanExpression representing the filtered expression
     * @throws IllegalArgumentException if an unexpected condition type is encountered
     */
    public static <T extends Enum<T>> BooleanExpression filter(
        @NonNull EnumExpression<T> expression,
        FilterDto filter,
        @NonNull Class<T> enumType)
        throws IllegalArgumentException {

        Objects.requireNonNull(expression);
        Objects.requireNonNull(enumType);

        if (filter == null) {
            return null;
        }

        FilterConditionType condition = filter.getCondition();

        // convert value type from string to enum
        List<T> values = filter.getValues() != null
            ? filter.getValues().stream()
            .map(StringFilter::convertValue)
            .filter(v -> !v.isEmpty())
            .map(v -> Enum.valueOf(enumType, v))
            .toList()
            : Collections.emptyList();

        // all
        if (!FilterConditionType.IS_EMPTY.equals(condition) &&
            !FilterConditionType.IS_NOT_EMPTY.equals(condition) &&
            (values.isEmpty())) {
            return Expressions.ONE.eq(Expressions.ONE);
        }

        return switch (condition) {
            case IS -> {
                // is
                if (values.size() == 1) {
                    yield expression.eq(values.get(0));

                    // isAnyOf
                } else { // values.size() > 1
                    yield expression.in(values);
                }
            }
            case IS_NOT -> {
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
            case IS_EMPTY -> expression.isNull();
            case IS_NOT_EMPTY -> expression.isNotNull();
            default -> throw new IllegalArgumentException(
                "Unexpected condition type: " + condition);
        };
    }

    /**
     * Filters a NumberExpression based on the given FilterDto.
     *
     * @param expression the NumberExpression to filter
     * @param filter     the FilterDto containing the filter condition and values
     * @return a BooleanExpression representing the filtered expression
     * @throws IllegalArgumentException if an unexpected condition type is encountered
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
            .map(v -> (T) NumberFilter.convertValue(v, type))
            .toList()
            : Collections.emptyList();

        // all
        if (!FilterConditionType.IS_EMPTY.equals(condition) &&
            !FilterConditionType.IS_NOT_EMPTY.equals(condition) &&
            (values.isEmpty())) {
            return Expressions.ONE.eq(Expressions.ONE);
        }

        return switch (condition) {
            case IS -> {
                // is
                if (values.size() == 1) {
                    yield expression.eq(values.get(0));

                    // isAnyOf
                } else { // values.size() > 1
                    yield expression.in(values);
                }
            }
            case IS_NOT -> {
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
            case IS_EMPTY -> expression.isNull();
            case IS_NOT_EMPTY -> expression.isNotNull();
            default -> throw new IllegalArgumentException(
                "Unexpected condition type: " + condition);
        };
    }

    /**
     * Filters a StringExpression based on the given FilterDto.
     *
     * @param expression the StringExpression to filter
     * @param filter     the FilterDto containing the filter condition and values
     * @return a BooleanExpression representing the filtered expression
     * @throws IllegalArgumentException if an unexpected condition type is encountered
     */
    public static BooleanExpression filter(
        @NonNull StringExpression expression,
        FilterDto filter)
        throws IllegalArgumentException {

        Objects.requireNonNull(expression);

        if (filter == null) {
            return null;
        }

        FilterConditionType condition = filter.getCondition();

        List<String> values = filter.getValues() != null
            ? filter.getValues().stream()
            .map(StringFilter::convertValue)
            .filter(v -> !v.isEmpty())
            .toList()
            : Collections.emptyList();

        // all
        if (!FilterConditionType.IS_EMPTY.equals(condition) &&
            !FilterConditionType.IS_NOT_EMPTY.equals(condition) &&
            (values.isEmpty())) {
            return Expressions.ONE.eq(Expressions.ONE);
        }

        return switch (condition) {
            case IS -> {
                // is
                if (values.size() == 1) {
                    yield expression.eq(values.get(0));

                    // isAnyOf
                } else { // values.size() > 1
                    yield expression.in(values);
                }
            }
            case IS_NOT -> {
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
            case IS_EMPTY -> expression.isEmpty()
                .or(expression.isNull());
            case IS_NOT_EMPTY -> expression.isNotEmpty()
                .and(expression.isNotNull());
            default -> throw new IllegalArgumentException(
                "Unexpected condition type: " + condition);
        };
    }

    /**
     * Filters a BooleanExpression based on the given FilterDto.
     *
     * @param expression the BooleanExpression to filter
     * @param filter     the FilterDto containing the filter condition and values
     * @return a BooleanExpression representing the filtered expression
     * @throws IllegalArgumentException if an unexpected condition type is encountered
     */
    public static BooleanExpression filter(
        @NonNull BooleanExpression expression,
        FilterDto filter)
        throws IllegalArgumentException {

        Objects.requireNonNull(expression);

        if (filter == null) {
            return null;
        }

        FilterConditionType condition = filter.getCondition();

        List<Boolean> values = filter.getValues() != null
            ? filter.getValues().stream()
            .map(StringFilter::convertValue)
            .filter(v -> !v.isEmpty())
            .map(Boolean::valueOf)
            .toList()
            : Collections.emptyList();

        // all
        if (!FilterConditionType.IS_EMPTY.equals(condition) &&
            !FilterConditionType.IS_NOT_EMPTY.equals(condition) &&
            (values.isEmpty())) {
            return Expressions.ONE.eq(Expressions.ONE);
        }

        return switch (condition) {
            case IS -> {
                // is
                if (values.size() == 1) {
                    yield expression.eq(values.get(0));

                    // none
                } else { // values.size() > 1
                    yield Expressions.ONE.eq(Expressions.TWO);
                }
            }
            case IS_NOT -> {
                // isNot
                if (values.size() == 1) {
                    yield expression.ne(values.get(0))
                        .or(expression.isNull());

                    // none
                } else { // values.size() > 1
                    yield Expressions.ONE.eq(Expressions.TWO);
                }
            }
            case IS_EMPTY -> expression.isNull();
            case IS_NOT_EMPTY -> expression.isNotNull();
            default -> throw new IllegalArgumentException(
                "Unexpected condition type: " + condition);
        };
    }

    /**
     * Returns a list of FilterValueDto objects for boolean values.
     *
     * @param queryFactory the JPAQueryFactory object
     * @param o            the object
     * @return a list of FilterValueDto objects representing boolean values
     */
    public static List<FilterValue> getBooleanValues(
        JPAQueryFactory queryFactory, Object o) {

        return List.of(
            FilterValue.builder()
                .name("TRUE")
                .value(String.valueOf(Boolean.TRUE))
                .build(),
            FilterValue.builder()
                .name("FALSE")
                .value(String.valueOf(Boolean.FALSE))
                .build());
    }
}
