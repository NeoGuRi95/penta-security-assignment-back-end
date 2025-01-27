package com.penta.security.global.entity;

import com.penta.security.search.type.FilterValue;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "titles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public static List<FilterValue> getTitleValues(JPAQueryFactory queryFactory) {
        QTitle t = new QTitle("t");
        return queryFactory
            .selectDistinct(t.id.title)
            .from(t)
            .fetch()

            .stream()
            .map(title -> FilterValue.builder()
                .name(title)
                .value(title)
                .build())
            .toList();
    }

    // 연관 관계 편의 메서드 start
    public void setEmployee(Employee employee) {
        if (this.employee != null) {
            this.employee.getTitles().remove(this);
        }
        this.employee = employee;
        if (employee != null && !employee.getTitles().contains(this)) {
            employee.getTitles().add(this);
        }
    }
    // 연관 관계 편의 메서드 end
}
