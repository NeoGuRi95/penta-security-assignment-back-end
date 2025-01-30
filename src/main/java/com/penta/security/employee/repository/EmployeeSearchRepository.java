package com.penta.security.employee.repository;

import com.penta.security.employee.dto.response.EmployeeDetailInfoResponseDto;
import com.penta.security.global.entity.Department;
import com.penta.security.global.entity.Employee.Gender;
import com.penta.security.global.entity.Title;
import com.penta.security.search.FilterRegistry;
import com.penta.security.search.dto.FilterDto;
import com.penta.security.search.filter.DateFilter;
import com.penta.security.search.type.FilterType;
import com.penta.security.search.filter.NumberFilter;
import com.penta.security.search.filter.SelectFilter;
import com.penta.security.search.filter.StringFilter;
import com.penta.security.search.repository.SearchRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.penta.security.global.entity.QEmployee.employee;
import static com.penta.security.global.entity.QSalary.salary1;
import static com.penta.security.global.entity.QTitle.title;
import static com.penta.security.global.entity.QDeptEmp.deptEmp;
import static com.penta.security.global.entity.QDepartment.department;

@Repository
@RequiredArgsConstructor
public class EmployeeSearchRepository extends SearchRepository {

    private final FilterRegistry filterRegistry;
    private final JPAQueryFactory queryFactory;

    @Override
    protected void init() {
        String entityName = "employee";
        filterRegistry.register(entityName, "empNo", FilterType.NUMBER);
        filterRegistry.register(entityName, "firstName", FilterType.STRING);
        filterRegistry.register(entityName, "lastName", FilterType.STRING);
        filterRegistry.register(entityName, "birthDate", FilterType.DATE);
        filterRegistry.register(entityName, "hireDate", FilterType.DATE);
        filterRegistry.register(entityName, "gender", FilterType.SELECT,
            null, Gender.getGenderValues());
        filterRegistry.register(entityName, "salary", FilterType.NUMBER);
        filterRegistry.register(entityName, "title", FilterType.SELECT,
            Title::getTitleValues, null);
        filterRegistry.register(entityName, "deptName", FilterType.SELECT,
            Department::getDepartmentNames, null);
    }

    public List<EmployeeDetailInfoResponseDto> searchFilterSlice(
        Integer lastEmployeeNo,
        List<FilterDto> filters
    ) {
        return queryFactory
            .select(Projections.fields(EmployeeDetailInfoResponseDto.class,
                employee.empNo,
                employee.birthDate,
                employee.firstName,
                employee.lastName,
                employee.gender,
                employee.hireDate,
                salary1.salary,
                title.id.title,
                department.deptName))
            .from(employee)
            .innerJoin(employee.salaries, salary1)
            .innerJoin(employee.titles, title)
            .innerJoin(employee.deptEmps, deptEmp)
            .innerJoin(deptEmp.department, department)
            .where(
                applyLastIndexFilter(lastEmployeeNo),
                applyFilter(filters)
            )
            .orderBy(applySort())
            .limit(20)
            .fetch();
    }

    @Override
    protected BooleanExpression applyLastIndexFilter(Integer empNo) {
        if (empNo == null) {
            return null;
        }

        return employee.empNo.lt(empNo);
    }

    @Override
    protected BooleanExpression applyFilter(List<FilterDto> filters) {
        List<BooleanExpression> expressions = new ArrayList<>(filters.stream()
            .map(filter ->
                switch (filter.getPropertyName()) {
                    case "empNo" -> NumberFilter.filter(employee.empNo, filter);
                    case "birthDate" -> DateFilter.filter(employee.birthDate, filter);
                    case "firstName" -> StringFilter.filter(employee.firstName, filter);
                    case "lastName" -> StringFilter.filter(employee.lastName, filter);
                    case "gender" -> SelectFilter.filter(employee.gender, filter, Gender.class);
                    case "hireDate" -> DateFilter.filter(employee.hireDate, filter);
                    case "salary" -> NumberFilter.filter(salary1.salary, filter);
                    case "title" -> SelectFilter.filter(title.id.title, filter);
                    case "deptName" -> SelectFilter.filter(department.deptName, filter);
                    default -> null;
                })
            .toList());

        expressions.add(salary1.toDate.eq(LocalDate.of(9999, 1, 1)));
        expressions.add(title.toDate.eq(LocalDate.of(9999, 1, 1)));
        expressions.add(deptEmp.toDate.eq(LocalDate.of(9999, 1, 1)));

        return combine(expressions);
    }

    @Override
    protected OrderSpecifier<?> applySort() {
        return employee.empNo.desc();
    }
}
