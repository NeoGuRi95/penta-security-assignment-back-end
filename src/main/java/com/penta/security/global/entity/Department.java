package com.penta.security.global.entity;

import com.penta.security.search.type.FilterValue;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Department {
    @Id
    @Column(name = "dept_no", columnDefinition = "CHAR(4)", nullable = false)
    private String deptNo;

    @Column(name = "dept_name", length = 40, nullable = false)
    private String deptName;

    @Column(name = "emp_count")
    private Integer empCount;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeptEmp> deptEmps;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeptManager> deptManagers;

    public static List<FilterValue> getDepartmentNames(JPAQueryFactory queryFactory) {
        QDepartment department = new QDepartment("department");
        return queryFactory
            .selectDistinct(department.deptName)
            .from(department)
            .orderBy(department.deptName.asc())
            .fetch()

            .stream()
            .map(t -> FilterValue.builder()
                .name(t)
                .value(t)
                .build())
            .toList();
    }

    // 연관 관계 편의 메서드 start
    public void addDeptEmp(DeptEmp deptEmp) {
        deptEmps.add(deptEmp);
        deptEmp.setDepartment(this);
    }

    public void removeDeptEmp(DeptEmp deptEmp) {
        deptEmps.remove(deptEmp);
        deptEmp.setDepartment(null);
    }

    public void addDeptManager(DeptManager deptManager) {
        deptManagers.add(deptManager);
        deptManager.setDepartment(this);
    }

    public void removeDeptManage(DeptManager deptManager) {
        deptManagers.remove(deptManager);
        deptManager.setDepartment(null);
    }
    // 연관 관계 편의 메서드 end
}