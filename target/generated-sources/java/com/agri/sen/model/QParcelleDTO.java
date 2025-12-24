package com.agri.sen.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QParcelleDTO is a Querydsl query type for ParcelleDTO
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParcelleDTO extends EntityPathBase<ParcelleDTO> {

    private static final long serialVersionUID = -1176046334L;

    public static final QParcelleDTO parcelleDTO = new QParcelleDTO("parcelleDTO");

    public final NumberPath<Long> AgriculteurId = createNumber("AgriculteurId", Long.class);

    public final StringPath AgriculteurNom = createString("AgriculteurNom");

    public final NumberPath<Long> cultureId = createNumber("cultureId", Long.class);

    public final StringPath CultureNom = createString("CultureNom");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath localisation = createString("localisation");

    public final StringPath motdepasse = createString("motdepasse");

    public final StringPath nom = createString("nom");

    public final StringPath prenom = createString("prenom");

    public final StringPath telephone = createString("telephone");

    public QParcelleDTO(String variable) {
        super(ParcelleDTO.class, forVariable(variable));
    }

    public QParcelleDTO(Path<? extends ParcelleDTO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParcelleDTO(PathMetadata metadata) {
        super(ParcelleDTO.class, metadata);
    }

}

