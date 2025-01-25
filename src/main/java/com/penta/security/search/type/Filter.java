package com.penta.security.search.type;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import lombok.Builder;

@Builder(builderMethodName = "innerBuilder")
public record Filter(
    String componentName,
    String propertyName,
    FilterType type,
    Function<JPAQueryFactory, List<FilterValue>> valueFunction
) {

    public static class FilterBuilder {
    }

    public static FilterBuilder builder(
        String componentName, String propertyName, FilterType type) {

        Objects.requireNonNull(componentName);
        Objects.requireNonNull(propertyName);
        Objects.requireNonNull(type);

        return innerBuilder()
            .componentName(componentName)
            .propertyName(propertyName)
            .type(type);
    }
}