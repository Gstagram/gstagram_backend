package com.gstagram.gstagram.booklet.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBooklet is a Querydsl query type for Booklet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBooklet extends EntityPathBase<Booklet> {

    private static final long serialVersionUID = -261995087L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBooklet booklet = new QBooklet("booklet");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.gstagram.gstagram.region.domain.QRegion region;

    public QBooklet(String variable) {
        this(Booklet.class, forVariable(variable), INITS);
    }

    public QBooklet(Path<? extends Booklet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBooklet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBooklet(PathMetadata metadata, PathInits inits) {
        this(Booklet.class, metadata, inits);
    }

    public QBooklet(Class<? extends Booklet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.region = inits.isInitialized("region") ? new com.gstagram.gstagram.region.domain.QRegion(forProperty("region")) : null;
    }

}

