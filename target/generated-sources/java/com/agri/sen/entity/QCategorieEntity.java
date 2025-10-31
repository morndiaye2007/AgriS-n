package com.agri.sen.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCategorieEntity is a Querydsl query type for CategorieEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategorieEntity extends EntityPathBase<CategorieEntity> {

    private static final long serialVersionUID = 615650533L;

    public static final QCategorieEntity categorieEntity = new QCategorieEntity("categorieEntity");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nom = createString("nom");

    public QCategorieEntity(String variable) {
        super(CategorieEntity.class, forVariable(variable));
    }

    public QCategorieEntity(Path<? extends CategorieEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategorieEntity(PathMetadata metadata) {
        super(CategorieEntity.class, metadata);
    }

}

