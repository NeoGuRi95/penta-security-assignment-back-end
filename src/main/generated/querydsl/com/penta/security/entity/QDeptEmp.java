package com.penta.security.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeptEmp is a Querydsl query type for DeptEmp
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeptEmp extends EntityPathBase<DeptEmp> {

    private static final long serialVersionUID = -1458374351L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeptEmp deptEmp = new QDeptEmp("deptEmp");

    public final QDepartment department;

    public final QEmployee employee;

    public final DatePath<java.time.LocalDate> fromDate = createDate("fromDate", java.time.LocalDate.class);

    public final QDeptEmpId id;

    public final DatePath<java.time.LocalDate> toDate = createDate("toDate", java.time.LocalDate.class);

    public QDeptEmp(String variable) {
        this(DeptEmp.class, forVariable(variable), INITS);
    }

    public QDeptEmp(Path<? extends DeptEmp> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeptEmp(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeptEmp(PathMetadata metadata, PathInits inits) {
        this(DeptEmp.class, metadata, inits);
    }

    public QDeptEmp(Class<? extends DeptEmp> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.department = inits.isInitialized("department") ? new QDepartment(forProperty("department")) : null;
        this.employee = inits.isInitialized("employee") ? new QEmployee(forProperty("employee"), inits.get("employee")) : null;
        this.id = inits.isInitialized("id") ? new QDeptEmpId(forProperty("id")) : null;
    }

}

