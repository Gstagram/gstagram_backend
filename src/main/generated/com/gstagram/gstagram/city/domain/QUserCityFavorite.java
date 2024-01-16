package com.gstagram.gstagram.city.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserCityFavorite is a Querydsl query type for UserCityFavorite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserCityFavorite extends EntityPathBase<UserCityFavorite> {

    private static final long serialVersionUID = 1790659046L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserCityFavorite userCityFavorite = new QUserCityFavorite("userCityFavorite");

    public final QCity city;

    public final NumberPath<Long> cityId = createNumber("cityId", Long.class);

    public final com.gstagram.gstagram.user.domain.QUser user;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserCityFavorite(String variable) {
        this(UserCityFavorite.class, forVariable(variable), INITS);
    }

    public QUserCityFavorite(Path<? extends UserCityFavorite> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserCityFavorite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserCityFavorite(PathMetadata metadata, PathInits inits) {
        this(UserCityFavorite.class, metadata, inits);
    }

    public QUserCityFavorite(Class<? extends UserCityFavorite> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.city = inits.isInitialized("city") ? new QCity(forProperty("city"), inits.get("city")) : null;
        this.user = inits.isInitialized("user") ? new com.gstagram.gstagram.user.domain.QUser(forProperty("user")) : null;
    }

}

