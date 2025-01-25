package com.penta.security.employee.controller;

import com.penta.security.employee.dto.response.EmployeeDetailInfoResponseDto;
import com.penta.security.employee.service.EmployeeSearchServicee;
import com.penta.security.search.dto.FilterDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeSearchServicee employeeSearchServicee;

    @GetMapping("/filter/slice")
    public List<EmployeeDetailInfoResponseDto> getEmployeesWithFilterSlice(
        @RequestParam(value = "lastEmployeeNo", required = false) Integer lastEmployeeNo,
        @RequestBody(required = false) List<FilterDto> filters
    ) {
        return employeeSearchServicee.searchFilterSlice(lastEmployeeNo, filters);
    }
}
