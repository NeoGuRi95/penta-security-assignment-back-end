package com.penta.security.search.controller;

import com.penta.security.search.dto.FilterComponentDto;
import com.penta.security.search.filter.Filter;
import com.penta.security.search.service.SearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/{componentName}")
    public ResponseEntity<FilterComponentDto> getFilterComponent(
        @PathVariable String componentName
    ) {
        FilterComponentDto filterComponentDto = searchService.getFilter(componentName);
        return ResponseEntity.ok(filterComponentDto);
    }
}
