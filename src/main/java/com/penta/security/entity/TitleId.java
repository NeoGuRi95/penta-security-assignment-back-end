package com.penta.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TitleId implements Serializable {
    @Column(name = "emp_no", nullable = false)
    private Integer empNo;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "title", length = 50, nullable = false)
    private String title;
}
