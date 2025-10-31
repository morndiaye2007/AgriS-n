package com.agri.sen.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRapportEntity is a Querydsl query type for RapportEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRapportEntity extends EntityPathBase<RapportEntity> {

    private static final long serialVersionUID = 1614523984L;

    public static final QRapportEntity rapportEntity = new QRapportEntity("rapportEntity");

    public final DateTimePath<java.time.LocalDateTime> dateGeneration = createDateTime("dateGeneration", java.time.LocalDateTime.class);

    public final StringPath donnees = createString("donnees");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath titre = createString("titre");

    public final StringPath type = createString("type");

    public QRapportEntity(String variable) {
        super(RapportEntity.class, forVariable(variable));
    }

    public QRapportEntity(Path<? extends RapportEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRapportEntity(PathMetadata metadata) {
        super(RapportEntity.class, metadata);
    }

}

