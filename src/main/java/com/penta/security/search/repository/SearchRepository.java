package com.penta.security.search.repository;

import com.penta.security.search.dto.FilterDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.List;

public class SearchRepository {

    public BooleanExpression combine(
        List<FilterDto> filter, List<BooleanExpression> expressions) {

        if (filter == null || filter.isEmpty()) {
            return null;
        }

        BooleanExpression resultExp = null;
        int i = 0;

        // find first non-null expression
        for (; i < filter.size() && resultExp == null; ++i) {
            resultExp = expressions.get(i);
        }

        // operating expressions
        for (; i < filter.size(); ++i) {
            FilterDto currFilter = filter.get(i);
            BooleanExpression currExp = expressions.get(i);
            if (currExp == null) {
                continue;
            }
            resultExp = resultExp.and(currExp);
        }

        return resultExp;
    }
}
