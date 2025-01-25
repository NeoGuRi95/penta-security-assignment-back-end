package com.penta.security.employee.repository;

import com.penta.security.employee.dto.response.EmployeeDetailInfoResponseDto;
import com.penta.security.global.entity.Employee.Gender;
import com.penta.security.search.FilterRegistry;
import com.penta.security.search.dto.FilterDto;
import com.penta.security.search.filter.DateFilter;
import com.penta.security.search.type.FilterType;
import com.penta.security.search.type.FilterValue;
import com.penta.security.search.filter.NumberFilter;
import com.penta.security.search.filter.SelectFilter;
import com.penta.security.search.filter.StringFilter;
import com.penta.security.search.repository.SearchRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.penta.security.global.entity.QEmployee.employee;

@Repository
@RequiredArgsConstructor
public class EmployeeSearchRepository extends SearchRepository {

    private final JPAQueryFactory queryFactory;
    private final FilterRegistry filterRegistry;

    @PostConstruct
    private void init() {
        String employee = "employee";
        if (!filterRegistry.isRegistered(employee)) {
            filterRegistry.register(employee, "empNo", FilterType.NUMBER);
            filterRegistry.register(employee, "firstName", FilterType.STRING);
            filterRegistry.register(employee, "lastName", FilterType.STRING);
            filterRegistry.register(employee, "gender", FilterType.SELECT,
                EmployeeSearchRepository::getGenderValues);
            filterRegistry.register(employee, "birthDate", FilterType.DATE);
            filterRegistry.register(employee, "hireDate", FilterType.DATE);
        }
    }

    public static List<FilterValue> getGenderValues(JPAQueryFactory jpaQueryFactory) {
        return Arrays.stream(Gender.values())
            .map(gender -> FilterValue.builder()
                .name(gender.getName())
                .value(gender.getValue())
                .build())
            .collect(Collectors.toList());
    }

    public List<EmployeeDetailInfoResponseDto> searchFilterSlice(
        Integer lastEmployeeNo, List<FilterDto> filters
    ) {
        return queryFactory
            .select(Projections.fields(EmployeeDetailInfoResponseDto.class,
                employee.empNo,
                employee.birthDate,
                employee.firstName,
                employee.lastName,
                employee.gender,
                employee.hireDate))
            .from(employee)
            .where(
                ltEmpNo(lastEmployeeNo),
                filter(filters)
            )
            .orderBy(employee.empNo.desc())
            .limit(20)
            .fetch();
    }

    private BooleanExpression ltEmpNo(Integer empNo) {
        if (empNo == null) {
            return null;
        }

        return employee.empNo.lt(empNo);
    }

    private BooleanExpression filter(List<FilterDto> filters) {
        if (filters == null || filters.isEmpty()) {
            return null;
        }

        List<BooleanExpression> expressions = filters.stream()
            .map(filter ->
                switch (filter.getPropertyName()) {
                    case "empNo" -> NumberFilter.filter(employee.empNo, filter);
                    case "birthDate" -> DateFilter.filter(employee.birthDate, filter);
                    case "firstName" -> StringFilter.filter(employee.firstName, filter);
                    case "lastName" -> StringFilter.filter(employee.lastName, filter);
                    case "gender" -> SelectFilter.filter(employee.gender, filter, Gender.class);
                    case "hireDate" -> DateFilter.filter(employee.hireDate, filter);
                    default -> null;
                })
            .toList();

        return combine(filters, expressions);
    }
}
