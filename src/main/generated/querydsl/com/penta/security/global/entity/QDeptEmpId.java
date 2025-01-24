package com.penta.security.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeptEmpId is a Querydsl query type for DeptEmpId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDeptEmpId extends BeanPath<DeptEmpId> {

    private static final long serialVersionUID = -1969962171L;

    public static final QDeptEmpId deptEmpId = new QDeptEmpId("deptEmpId");

    public final StringPath deptNo = createString("deptNo");

    public final NumberPath<Integer> empNo = createNumber("empNo", Integer.class);

    public QDeptEmpId(String variable) {
        super(DeptEmpId.class, forVariable(variable));
    }

    public QDeptEmpId(Path<? extends DeptEmpId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeptEmpId(PathMetadata metadata) {
        super(DeptEmpId.class, metadata);
    }

}

