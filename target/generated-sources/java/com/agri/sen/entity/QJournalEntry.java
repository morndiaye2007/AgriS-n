package com.agri.sen.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QJournalEntry is a Querydsl query type for JournalEntry
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJournalEntry extends EntityPathBase<JournalEntry> {

    private static final long serialVersionUID = 1666615920L;

    public static final QJournalEntry journalEntry = new QJournalEntry("journalEntry");

    public final NumberPath<Double> cout = createNumber("cout", Double.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> dateActivite = createDateTime("dateActivite", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final StringPath documents = createString("documents");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> parcelleId = createNumber("parcelleId", Long.class);

    public final StringPath photos = createString("photos");

    public final NumberPath<Double> quantite = createNumber("quantite", Double.class);

    public final EnumPath<com.agri.sen.entity.enums.TypeActivite> typeActivite = createEnum("typeActivite", com.agri.sen.entity.enums.TypeActivite.class);

    public final StringPath unite = createString("unite");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QJournalEntry(String variable) {
        super(JournalEntry.class, forVariable(variable));
    }

    public QJournalEntry(Path<? extends JournalEntry> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJournalEntry(PathMetadata metadata) {
        super(JournalEntry.class, metadata);
    }

}

