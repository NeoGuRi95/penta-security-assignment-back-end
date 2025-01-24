package com.penta.security.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTitle is a Querydsl query type for Title
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTitle extends EntityPathBase<Title> {

    private static final long serialVersionUID = 1783467103L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTitle title = new QTitle("title");

    public final QEmployee employee;

    public final QTitleId id;

    public final DatePath<java.time.LocalDate> toDate = createDate("toDate", java.time.LocalDate.class);

    public QTitle(String variable) {
        this(Title.class, forVariable(variable), INITS);
    }

    public QTitle(Path<? extends Title> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTitle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTitle(PathMetadata metadata, PathInits inits) {
        this(Title.class, metadata, inits);
    }

    public QTitle(Class<? extends Title> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new QEmployee(forProperty("employee"), inits.get("employee")) : null;
        this.id = inits.isInitialized("id") ? new QTitleId(forProperty("id")) : null;
    }

}

