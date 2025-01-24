package com.penta.security.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserHistory is a Querydsl query type for UserHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserHistory extends EntityPathBase<UserHistory> {

    private static final long serialVersionUID = -519090416L;

    public static final QUserHistory userHistory = new QUserHistory("userHistory");

    public final EnumPath<UserHistory.ActionType> actionType = createEnum("actionType", UserHistory.ActionType.class);

    public final NumberPath<Integer> historyIdx = createNumber("historyIdx", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final StringPath regIp = createString("regIp");

    public final NumberPath<Integer> regUserIdx = createNumber("regUserIdx", Integer.class);

    public final StringPath url = createString("url");

    public QUserHistory(String variable) {
        super(UserHistory.class, forVariable(variable));
    }

    public QUserHistory(Path<? extends UserHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserHistory(PathMetadata metadata) {
        super(UserHistory.class, metadata);
    }

}

