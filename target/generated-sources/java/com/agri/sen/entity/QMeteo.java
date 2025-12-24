package com.agri.sen.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMeteo is a Querydsl query type for Meteo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMeteo extends EntityPathBase<Meteo> {

    private static final long serialVersionUID = 1849110065L;

    public static final QMeteo meteo = new QMeteo("meteo");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> datePrevisionnelle = createDateTime("datePrevisionnelle", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final NumberPath<Integer> humidite = createNumber("humidite", Integer.class);

    public final StringPath icone = createString("icone");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final NumberPath<Double> probabilitePluie = createNumber("probabilitePluie", Double.class);

    public final NumberPath<Double> temperature = createNumber("temperature", Double.class);

    public final NumberPath<Double> temperatureMax = createNumber("temperatureMax", Double.class);

    public final NumberPath<Double> temperatureMin = createNumber("temperatureMin", Double.class);

    public final StringPath ville = createString("ville");

    public final NumberPath<Double> vitesseVent = createNumber("vitesseVent", Double.class);

    public QMeteo(String variable) {
        super(Meteo.class, forVariable(variable));
    }

    public QMeteo(Path<? extends Meteo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMeteo(PathMetadata metadata) {
        super(Meteo.class, metadata);
    }

}

