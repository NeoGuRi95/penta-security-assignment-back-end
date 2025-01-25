package com.penta.security.search.dto;

import com.penta.security.search.type.FilterConditionType;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilterDto {

    private String propertyName;
    private FilterConditionType condition;
    private List<String> values;
}
