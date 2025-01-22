package com.penta.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DeptManagerId implements Serializable {
    @Column(name = "dept_no", columnDefinition = "CHAR(4)", nullable = false)
    private String deptNo;

    @Column(name = "emp_no", nullable = false)
    private Integer empNo;
}
