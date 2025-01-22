package com.penta.security.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
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
    private List<DeptEmp> deptEmps = new ArrayList<>();

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeptManager> deptManagers = new ArrayList<>();

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Salary> salaries = new ArrayList<>();

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Title> titles = new ArrayList<>();

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private EmployeeDoc employeeDoc;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private EmployeeName employeeName;

    public enum Gender {
        M, F;
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