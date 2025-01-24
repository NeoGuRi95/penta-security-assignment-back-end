package com.penta.security.global.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "dept_manager")
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DeptManager {
    @EmbeddedId
    private DeptManagerId id;

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

    // 연관 관계 편의 메서드 start
    public void setDepartment(Department department) {
        if (this.department != null) {
            this.department.getDeptManagers().remove(this);
        }
        this.department = department;
        if (department != null && !department.getDeptManagers().contains(this)) {
            department.getDeptManagers().add(this);
        }
    }

    public void setEmployee(Employee employee) {
        if (this.employee != null) {
            this.employee.getDeptManagers().remove(this);
        }
        this.employee = employee;
        if (employee != null && !employee.getDeptManagers().contains(this)) {
            employee.getDeptManagers().add(this);
        }
    }
    // 연관 관계 편의 메서드 end
}