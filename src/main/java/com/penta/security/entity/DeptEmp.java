package com.penta.security.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
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
    @JoinColumn(name = "dept_no", nullable = false)
    private Department department;

    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee employee;

    @Column(name = "from_date", nullable = false)
    private LocalDateTime fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDateTime toDate;

    @Embeddable
    @Getter
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeptEmpId implements Serializable {
        private String deptNo;
        private Integer empNo;
    }
}
