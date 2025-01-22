package com.penta.security.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "titles")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Title {
    @EmbeddedId
    private TitleId id;

    @Column(name = "to_date")
    private LocalDate toDate;

    @MapsId("empNo")
    @ManyToOne
    @JoinColumn(name = "emp_no")
    private Employee employee;
}
