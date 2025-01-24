package com.penta.security.global.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee_name")
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EmployeeName {
    @Id
    @Column(name = "emp_no", nullable = false)
    private Integer empNo;

    @OneToOne
    @MapsId
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @Column(name = "first_name", length = 14, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 16, nullable = false)
    private String lastName;

    // 연관 관계 편의 메서드 start
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // 연관 관계 편의 메서드 end
}