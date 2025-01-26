package com.penta.security.search.repository;

import com.penta.security.search.dto.FilterDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.annotation.PostConstruct;
import java.util.List;

public abstract class SearchRepository {

    @PostConstruct
    protected abstract void init();

    // 인덱스 적용 조건 생성
    protected abstract BooleanExpression applyLastIndexFilter(Integer lastIndex);

    // 동적 필터 조건 생성
    protected abstract BooleanExpression applyFilter(List<FilterDto> filters);

    // 정렬 조건 생성
    protected abstract OrderSpecifier<?> applySort();

    protected BooleanExpression combine(
        List<BooleanExpression> expressions
    ) {

        BooleanExpression resultExp = null;
        int i = 0;

        // find first non-null expression
        for (; i < expressions.size() && resultExp == null; i++) {
            resultExp = expressions.get(i);
        }

        // operating expressions
        for (; i < expressions.size(); i++) {
            BooleanExpression currExp = expressions.get(i);
            if (currExp == null) {
                continue;
            }
            resultExp = resultExp.and(currExp);
        }

        return resultExp;
    }
}
