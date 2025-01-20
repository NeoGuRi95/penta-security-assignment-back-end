package com.penta.security.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
    @Id
    @Column(name = "dept_no", length = 4, nullable = false)
    private String deptNo;

    @Column(name = "dept_name", length = 40, nullable = false)
    private String deptName;

    @Column(name = "emp_count")
    private Integer empCount;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeptEmp> deptEmps;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeptManager> deptManagers;
}