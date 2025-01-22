package com.penta.security.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "dept_emp")
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeptEmp {
    @EmbeddedId
    private DeptEmpId id;

    @ManyToOne
    @MapsId("deptNo")
    @JoinColumn(name = "dept_no", columnDefinition = "char(4)")
    private Department department;

    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;
}
