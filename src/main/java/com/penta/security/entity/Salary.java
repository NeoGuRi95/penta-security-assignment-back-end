package com.penta.security.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "salaries")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
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
}
