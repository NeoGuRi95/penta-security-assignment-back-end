package com.penta.security.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSalaryId is a Querydsl query type for SalaryId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSalaryId extends BeanPath<SalaryId> {

    private static final long serialVersionUID = 2031422750L;

    public static final QSalaryId salaryId = new QSalaryId("salaryId");

    public final NumberPath<Integer> empNo = createNumber("empNo", Integer.class);

    public final DatePath<java.time.LocalDate> fromDate = createDate("fromDate", java.time.LocalDate.class);

    public QSalaryId(String variable) {
        super(SalaryId.class, forVariable(variable));
    }

    public QSalaryId(Path<? extends SalaryId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSalaryId(PathMetadata metadata) {
        super(SalaryId.class, metadata);
    }

}

