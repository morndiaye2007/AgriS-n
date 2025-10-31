package com.agri.sen.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnalyseEntity is a Querydsl query type for AnalyseEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnalyseEntity extends EntityPathBase<AnalyseEntity> {

    private static final long serialVersionUID = 2081518049L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnalyseEntity analyseEntity = new QAnalyseEntity("analyseEntity");

    public final DateTimePath<java.time.LocalDateTime> dateCreation = createDateTime("dateCreation", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath metrique = createString("metrique");

    public final StringPath periode = createString("periode");

    public final QRapportEntity rapport;

    public final QUtilisateurEntity utilisateur;

    public final NumberPath<Double> valeur = createNumber("valeur", Double.class);

    public QAnalyseEntity(String variable) {
        this(AnalyseEntity.class, forVariable(variable), INITS);
    }

    public QAnalyseEntity(Path<? extends AnalyseEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnalyseEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnalyseEntity(PathMetadata metadata, PathInits inits) {
        this(AnalyseEntity.class, metadata, inits);
    }

    public QAnalyseEntity(Class<? extends AnalyseEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.rapport = inits.isInitialized("rapport") ? new QRapportEntity(forProperty("rapport")) : null;
        this.utilisateur = inits.isInitialized("utilisateur") ? new QUtilisateurEntity(forProperty("utilisateur")) : null;
    }

}

