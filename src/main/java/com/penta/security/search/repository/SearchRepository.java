package com.penta.security.search.repository;

import com.penta.security.search.FilterRegistry;
import com.penta.security.search.dto.FilterDto;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class SearchRepository<T, R> {

    private final JPAQueryFactory queryFactory;

    @PostConstruct
    protected abstract void init();

    protected List<R> searchFilterSlice(
        T entity,
        Class<R> dtoClass,
        List<Expression<?>> projections,
        Integer lastIndex,
        List<FilterDto> filters
    ) {
        return queryFactory
            .select(Projections.fields(dtoClass, projections.toArray(new Expression[0])))
            .from((EntityPath<?>) entity)
            .where(
                applyLastIndexFilter(lastIndex),
                applyFilter(filters)
            )
            .orderBy(applySort())
            .limit(20)
            .fetch();
    }

    // 인덱스 적용 조건 생성
    protected abstract BooleanExpression applyLastIndexFilter(Integer lastIndex);

    // 동적 필터 조건 생성
    protected abstract BooleanExpression applyFilter(List<FilterDto> filters);

    // 정렬 조건 생성
    protected abstract OrderSpecifier<?> applySort();

    protected BooleanExpression combine(
        List<FilterDto> filter,
        List<BooleanExpression> expressions
    ) {

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
