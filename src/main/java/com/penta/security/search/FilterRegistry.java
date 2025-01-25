package com.penta.security.search;

import com.penta.security.search.type.Filter;
import com.penta.security.search.type.FilterType;
import com.penta.security.search.type.FilterValue;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FilterRegistry {

    private static final Map<String, Map<String, Filter>>
        FILTER_MAP = new ConcurrentHashMap<>();

    public Map<String, Filter> get(String componentName) {

        return FILTER_MAP.getOrDefault(componentName, Collections.emptyMap());
    }

    public Filter get(String componentName, String propertyName) {

        return get(componentName).getOrDefault(propertyName, null);
    }

    public void register(String componentName, String propertyName, FilterType filterType)
        throws IllegalArgumentException {

        register(componentName, propertyName, filterType, null);
    }

    public void register(String componentName, String propertyName, FilterType filterType,
        Function<JPAQueryFactory, List<FilterValue>> valueFunction)
        throws IllegalArgumentException {

        if (!StringUtils.hasText(componentName)) {
            throw new IllegalArgumentException("The component name is required.");
        }
        if (!StringUtils.hasText(propertyName)) {
            throw new IllegalArgumentException("The property name is required.");
        }
        Objects.requireNonNull(filterType, "The filter type is required.");

        Filter filter = Filter.builder(componentName, propertyName, filterType)
            .valueFunction(valueFunction)
            .build();

        Map<String, Filter> propMap =
            FILTER_MAP.getOrDefault(componentName, new ConcurrentHashMap<>());
        propMap.put(propertyName, filter);
        FILTER_MAP.put(componentName, propMap);
    }

    public Map<String, Filter> deregister(String componentName) {

        return isRegistered(componentName)
            ? FILTER_MAP.remove(componentName)
            : null;
    }

    public Filter deregister(String componentName, String propertyName) {

        return isRegistered(componentName, propertyName)
            ? FILTER_MAP.get(componentName).remove(propertyName)
            : null;
    }

    public boolean isRegistered(String componentName) {

        if (!StringUtils.hasText(componentName)) {
            return false;
        }

        return FILTER_MAP.containsKey(componentName);
    }

    public boolean isRegistered(String componentName, String propertyName) {

        if (!StringUtils.hasText(componentName)) {
            return false;
        }
        if (!StringUtils.hasText(propertyName)) {
            return false;
        }

        Map<String, Filter> propMap =
            FILTER_MAP.getOrDefault(componentName, Collections.emptyMap());
        return propMap.containsKey(propertyName);
    }
}
