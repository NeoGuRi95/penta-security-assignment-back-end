package com.penta.security.search.filter;

import com.penta.security.search.dto.FilterDto;
import com.penta.security.search.type.DateFilterPivotType;
import com.penta.security.search.type.DateFilterRangeType;
import com.penta.security.search.type.DateFilterValueType;
import com.penta.security.search.type.FilterConditionType;
import com.penta.security.search.type.FilterType;
import com.penta.security.search.type.FilterValue;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The DateFilter class provides methods for filtering DateTimeExpression based on FilterDto.
 *
 * @see DateTimeExpression
 * @see FilterDto
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateFilter {

    public static final FilterType FILTER_TYPE = FilterType.DATE;

    /**
     * Filters the given DateTimeExpression based on the provided FilterDto.
     *
     * @param expression The DateTimeExpression to filter.
     * @param filter     The FilterDto containing the filter conditions.
     * @return A BooleanExpression representing the filtered result.
     * @throws IllegalArgumentException If the filter values are invalid.
     */
    public static BooleanExpression filter(
        DatePath<LocalDate> expression,
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
                    LocalDate value = convertValue(values.get(0));
                    yield expression.eq(value);

                    // isAnyOf
                } else {
                    yield values.stream()
                        .map(v -> {
                            LocalDate value = convertValue(v);
                            return expression.eq(value);
                        })
                        .reduce(BooleanExpression::or)
                        .orElse(Expressions.ONE.eq(Expressions.ONE));
                }
            }
            case IS_NOT -> {
                // isNot
                if (values.size() == 1) {
                    LocalDate value = convertValue(values.get(0));
                    yield expression.ne(value);

                    // isNoneOf
                } else {
                    yield values.stream()
                        .map(v -> {
                            LocalDate value = convertValue(v);
                            return expression.ne(value);
                        })
                        .reduce(BooleanExpression::and)
                        .orElse(Expressions.ONE.eq(Expressions.ONE));
                }
            }
            case IS_BEFORE -> {
                // isBefore
                if (values.size() == 1) {
                    LocalDate value = convertValue(values.get(0));
                    yield expression.before(value);

                    // isBefore with the latest date
                } else {
                    LocalDate latestDate = values.stream()
                        .map(DateFilter::convertValue)
                        .max(Comparator.comparing(v -> v))
                        .orElse(null);
                    yield expression.before(latestDate);
                }
            }
            case IS_ON_OR_BEFORE -> {
                // isOnOrBefore
                if (values.size() == 1) {
                    LocalDate value = convertValue(values.get(0));
                    yield expression.before(value.plusDays(1));

                    // isOnOrBefore with the latest date
                } else {
                    LocalDate latestDate = values.stream()
                        .map(DateFilter::convertValue)
                        .max(Comparator.comparing(v -> v))
                        .orElse(null);
                    yield expression.before(latestDate.plusDays(1));
                }
            }
            case IS_AFTER -> {
                // isAfter
                if (values.size() == 1) {
                    LocalDate value = convertValue(values.get(0));
                    yield expression.after(value);

                    // isAfter with the earliest date
                } else {
                    LocalDate earliestDate = values.stream()
                        .map(DateFilter::convertValue)
                        .min(Comparator.comparing(v -> v))
                        .orElse(null);
                    yield expression.after(earliestDate);
                }
            }
            case IS_ON_OR_AFTER -> {
                // isOnOrAfter
                if (values.size() == 1) {
                    LocalDate value = convertValue(values.get(0));
                    yield expression.after(value.minusDays(1));

                    // isOnOrAfter with the earliest date
                } else {
                    LocalDate earliestDate = values.stream()
                        .map(DateFilter::convertValue)
                        .min(Comparator.comparing(v -> v))
                        .orElse(null);
                    yield expression.after(earliestDate.minusDays(1));
                }
            }
            case IS_BETWEEN -> {
                // isBetween
                if (values.size() == 2) {
                    LocalDate from = convertValue(values.get(0));
                    LocalDate to = convertValue(values.get(1));
                    yield expression.between(from, to);

                    // isBetweenAnyOf
                } else if (values.size() % 2 == 0) {
                    int index = 0;
                    BooleanExpression result = Expressions.ONE.eq(Expressions.TWO);
                    while (index < values.size()) {
                        LocalDate from = convertValue(values.get(index));
                        LocalDate to = convertValue(values.get(index + 1));
                        result = result.or(expression.between(from, to));
                        index += 2;
                    }
                    yield result;

                } else {
                    throw new IllegalArgumentException("wrong value set");
                }
            }
            case IS_RELATIVE_TO_TODAY -> {
                // isRelativeAnyOf
                if (values.size() % 3 != 0) throw new IllegalArgumentException("wrong value set");

                int index = 0;
                BooleanExpression result = Expressions.ONE.eq(Expressions.TWO);
                while (index < values.size()) {

                    DateFilterPivotType pivotType = DateFilterPivotType.from(
                        values.get(index));
                    int range = !DateFilterPivotType.THIS.equals(pivotType)
                        ? Integer.parseInt(values.get(index + 1))
                        : 1;
                    DateFilterRangeType rangeType = DateFilterRangeType.from(
                        values.get(index + 2));

                    if (pivotType == null || range < 1 || rangeType == null) {
                        throw new IllegalArgumentException("wrong input: " +
                            "pivotType: " + pivotType + ", " +
                            "range: " + range + ", " +
                            "rangeType: " + rangeType);
                    }

                    LocalDate from = null;
                    LocalDate to = null;
                    LocalDate today = LocalDate.now();

                    if (DateFilterPivotType.PAST.equals(pivotType)) {
                        if (DateFilterRangeType.DAY.equals(rangeType)) {
                            from = today.minusDays(range);
                        } else if (DateFilterRangeType.WEEK.equals(rangeType)) {
                            from = today.minusWeeks(range);
                        } else if (DateFilterRangeType.MONTH.equals(rangeType)) {
                            from = today.minusMonths(range);
                        } else if (DateFilterRangeType.YEAR.equals(rangeType)) {
                            from = today.minusYears(range);
                        }
                        to = today;

                    } else if (DateFilterPivotType.NEXT.equals(pivotType)) {
                        from = today;
                        if (DateFilterRangeType.DAY.equals(rangeType)) {
                            to = today.plusDays(range);
                        } else if (DateFilterRangeType.WEEK.equals(rangeType)) {
                            to = today.plusWeeks(range);
                        } else if (DateFilterRangeType.MONTH.equals(rangeType)) {
                            to = today.plusMonths(range);
                        } else if (DateFilterRangeType.YEAR.equals(rangeType)) {
                            to = today.plusYears(range);
                        }

                    } else if (DateFilterPivotType.THIS.equals(pivotType)) {
                        if (DateFilterRangeType.DAY.equals(rangeType)) {
                            from = today;
                            to = today;

                        } else if (DateFilterRangeType.WEEK.equals(rangeType)) {
                            from = today.with(
                                TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
                            to = today.with(
                                TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

                        } else if (DateFilterRangeType.MONTH.equals(rangeType)) {
                            from = today.with(TemporalAdjusters.firstDayOfMonth());
                            to = today.with(TemporalAdjusters.lastDayOfMonth());

                        } else if (DateFilterRangeType.YEAR.equals(rangeType)) {
                            from = today.with(TemporalAdjusters.firstDayOfYear());
                            to = today.with(TemporalAdjusters.lastDayOfYear());
                        }

                    } else if (DateFilterPivotType.PAST_BEFORE.equals(pivotType)) {
                        if (DateFilterRangeType.DAY.equals(rangeType)) {
                            LocalDate baseDate = today.minusDays(range);
                            from = baseDate;
                            to = baseDate;

                        } else if (DateFilterRangeType.WEEK.equals(rangeType)) {
                            LocalDate baseDate = today.minusWeeks(range);
                            from = baseDate.with(
                                TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
                            to = baseDate.with(
                                TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

                        } else if (DateFilterRangeType.MONTH.equals(rangeType)) {
                            LocalDate baseDate = today.minusMonths(range);
                            from = baseDate.with(TemporalAdjusters.firstDayOfMonth());
                            to = baseDate.with(TemporalAdjusters.lastDayOfMonth());

                        } else if (DateFilterRangeType.YEAR.equals(rangeType)) {
                            LocalDate baseDate = today.minusYears(range);
                            from = baseDate.with(TemporalAdjusters.firstDayOfYear());
                            to = baseDate.with(TemporalAdjusters.lastDayOfYear());
                        }

                    } else if (DateFilterPivotType.NEXT_AFTER.equals(pivotType)) {
                        if (DateFilterRangeType.DAY.equals(rangeType)) {
                            LocalDate baseDate = today.plusDays(range);
                            from = baseDate;
                            to = baseDate;

                        } else if (DateFilterRangeType.WEEK.equals(rangeType)) {
                            LocalDate baseDate = today.plusWeeks(range);
                            from = baseDate.with(
                                TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
                            to = baseDate.with(
                                TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

                        } else if (DateFilterRangeType.MONTH.equals(rangeType)) {
                            LocalDate baseDate = today.plusMonths(range);
                            from = baseDate.with(TemporalAdjusters.firstDayOfMonth());
                            to = baseDate.with(TemporalAdjusters.lastDayOfMonth());

                        } else if (DateFilterRangeType.YEAR.equals(rangeType)) {
                            LocalDate baseDate = today.plusYears(range);
                            from = baseDate.with(TemporalAdjusters.firstDayOfYear());
                            to = baseDate.with(TemporalAdjusters.lastDayOfYear());
                        }
                    }

                    result = result.or(expression.between(from, to));
                    index += 3;
                }
                yield result;

            }
            case IS_EMPTY -> expression.isNull();
            case IS_NOT_EMPTY -> expression.isNotNull();
            default -> throw new IllegalArgumentException(
                "Unexpected condition type: " + condition);
        };
    }

    /**
     * Converts a string representation of a date filter value into a {@link LocalDate} object.
     *
     * @param value The string representation of the date filter value.
     * @return The converted {@link LocalDate} object.
     * @throws DateTimeParseException If the string cannot be parsed into a valid date.
     * @throws NullPointerException   If the value is null.
     */
    public static LocalDate convertValue(String value)
        throws DateTimeParseException {

        Objects.requireNonNull(value);

        LocalDate today = LocalDate.now();

        DateFilterValueType valueType = DateFilterValueType.from(value);
        if (DateFilterValueType.TODAY.equals(valueType)) {
            return today;
        } else if (DateFilterValueType.YESTERDAY.equals(valueType)) {
            return today.minusDays(1);
        } else if (DateFilterValueType.TOMORROW.equals(valueType)) {
            return today.plusDays(1);
        } else if (DateFilterValueType.ONE_WEEK_AGO.equals(valueType)) {
            return today.minusWeeks(1);
        } else if (DateFilterValueType.ONE_WEEK_FROM_NOW.equals(valueType)) {
            return today.plusWeeks(1);
        } else if (DateFilterValueType.ONE_MONTH_AGO.equals(valueType)) {
            return today.minusMonths(1);
        } else if (DateFilterValueType.ONE_MONTH_FROM_NOW.equals(valueType)) {
            return today.plusMonths(1);
        } else {
            return LocalDate.parse(value);
        }
    }

    /**
     * Retrieves a list of filter values based on the given condition.
     *
     * @param queryFactory The JPAQueryFactory object used for querying the database.
     * @param o            The condition object used to determine the filter values. It can be
     *                     either a FilterConditionType object or a String object.
     * @return A list of FilterValueDto objects representing the filter values.
     * @see FilterValue
     */
    public static List<FilterValue> getValues(
        JPAQueryFactory queryFactory, Object o) {

        FilterConditionType condition = null;
        if (o != null) {
            if (o.getClass().equals(FilterConditionType.class)) {
                condition = (FilterConditionType) o;

            } else if (o.getClass().equals(String.class)) {
                condition = FilterConditionType.from((String) o);
            }
        }

        if (condition == null) {
            return Collections.emptyList();
        }
        return switch (condition) {
            case IS, IS_NOT, IS_BEFORE, IS_AFTER, IS_ON_OR_BEFORE, IS_ON_OR_AFTER ->
                Stream.of(DateFilterValueType.values())
                    .map(t -> FilterValue.builder()
                        .name(t.name())
                        .value(t.getValue())
                        .build())
                    .toList();
            case IS_RELATIVE_TO_TODAY -> {
                List<FilterValue> values = new ArrayList<>();
                for (DateFilterPivotType pt : DateFilterPivotType.values()) {
                    for (DateFilterRangeType vt : DateFilterRangeType.values()) {
                        values.add(FilterValue.builder()
                            .name(pt.name() + " " + vt.name())
                            .value(pt.getValue() + " " + vt.getValue())
                            .build());
                    }
                }
                yield values;
            }
            default -> Collections.emptyList();
        };
    }
}
