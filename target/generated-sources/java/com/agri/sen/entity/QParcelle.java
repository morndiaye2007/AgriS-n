package com.agri.sen.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QParcelle is a Querydsl query type for Parcelle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParcelle extends EntityPathBase<Parcelle> {

    private static final long serialVersionUID = 1206528183L;

    public static final QParcelle parcelle = new QParcelle("parcelle");

    public final BooleanPath active = createBoolean("active");

    public final StringPath adresse = createString("adresse");

    public final NumberPath<Long> agriculteurId = createNumber("agriculteurId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> cultureId = createNumber("cultureId", Long.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath nom = createString("nom");

    public final StringPath sourceEau = createString("sourceEau");

    public final NumberPath<Double> superficie = createNumber("superficie", Double.class);

    public final StringPath typeSol = createString("typeSol");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final StringPath ville = createString("ville");

    public QParcelle(String variable) {
        super(Parcelle.class, forVariable(variable));
    }

    public QParcelle(Path<? extends Parcelle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParcelle(PathMetadata metadata) {
        super(Parcelle.class, metadata);
    }

}

