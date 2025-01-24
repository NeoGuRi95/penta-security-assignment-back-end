package com.penta.security.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmployeeDoc is a Querydsl query type for EmployeeDoc
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmployeeDoc extends EntityPathBase<EmployeeDoc> {

    private static final long serialVersionUID = -230094735L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmployeeDoc employeeDoc = new QEmployeeDoc("employeeDoc");

    public final StringPath doc = createString("doc");

    public final QEmployee employee;

    public final NumberPath<Integer> empNo = createNumber("empNo", Integer.class);

    public QEmployeeDoc(String variable) {
        this(EmployeeDoc.class, forVariable(variable), INITS);
    }

    public QEmployeeDoc(Path<? extends EmployeeDoc> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmployeeDoc(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmployeeDoc(PathMetadata metadata, PathInits inits) {
        this(EmployeeDoc.class, metadata, inits);
    }

    public QEmployeeDoc(Class<? extends EmployeeDoc> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new QEmployee(forProperty("employee"), inits.get("employee")) : null;
    }

}

