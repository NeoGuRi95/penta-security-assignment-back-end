package com.penta.security.search.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum DateFilterRangeType {
    DAY("일", "Day", "day"),
    WEEK("주", "Week", "week"),
    MONTH("월", "Month", "month"),
    YEAR("년", "Year", "year");

    private final String value;
    private List<String> alias = new ArrayList<>();

    DateFilterRangeType(String... value) {
        this.value = value[0];
        this.alias.addAll(Arrays.asList(value));
    }

    public List<String> getAlias() {
        return alias;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    // client: json string --convert--> server: enum class
    @JsonCreator
    public static DateFilterRangeType from(String value) {
        for (DateFilterRangeType type : DateFilterRangeType.values()) {
            if (type.getValue().equals(value)
                || type.name().equals(value)
                || type.getAlias().contains(value)) {
                return type;
            }
        }
        return null;
    }
}
