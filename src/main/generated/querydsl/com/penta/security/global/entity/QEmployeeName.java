package com.penta.security.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmployeeName is a Querydsl query type for EmployeeName
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmployeeName extends EntityPathBase<EmployeeName> {

    private static final long serialVersionUID = 1457282674L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmployeeName employeeName = new QEmployeeName("employeeName");

    public final QEmployee employee;

    public final NumberPath<Integer> empNo = createNumber("empNo", Integer.class);

    public final StringPath firstName = createString("firstName");

    public final StringPath lastName = createString("lastName");

    public QEmployeeName(String variable) {
        this(EmployeeName.class, forVariable(variable), INITS);
    }

    public QEmployeeName(Path<? extends EmployeeName> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmployeeName(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmployeeName(PathMetadata metadata, PathInits inits) {
        this(EmployeeName.class, metadata, inits);
    }

    public QEmployeeName(Class<? extends EmployeeName> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new QEmployee(forProperty("employee"), inits.get("employee")) : null;
    }

}

