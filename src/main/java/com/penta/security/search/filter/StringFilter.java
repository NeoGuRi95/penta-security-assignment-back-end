package com.penta.security.search.filter;

import com.penta.security.search.dto.FilterDto;
import com.penta.security.search.type.FilterConditionType;
import com.penta.security.search.type.FilterType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.util.StringUtils;

/**
 * The StringFilter class provides methods for filtering a StringExpression based on a FilterDto.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringFilter {

    public static final FilterType FILTER_TYPE = FilterType.STRING;

    /**
     * Filters a StringExpression based on the provided FilterDto.
     *
     * @param expression the StringExpression to filter
     * @param filter     the FilterDto containing the filter condition and values
     * @return a BooleanExpression that represents the filtering condition
     * @throws IllegalArgumentException if the expression parameter is null or the condition in the filter is unexpected
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
            .map(StringFilter::convertValue) // TODO: extract to StringUtils
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
            case IS_CONTAINS -> {
                // contains
                if (values.size() == 1) {
                    yield expression.toLowerCase()
                        .like(("%" + values.get(0) + "%").toLowerCase());

                    // containsAnyOf
                } else { // values.size() > 1
                    yield values.stream()
                        .map(v -> expression.toLowerCase()
                            .like(("%" + v + "%").toLowerCase()))
                        .reduce(BooleanExpression::or)
                        .orElse(Expressions.ONE.eq(Expressions.ONE));
                }
            }
            case DOES_NOT_CONTAIN -> {
                // doesNotContain
                if (values.size() == 1) {
                    yield expression.toLowerCase()
                        .notLike(("%" + values.get(0) + "%").toLowerCase())
                        .or(expression.isNull());

                    // doesNotContainAnyOf
                } else { // values.size() > 1
                    yield values.stream()
                        .map(v -> expression.toLowerCase()
                            .notLike(("%" + v + "%").toLowerCase()))
                        .reduce(BooleanExpression::and)
                        .orElse(Expressions.ONE.eq(Expressions.ONE))
                        .or(expression.isNull());
                }
            }
            case START_WITH -> {
                // startWith
                if (values.size() == 1) {
                    yield expression.toLowerCase()
                        .like((values.get(0) + "%").toLowerCase());

                    // startWithAnyOf
                } else { // values.size() > 1
                    yield values.stream()
                        .map(v -> expression.toLowerCase()
                            .like((v + "%").toLowerCase()))
                        .reduce(BooleanExpression::or)
                        .orElse(Expressions.ONE.eq(Expressions.ONE));
                }
            }
            case END_WITH -> {
                // endWith
                if (values.size() == 1) {
                    yield expression.toLowerCase()
                        .like(("%" + values.get(0)).toLowerCase());

                    // endWithAnyOf
                } else { // values.size() > 1
                    yield values.stream()
                        .map(v -> expression.toLowerCase()
                            .like(("%" + v).toLowerCase()))
                        .reduce(BooleanExpression::or)
                        .orElse(Expressions.ONE.eq(Expressions.ONE));
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
     * Converts the given value according to certain rules.
     *
     * @param value the value to be converted
     * @return the converted value
     * @throws IllegalArgumentException if value is invalid
     */
    public static String convertValue(String value)
        throws IllegalArgumentException {

        // validation

        // refine value
        // isBlank
        if (StringUtils.hasLength(value) && !StringUtils.hasText(value)) {
            return "";
        }
        // lowercase, trim and etc

        return value;
    }
}
