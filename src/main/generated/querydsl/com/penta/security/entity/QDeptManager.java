package com.penta.security.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeptManager is a Querydsl query type for DeptManager
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeptManager extends EntityPathBase<DeptManager> {

    private static final long serialVersionUID = 443163542L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeptManager deptManager = new QDeptManager("deptManager");

    public final QDepartment department;

    public final QEmployee employee;

    public final DatePath<java.time.LocalDate> fromDate = createDate("fromDate", java.time.LocalDate.class);

    public final QDeptManagerId id;

    public final DatePath<java.time.LocalDate> toDate = createDate("toDate", java.time.LocalDate.class);

    public QDeptManager(String variable) {
        this(DeptManager.class, forVariable(variable), INITS);
    }

    public QDeptManager(Path<? extends DeptManager> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeptManager(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeptManager(PathMetadata metadata, PathInits inits) {
        this(DeptManager.class, metadata, inits);
    }

    public QDeptManager(Class<? extends DeptManager> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.department = inits.isInitialized("department") ? new QDepartment(forProperty("department")) : null;
        this.employee = inits.isInitialized("employee") ? new QEmployee(forProperty("employee"), inits.get("employee")) : null;
        this.id = inits.isInitialized("id") ? new QDeptManagerId(forProperty("id")) : null;
    }

}

