package com.gstagram.gstagram.badge.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserBadge is a Querydsl query type for UserBadge
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserBadge extends EntityPathBase<UserBadge> {

    private static final long serialVersionUID = -450193338L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserBadge userBadge = new QUserBadge("userBadge");

    public final QBadge badge;

    public final NumberPath<Long> badgeId = createNumber("badgeId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> getDate = createDateTime("getDate", java.time.LocalDateTime.class);

    public final com.gstagram.gstagram.user.domain.QUser user;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserBadge(String variable) {
        this(UserBadge.class, forVariable(variable), INITS);
    }

    public QUserBadge(Path<? extends UserBadge> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserBadge(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserBadge(PathMetadata metadata, PathInits inits) {
        this(UserBadge.class, metadata, inits);
    }

    public QUserBadge(Class<? extends UserBadge> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.badge = inits.isInitialized("badge") ? new QBadge(forProperty("badge")) : null;
        this.user = inits.isInitialized("user") ? new com.gstagram.gstagram.user.domain.QUser(forProperty("user")) : null;
    }

}

