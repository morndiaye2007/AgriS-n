package com.agri.sen.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPaiementEntity is a Querydsl query type for PaiementEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaiementEntity extends EntityPathBase<PaiementEntity> {

    private static final long serialVersionUID = -663078109L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPaiementEntity paiementEntity = new QPaiementEntity("paiementEntity");

    public final QCommandeEntity commande;

    public final DateTimePath<java.time.LocalDateTime> datePaiement = createDateTime("datePaiement", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> montant = createNumber("montant", Double.class);

    public final StringPath moyenPaiement = createString("moyenPaiement");

    public final StringPath referenceTransaction = createString("referenceTransaction");

    public final StringPath statut = createString("statut");

    public QPaiementEntity(String variable) {
        this(PaiementEntity.class, forVariable(variable), INITS);
    }

    public QPaiementEntity(Path<? extends PaiementEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPaiementEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPaiementEntity(PathMetadata metadata, PathInits inits) {
        this(PaiementEntity.class, metadata, inits);
    }

    public QPaiementEntity(Class<? extends PaiementEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.commande = inits.isInitialized("commande") ? new QCommandeEntity(forProperty("commande"), inits.get("commande")) : null;
    }

}

