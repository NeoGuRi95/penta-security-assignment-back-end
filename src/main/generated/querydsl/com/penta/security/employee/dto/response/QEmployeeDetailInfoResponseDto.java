package com.penta.security.employee.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.penta.security.employee.dto.response.QEmployeeDetailInfoResponseDto is a Querydsl Projection type for EmployeeDetailInfoResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QEmployeeDetailInfoResponseDto extends ConstructorExpression<EmployeeDetailInfoResponseDto> {

    private static final long serialVersionUID = -1510199718L;

    public QEmployeeDetailInfoResponseDto(com.querydsl.core.types.Expression<Integer> empNo, com.querydsl.core.types.Expression<java.time.LocalDate> birthDate, com.querydsl.core.types.Expression<String> firstName, com.querydsl.core.types.Expression<String> lastName, com.querydsl.core.types.Expression<com.penta.security.global.entity.Employee.Gender> gender, com.querydsl.core.types.Expression<java.time.LocalDate> hireDate) {
        super(EmployeeDetailInfoResponseDto.class, new Class<?>[]{int.class, java.time.LocalDate.class, String.class, String.class, com.penta.security.global.entity.Employee.Gender.class, java.time.LocalDate.class}, empNo, birthDate, firstName, lastName, gender, hireDate);
    }

}

