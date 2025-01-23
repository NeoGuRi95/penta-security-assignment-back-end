package com.penta.security.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTitleId is a Querydsl query type for TitleId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QTitleId extends BeanPath<TitleId> {

    private static final long serialVersionUID = -25215359L;

    public static final QTitleId titleId = new QTitleId("titleId");

    public final NumberPath<Integer> empNo = createNumber("empNo", Integer.class);

    public final DatePath<java.time.LocalDate> fromDate = createDate("fromDate", java.time.LocalDate.class);

    public final StringPath title = createString("title");

    public QTitleId(String variable) {
        super(TitleId.class, forVariable(variable));
    }

    public QTitleId(Path<? extends TitleId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTitleId(PathMetadata metadata) {
        super(TitleId.class, metadata);
    }

}

