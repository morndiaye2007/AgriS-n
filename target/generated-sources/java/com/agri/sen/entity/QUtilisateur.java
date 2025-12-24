package com.agri.sen.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUtilisateur is a Querydsl query type for Utilisateur
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUtilisateur extends EntityPathBase<Utilisateur> {

    private static final long serialVersionUID = 599640558L;

    public static final QUtilisateur utilisateur = new QUtilisateur("utilisateur");

    public final StringPath adresse = createString("adresse");

    public final StringPath codePostal = createString("codePostal");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final BooleanPath enabled = createBoolean("enabled");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> lastLogin = createDateTime("lastLogin", java.time.LocalDateTime.class);

    public final StringPath mot_de_passe = createString("mot_de_passe");

    public final StringPath nom = createString("nom");

    public final StringPath prenom = createString("prenom");

    public final StringPath profileImage = createString("profileImage");

    public final EnumPath<com.agri.sen.entity.enums.Role> role = createEnum("role", com.agri.sen.entity.enums.Role.class);

    public final StringPath telephone = createString("telephone");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final StringPath ville = createString("ville");

    public QUtilisateur(String variable) {
        super(Utilisateur.class, forVariable(variable));
    }

    public QUtilisateur(Path<? extends Utilisateur> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUtilisateur(PathMetadata metadata) {
        super(Utilisateur.class, metadata);
    }

}

