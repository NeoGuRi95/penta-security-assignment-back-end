package com.penta.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DeptEmpId implements Serializable {
    @Column(name = "dept_no", columnDefinition = "CHAR(4)", nullable = false)
    private String deptNo;

    @Column(name = "emp_no", nullable = false)
    private Integer empNo;
}
