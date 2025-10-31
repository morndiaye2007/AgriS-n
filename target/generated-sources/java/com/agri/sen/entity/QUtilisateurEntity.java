package com.agri.sen.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUtilisateurEntity is a Querydsl query type for UtilisateurEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUtilisateurEntity extends EntityPathBase<UtilisateurEntity> {

    private static final long serialVersionUID = 277933937L;

    public static final QUtilisateurEntity utilisateurEntity = new QUtilisateurEntity("utilisateurEntity");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath motDePasse = createString("motDePasse");

    public final StringPath nom = createString("nom");

    public final StringPath prenom = createString("prenom");

    public final StringPath role = createString("role");

    public QUtilisateurEntity(String variable) {
        super(UtilisateurEntity.class, forVariable(variable));
    }

    public QUtilisateurEntity(Path<? extends UtilisateurEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUtilisateurEntity(PathMetadata metadata) {
        super(UtilisateurEntity.class, metadata);
    }

}

