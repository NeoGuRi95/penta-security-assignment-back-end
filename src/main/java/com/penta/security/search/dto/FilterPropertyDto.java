package com.penta.security.search.dto;

import com.penta.security.search.type.FilterConditionType;
import com.penta.security.search.type.FilterType;
import com.penta.security.search.type.FilterValue;
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
public class FilterPropertyDto {

    private String propertyName;
    private FilterType filterType;
    private List<FilterConditionType> filterConditionTypes;
    private List<FilterValue> filterValues;
}
