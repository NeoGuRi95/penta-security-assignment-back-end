package com.penta.security.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeptManagerId is a Querydsl query type for DeptManagerId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDeptManagerId extends BeanPath<DeptManagerId> {

    private static final long serialVersionUID = 678403921L;

    public static final QDeptManagerId deptManagerId = new QDeptManagerId("deptManagerId");

    public final StringPath deptNo = createString("deptNo");

    public final NumberPath<Integer> empNo = createNumber("empNo", Integer.class);

    public QDeptManagerId(String variable) {
        super(DeptManagerId.class, forVariable(variable));
    }

    public QDeptManagerId(Path<? extends DeptManagerId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeptManagerId(PathMetadata metadata) {
        super(DeptManagerId.class, metadata);
    }

}

