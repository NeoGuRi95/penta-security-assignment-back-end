package com.penta.security.search.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum DateFilterValueType {
    TODAY("Today", "today"),
    YESTERDAY("Yesterday", "yesterday"),
    TOMORROW("Tomorrow", "tomorrow"),
    ONE_WEEK_AGO("One week ago"),
    ONE_WEEK_FROM_NOW("One week from now"),
    ONE_MONTH_AGO("One month ago"),
    ONE_MONTH_FROM_NOW("One month from now"),
    CUSTOM_DATE("Custom date");

    private final String value;
    private List<String> alias = new ArrayList<>();

    DateFilterValueType(String... value) {
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
    public static DateFilterValueType from(String value) {
        for (DateFilterValueType type : DateFilterValueType.values()) {
            if (type.getValue().equals(value)
                || type.name().equals(value)
                || type.getAlias().contains(value)) {
                return type;
            }
        }
        return null;
    }
}
