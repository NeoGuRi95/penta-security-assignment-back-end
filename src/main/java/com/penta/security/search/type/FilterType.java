package com.penta.security.search.type;

import static com.penta.security.search.type.FilterConditionType.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.List;

public enum FilterType {
    DATE("date", Arrays.asList(
        IS, IS_NOT,
        IS_BEFORE, IS_AFTER,
        IS_ON_OR_BEFORE, IS_ON_OR_AFTER,
        IS_BETWEEN, IS_RELATIVE_TO_TODAY,
        IS_EMPTY, IS_NOT_EMPTY
    )),
    SELECT("select", Arrays.asList(
        IS, IS_NOT,
        IS_EMPTY, IS_NOT_EMPTY
    )),
    MULTI_SELECT("multiSelect", Arrays.asList(
        IS, IS_NOT,
        IS_CONTAINS, DOES_NOT_CONTAIN,
        IS_EMPTY, IS_NOT_EMPTY
    )),
    STRING("string", Arrays.asList(
        IS, IS_NOT,
        IS_CONTAINS, DOES_NOT_CONTAIN,
        START_WITH, END_WITH,
        IS_EMPTY, IS_NOT_EMPTY
    )),
    NUMBER("number", Arrays.asList(
        EQUAL, NOT_EQUAL,
        GREATER_THAN, GREATER_THAN_OR_EQUAL,
        LESS_THAN, LESS_THAN_OR_EQUAL,
        IS_EMPTY, IS_NOT_EMPTY
    ));

    private final String value;
    private final List<FilterConditionType> filterConditionTypes;

    FilterType(String value, List<FilterConditionType> filterConditionTypes) {
        this.value = value;
        this.filterConditionTypes = filterConditionTypes;
    }

    public List<FilterConditionType> getFilterConditionTypes() {
        return filterConditionTypes;
    }

    // server: enum class --convert--> client: json string
    @JsonValue
    public String getValue() {
        return value;
    }

    // client: json string --convert--> server: enum class
    @JsonCreator
    public static FilterType from(String value) {
        for (FilterType type : FilterType.values()) {
            if (type.getValue().equals(value) || type.name().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
