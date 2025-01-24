package com.penta.security.employee.repository;

import com.penta.security.global.entity.Employee.Gender;
import com.penta.security.search.FilterRegistry;
import com.penta.security.search.filter.FilterType;
import com.penta.security.search.filter.FilterValue;
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
public class EmployeeSearchRepository {

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
}
