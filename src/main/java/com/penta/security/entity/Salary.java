package com.penta.security.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "salaries")
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Salary {
    @EmbeddedId
    private SalaryId id;

    @Column(name = "salary", nullable = false)
    private Integer salary;

    @MapsId("empNo")
    @ManyToOne
    @JoinColumn(name = "emp_no")
    private Employee employee;

    // 연관 관계 편의 메서드 start
    public void setEmployee(Employee employee) {
        if (this.employee != null) {
            this.employee.getSalaries().remove(this);
        }
        this.employee = employee;
        if (employee != null && !employee.getSalaries().contains(this)) {
            employee.getSalaries().add(this);
        }
    }
    // 연관 관계 편의 메서드 end
}
