package com.penta.security.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSystemUser is a Querydsl query type for SystemUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSystemUser extends EntityPathBase<SystemUser> {

    private static final long serialVersionUID = -1429271828L;

    public static final QSystemUser systemUser = new QSystemUser("systemUser");

    public final StringPath userAuth = createString("userAuth");

    public final StringPath userId = createString("userId");

    public final NumberPath<Integer> userIdx = createNumber("userIdx", Integer.class);

    public final StringPath userNm = createString("userNm");

    public final StringPath userPw = createString("userPw");

    public QSystemUser(String variable) {
        super(SystemUser.class, forVariable(variable));
    }

    public QSystemUser(Path<? extends SystemUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSystemUser(PathMetadata metadata) {
        super(SystemUser.class, metadata);
    }

}

