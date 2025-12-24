package com.agri.sen.mapper;

import com.agri.sen.entity.Notification;
import com.agri.sen.entity.Notification.NotificationBuilder;
import com.agri.sen.entity.enums.TypeNotification;
import com.agri.sen.model.NotificationDTO;
import com.agri.sen.model.NotificationDTO.NotificationDTOBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-24T21:46:57+0000",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class NotificationMapperImpl implements NotificationMapper {

    @Override
    public Notification asEntity(NotificationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        NotificationBuilder notification = Notification.builder();

        notification.id( dto.getId() );
        notification.utilisateurId( dto.getUtilisateurId() );
        notification.message( dto.getMessage() );
        if ( dto.getType() != null ) {
            notification.type( Enum.valueOf( TypeNotification.class, dto.getType() ) );
        }
        if ( dto.getLu() != null ) {
            notification.lu( Boolean.parseBoolean( dto.getLu() ) );
        }
        notification.createdAt( dto.getCreatedAt() );

        return notification.build();
    }

    @Override
    public NotificationDTO asDto(Notification entity) {
        if ( entity == null ) {
            return null;
        }

        NotificationDTOBuilder notificationDTO = NotificationDTO.builder();

        notificationDTO.id( entity.getId() );
        notificationDTO.message( entity.getMessage() );
        notificationDTO.createdAt( entity.getCreatedAt() );
        if ( entity.getType() != null ) {
            notificationDTO.type( entity.getType().name() );
        }
        if ( entity.getLu() != null ) {
            notificationDTO.lu( String.valueOf( entity.getLu() ) );
        }

        return notificationDTO.build();
    }

    @Override
    public List<NotificationDTO> parse(List<Notification> entities) {
        if ( entities == null ) {
            return null;
        }

        List<NotificationDTO> list = new ArrayList<NotificationDTO>( entities.size() );
        for ( Notification notification : entities ) {
            list.add( asDto( notification ) );
        }

        return list;
    }

    @Override
    public List<Notification> parseToEntity(List<NotificationDTO> entities) {
        if ( entities == null ) {
            return null;
        }

        List<Notification> list = new ArrayList<Notification>( entities.size() );
        for ( NotificationDTO notificationDTO : entities ) {
            list.add( asEntity( notificationDTO ) );
        }

        return list;
    }
}
