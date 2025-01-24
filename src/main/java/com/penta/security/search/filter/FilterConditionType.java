package com.penta.security.search.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FilterConditionType {
    // Nullable
    IS_EMPTY("isEmpty"),
    IS_NOT_EMPTY("isNotEmpty"),
    // Select
    IS("is"),
    IS_NOT("isNot"),
    // Multi Select
    IS_CONTAINS("isContains"),
    DOES_NOT_CONTAIN("doesNotContain"),
    // String
    START_WITH("startWith"),
    END_WITH("endWith"),
    // Number
    EQUAL("equal"),
    NOT_EQUAL("notEqual"),
    GREATER_THAN("greaterThan"),
    GREATER_THAN_OR_EQUAL("greaterThanOrEqual"),
    LESS_THAN("lessThan"),
    LESS_THAN_OT_EQUAL("lessThanOrEqual"),
    // Date
    IS_BEFORE("isBefore"),
    IS_AFTER("isAfter"),
    IS_ON_OR_BEFORE("isOnOrBefore"),
    IS_ON_OR_AFTER("isOnOrAfter"),
    IS_BETWEEN("isBetween"),
    IS_RELATIVE_TO_TODAY("isRelativeToToday");

    private final String value;

    FilterConditionType(String value) {
        this.value = value;
    }

    // server: enum class --convert--> client: json string
    @JsonValue
    public String getValue() {
        return value;
    }

    // client: json string --convert--> server: enum class
    @JsonCreator
    public static FilterConditionType from(String value) {
        for (FilterConditionType type : FilterConditionType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
