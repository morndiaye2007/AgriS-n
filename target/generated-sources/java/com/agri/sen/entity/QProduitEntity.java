package com.agri.sen.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduitEntity is a Querydsl query type for ProduitEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduitEntity extends EntityPathBase<ProduitEntity> {

    private static final long serialVersionUID = -799300969L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduitEntity produitEntity = new QProduitEntity("produitEntity");

    public final QCategorieEntity categorie;

    public final DateTimePath<java.time.LocalDateTime> dateCreation = createDateTime("dateCreation", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> dateModification = createDateTime("dateModification", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final BooleanPath disponible = createBoolean("disponible");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath nom = createString("nom");

    public final NumberPath<Double> prix = createNumber("prix", Double.class);

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public final QUtilisateurEntity vendeur;

    public QProduitEntity(String variable) {
        this(ProduitEntity.class, forVariable(variable), INITS);
    }

    public QProduitEntity(Path<? extends ProduitEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduitEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduitEntity(PathMetadata metadata, PathInits inits) {
        this(ProduitEntity.class, metadata, inits);
    }

    public QProduitEntity(Class<? extends ProduitEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.categorie = inits.isInitialized("categorie") ? new QCategorieEntity(forProperty("categorie")) : null;
        this.vendeur = inits.isInitialized("vendeur") ? new QUtilisateurEntity(forProperty("vendeur")) : null;
    }

}

