package com.gstagram.gstagram.booklet.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookletCaption is a Querydsl query type for BookletCaption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookletCaption extends EntityPathBase<BookletCaption> {

    private static final long serialVersionUID = 1162760949L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookletCaption bookletCaption = new QBookletCaption("bookletCaption");

    public final QBooklet booklet;

    public final StringPath caption = createString("caption");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath s3Url = createString("s3Url");

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public QBookletCaption(String variable) {
        this(BookletCaption.class, forVariable(variable), INITS);
    }

    public QBookletCaption(Path<? extends BookletCaption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookletCaption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookletCaption(PathMetadata metadata, PathInits inits) {
        this(BookletCaption.class, metadata, inits);
    }

    public QBookletCaption(Class<? extends BookletCaption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.booklet = inits.isInitialized("booklet") ? new QBooklet(forProperty("booklet"), inits.get("booklet")) : null;
    }

}

