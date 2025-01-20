package com.penta.security.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "titles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Title {
    @EmbeddedId
    private TitleId id;

    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee employee;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "to_date")
    private LocalDateTime toDate;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class TitleId implements java.io.Serializable {
        private Integer empNo;
        private LocalDateTime fromDate;
        private String title;
    }
}
