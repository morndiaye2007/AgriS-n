package com.agri.sen.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommandeEntity is a Querydsl query type for CommandeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommandeEntity extends EntityPathBase<CommandeEntity> {

    private static final long serialVersionUID = -872749902L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommandeEntity commandeEntity = new QCommandeEntity("commandeEntity");

    public final QUtilisateurEntity acheteur;

    public final DateTimePath<java.time.LocalDateTime> dateCreation = createDateTime("dateCreation", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> dateModification = createDateTime("dateModification", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> montantTotal = createNumber("montantTotal", Double.class);

    public final StringPath numeroCommande = createString("numeroCommande");

    public final QPaiementEntity paiement;

    public final StringPath statut = createString("statut");

    public QCommandeEntity(String variable) {
        this(CommandeEntity.class, forVariable(variable), INITS);
    }

    public QCommandeEntity(Path<? extends CommandeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommandeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommandeEntity(PathMetadata metadata, PathInits inits) {
        this(CommandeEntity.class, metadata, inits);
    }

    public QCommandeEntity(Class<? extends CommandeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.acheteur = inits.isInitialized("acheteur") ? new QUtilisateurEntity(forProperty("acheteur")) : null;
        this.paiement = inits.isInitialized("paiement") ? new QPaiementEntity(forProperty("paiement"), inits.get("paiement")) : null;
    }

}

