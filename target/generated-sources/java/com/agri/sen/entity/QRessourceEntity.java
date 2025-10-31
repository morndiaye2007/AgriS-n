package com.agri.sen.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRessourceEntity is a Querydsl query type for RessourceEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRessourceEntity extends EntityPathBase<RessourceEntity> {

    private static final long serialVersionUID = 1590667625L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRessourceEntity ressourceEntity = new QRessourceEntity("ressourceEntity");

    public final QUtilisateurEntity auteur;

    public final DateTimePath<java.time.LocalDateTime> dateCreation = createDateTime("dateCreation", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath langue = createString("langue");

    public final StringPath region = createString("region");

    public final StringPath titre = createString("titre");

    public final StringPath type = createString("type");

    public final StringPath url = createString("url");

    public QRessourceEntity(String variable) {
        this(RessourceEntity.class, forVariable(variable), INITS);
    }

    public QRessourceEntity(Path<? extends RessourceEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRessourceEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRessourceEntity(PathMetadata metadata, PathInits inits) {
        this(RessourceEntity.class, metadata, inits);
    }

    public QRessourceEntity(Class<? extends RessourceEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auteur = inits.isInitialized("auteur") ? new QUtilisateurEntity(forProperty("auteur")) : null;
    }

}

