package com.penta.security.employee.dto.response;

import com.penta.security.global.entity.Employee.Gender;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDetailInfoResponseDto {

    private Integer empNo;
    private LocalDate birthDate;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate hireDate;

    @Builder
    @QueryProjection
    public EmployeeDetailInfoResponseDto(
        Integer empNo, LocalDate birthDate,
        String firstName, String lastName,
        Gender gender, LocalDate hireDate
    ) {
        this.empNo = empNo;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.hireDate = hireDate;
    }
}
