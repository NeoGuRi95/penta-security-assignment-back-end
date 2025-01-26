package com.penta.security.global.entity;

import com.penta.security.search.type.FilterValue;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_no", nullable = false)
    private Integer empNo;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "first_name", length = 14, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 16, nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeptEmp> deptEmps;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeptManager> deptManagers;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Salary> salaries;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Title> titles;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private EmployeeDoc employeeDoc;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private EmployeeName employeeName;

    @Getter
    public enum Gender {
        M("남성", "M"),
        F("여성", "F");

        private final String name;
        private final String value;

        Gender(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public static List<FilterValue> getGenderValues() {
            return Arrays.stream(Gender.values())
                .map(gender -> FilterValue.builder()
                    .name(gender.getName())
                    .value(gender.getValue())
                    .build())
                .collect(Collectors.toList());
        }
    }

    // 연관 관계 편의 메서드 start
    public void addDeptEmp(DeptEmp deptEmp) {
        this.deptEmps.add(deptEmp);
        deptEmp.setEmployee(this);
    }

    public void removeDeptEmp(DeptEmp deptEmp) {
        this.deptEmps.remove(deptEmp);
        deptEmp.setEmployee(null);
    }

    public void addDeptManager(DeptManager deptManager) {
        this.deptManagers.add(deptManager);
        deptManager.setEmployee(this);
    }

    public void removeDeptManager(DeptManager deptManager) {
        this.deptManagers.remove(deptManager);
        deptManager.setEmployee(null);
    }

    public void addSalary(Salary salary) {
        this.salaries.add(salary);
        salary.setEmployee(this);
    }

    public void removeSalary(Salary salary) {
        this.salaries.remove(salary);
        salary.setEmployee(null);
    }

    public void addTitle(Title title) {
        this.titles.add(title);
        title.setEmployee(this);
    }

    public void removeTitle(Title title) {
        this.titles.remove(title);
        title.setEmployee(null);
    }

    public void setEmployeeDoc(EmployeeDoc employeeDoc) {
        if (this.employeeDoc != null) {
            this.employeeDoc.setEmployee(null);
        }
        this.employeeDoc = employeeDoc;
        if (employeeDoc != null) {
            employeeDoc.setEmployee(this);
        }
    }

    public void setEmployeeName(EmployeeName employeeName) {
        if (this.employeeName != null) {
            this.employeeName.setEmployee(null);
        }
        this.employeeName = employeeName;
        if (employeeName != null) {
            employeeName.setEmployee(this);
        }
    }
    // 연관 관계 편의 메서드 end
}