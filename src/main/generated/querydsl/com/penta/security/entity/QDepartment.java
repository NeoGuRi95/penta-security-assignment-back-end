package com.penta.security.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDepartment is a Querydsl query type for Department
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDepartment extends EntityPathBase<Department> {

    private static final long serialVersionUID = -1224004700L;

    public static final QDepartment department = new QDepartment("department");

    public final ListPath<DeptEmp, QDeptEmp> deptEmps = this.<DeptEmp, QDeptEmp>createList("deptEmps", DeptEmp.class, QDeptEmp.class, PathInits.DIRECT2);

    public final ListPath<DeptManager, QDeptManager> deptManagers = this.<DeptManager, QDeptManager>createList("deptManagers", DeptManager.class, QDeptManager.class, PathInits.DIRECT2);

    public final StringPath deptName = createString("deptName");

    public final StringPath deptNo = createString("deptNo");

    public final NumberPath<Integer> empCount = createNumber("empCount", Integer.class);

    public QDepartment(String variable) {
        super(Department.class, forVariable(variable));
    }

    public QDepartment(Path<? extends Department> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDepartment(PathMetadata metadata) {
        super(Department.class, metadata);
    }

}

