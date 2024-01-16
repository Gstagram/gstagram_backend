package com.gstagram.gstagram.city.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserCity is a Querydsl query type for UserCity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserCity extends EntityPathBase<UserCity> {

    private static final long serialVersionUID = 1128982058L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserCity userCity = new QUserCity("userCity");

    public final QCity city;

    public final NumberPath<Long> cityId = createNumber("cityId", Long.class);

    public final StringPath regionRegisteredDate = createString("regionRegisteredDate");

    public final com.gstagram.gstagram.user.domain.QUser user;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserCity(String variable) {
        this(UserCity.class, forVariable(variable), INITS);
    }

    public QUserCity(Path<? extends UserCity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserCity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserCity(PathMetadata metadata, PathInits inits) {
        this(UserCity.class, metadata, inits);
    }

    public QUserCity(Class<? extends UserCity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.city = inits.isInitialized("city") ? new QCity(forProperty("city"), inits.get("city")) : null;
        this.user = inits.isInitialized("user") ? new com.gstagram.gstagram.user.domain.QUser(forProperty("user")) : null;
    }

}

