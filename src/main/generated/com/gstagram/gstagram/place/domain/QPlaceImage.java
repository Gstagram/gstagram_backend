package com.gstagram.gstagram.place.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaceImage is a Querydsl query type for PlaceImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaceImage extends EntityPathBase<PlaceImage> {

    private static final long serialVersionUID = -2140758774L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaceImage placeImage = new QPlaceImage("placeImage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageURL = createString("imageURL");

    public final QPlace place;

    public QPlaceImage(String variable) {
        this(PlaceImage.class, forVariable(variable), INITS);
    }

    public QPlaceImage(Path<? extends PlaceImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaceImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaceImage(PathMetadata metadata, PathInits inits) {
        this(PlaceImage.class, metadata, inits);
    }

    public QPlaceImage(Class<? extends PlaceImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.place = inits.isInitialized("place") ? new QPlace(forProperty("place"), inits.get("place")) : null;
    }

}

