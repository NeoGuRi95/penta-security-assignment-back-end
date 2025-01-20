package com.penta.security.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee_docs")
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDoc {
    @Id
    @Column(name = "emp_no", nullable = false)
    private Integer empNo;

    @OneToOne
    @MapsId
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @Column(name = "doc", columnDefinition = "json")
    private String doc;
}
