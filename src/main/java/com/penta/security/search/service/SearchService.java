package com.penta.security.search.service;

import com.penta.security.search.FilterRegistry;
import com.penta.security.search.dto.FilterComponentDto;
import com.penta.security.search.dto.FilterPropertyDto;
import com.penta.security.search.type.Filter;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {

    private final FilterRegistry filterRegistry;
    private final JPAQueryFactory jpaQueryFactory;

    public FilterComponentDto getFilter(String componentName) {

        if (!StringUtils.hasText(componentName)) {
            return null;
        }

        Map<String, Filter> componentFilterMap = filterRegistry.get(componentName);

        return FilterComponentDto.builder()
            .componentName(componentName)
            .properties(componentFilterMap.entrySet().stream()
                .map(e -> {
                    Filter filter = e.getValue();
                    return FilterPropertyDto.builder()
                        .propertyName(e.getKey())
                        .filterType(filter.type())
                        .filterConditionTypes(filter.type().getFilterConditionTypes())
                        .filterValues(filter.valueFunction() != null
                            ? filter.valueFunction().apply(jpaQueryFactory)
                            : Collections.emptyList())
                        .build();
                }).toList())
            .build();
    }
}
