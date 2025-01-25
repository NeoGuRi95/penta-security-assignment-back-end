package com.penta.security.employee.service;

import com.penta.security.employee.dto.response.EmployeeDetailInfoResponseDto;
import com.penta.security.employee.repository.EmployeeSearchRepository;
import com.penta.security.search.dto.FilterDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployeeSearchServicee {

    private final EmployeeSearchRepository employeeSearchRepository;

    public List<EmployeeDetailInfoResponseDto> searchFilterSlice(
        Integer lastEmployeeNo, List<FilterDto> filters) {
        return employeeSearchRepository.searchFilterSlice(lastEmployeeNo, filters);
    }
}
