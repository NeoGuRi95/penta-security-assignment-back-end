package com.penta.security.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "salaries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Salary {
    @EmbeddedId
    private SalaryId id;

    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee employee;

    @Column(name = "salary", nullable = false)
    private Integer salary;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class SalaryId implements java.io.Serializable {
        private Integer empNo;
        private LocalDateTime fromDate;
    }
}
