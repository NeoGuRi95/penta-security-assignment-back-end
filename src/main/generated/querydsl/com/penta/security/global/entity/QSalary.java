package com.penta.security.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSalary is a Querydsl query type for Salary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSalary extends EntityPathBase<Salary> {

    private static final long serialVersionUID = -583360349L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSalary salary1 = new QSalary("salary1");

    public final QEmployee employee;

    public final QSalaryId id;

    public final NumberPath<Integer> salary = createNumber("salary", Integer.class);

    public QSalary(String variable) {
        this(Salary.class, forVariable(variable), INITS);
    }

    public QSalary(Path<? extends Salary> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSalary(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSalary(PathMetadata metadata, PathInits inits) {
        this(Salary.class, metadata, inits);
    }

    public QSalary(Class<? extends Salary> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new QEmployee(forProperty("employee"), inits.get("employee")) : null;
        this.id = inits.isInitialized("id") ? new QSalaryId(forProperty("id")) : null;
    }

}

