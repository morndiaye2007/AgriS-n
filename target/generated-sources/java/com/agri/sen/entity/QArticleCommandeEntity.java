package com.agri.sen.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticleCommandeEntity is a Querydsl query type for ArticleCommandeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticleCommandeEntity extends EntityPathBase<ArticleCommandeEntity> {

    private static final long serialVersionUID = -1845553602L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticleCommandeEntity articleCommandeEntity = new QArticleCommandeEntity("articleCommandeEntity");

    public final QCommandeEntity commande;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> prixTotal = createNumber("prixTotal", Double.class);

    public final NumberPath<Double> prixUnitaire = createNumber("prixUnitaire", Double.class);

    public final QProduitEntity produit;

    public final NumberPath<Integer> quantite = createNumber("quantite", Integer.class);

    public QArticleCommandeEntity(String variable) {
        this(ArticleCommandeEntity.class, forVariable(variable), INITS);
    }

    public QArticleCommandeEntity(Path<? extends ArticleCommandeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticleCommandeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticleCommandeEntity(PathMetadata metadata, PathInits inits) {
        this(ArticleCommandeEntity.class, metadata, inits);
    }

    public QArticleCommandeEntity(Class<? extends ArticleCommandeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.commande = inits.isInitialized("commande") ? new QCommandeEntity(forProperty("commande"), inits.get("commande")) : null;
        this.produit = inits.isInitialized("produit") ? new QProduitEntity(forProperty("produit"), inits.get("produit")) : null;
    }

}

