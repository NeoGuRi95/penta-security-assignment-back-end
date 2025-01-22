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

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeptEmp> deptEmps = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeptManager> deptManagers = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Salary> salaries = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Title> titles = new ArrayList<>();

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private EmployeeDoc employeeDoc;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private EmployeeName employeeName;

    public enum Gender {
        M, F;
    }
}