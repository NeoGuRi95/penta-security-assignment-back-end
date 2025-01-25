package com.penta.security.search.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum DateFilterPivotType {

    PAST("지난", "Past", "past"),
    NEXT("다음", "Next", "next"),
    THIS("이번", "This", "this"),
    PAST_BEFORE("지지난", "pastBefore", "before"),
    NEXT_AFTER("다다음", "nextAfter", "after");

    private final String value;
    private List<String> alias = new ArrayList<>();

    DateFilterPivotType(String... value) {
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
    public static DateFilterPivotType from(String value) {
        for (DateFilterPivotType type : DateFilterPivotType.values()) {
            if (type.getValue().equals(value)
                || type.name().equals(value)
                || type.getAlias().contains(value)) {
                return type;
            }
        }
        return null;
    }
}
