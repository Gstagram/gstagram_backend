package com.gstagram.gstagram.course.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCourse is a Querydsl query type for Course
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCourse extends EntityPathBase<Course> {

    private static final long serialVersionUID = 1000518367L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCourse course = new QCourse("course");

    public final com.gstagram.gstagram.city.domain.QCity city;

    public final StringPath courseName = createString("courseName");

    public final DateTimePath<java.time.LocalDateTime> createdTime = createDateTime("createdTime", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.gstagram.gstagram.region.domain.QRegion region;

    public final StringPath thumbNailUrl = createString("thumbNailUrl");

    public final com.gstagram.gstagram.user.domain.QUser user;

    public QCourse(String variable) {
        this(Course.class, forVariable(variable), INITS);
    }

    public QCourse(Path<? extends Course> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCourse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCourse(PathMetadata metadata, PathInits inits) {
        this(Course.class, metadata, inits);
    }

    public QCourse(Class<? extends Course> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.city = inits.isInitialized("city") ? new com.gstagram.gstagram.city.domain.QCity(forProperty("city"), inits.get("city")) : null;
        this.region = inits.isInitialized("region") ? new com.gstagram.gstagram.region.domain.QRegion(forProperty("region")) : null;
        this.user = inits.isInitialized("user") ? new com.gstagram.gstagram.user.domain.QUser(forProperty("user")) : null;
    }

}

