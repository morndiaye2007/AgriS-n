package com.agri.sen.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCulture is a Querydsl query type for Culture
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCulture extends EntityPathBase<Culture> {

    private static final long serialVersionUID = -955655783L;

    public static final QCulture culture = new QCulture("culture");

    public final StringPath categorie = createString("categorie");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> dureeCroissance = createNumber("dureeCroissance", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath nom = createString("nom");

    public final StringPath saisonOptimale = createString("saisonOptimale");

    public QCulture(String variable) {
        super(Culture.class, forVariable(variable));
    }

    public QCulture(Path<? extends Culture> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCulture(PathMetadata metadata) {
        super(Culture.class, metadata);
    }

}

