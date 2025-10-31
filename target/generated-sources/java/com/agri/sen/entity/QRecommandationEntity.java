package com.agri.sen.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecommandationEntity is a Querydsl query type for RecommandationEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommandationEntity extends EntityPathBase<RecommandationEntity> {

    private static final long serialVersionUID = 1046801525L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecommandationEntity recommandationEntity = new QRecommandationEntity("recommandationEntity");

    public final StringPath culturesRecommandees = createString("culturesRecommandees");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath region = createString("region");

    public final QRessourceEntity ressource;

    public final StringPath saison = createString("saison");

    public QRecommandationEntity(String variable) {
        this(RecommandationEntity.class, forVariable(variable), INITS);
    }

    public QRecommandationEntity(Path<? extends RecommandationEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecommandationEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecommandationEntity(PathMetadata metadata, PathInits inits) {
        this(RecommandationEntity.class, metadata, inits);
    }

    public QRecommandationEntity(Class<? extends RecommandationEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ressource = inits.isInitialized("ressource") ? new QRessourceEntity(forProperty("ressource"), inits.get("ressource")) : null;
    }

}

