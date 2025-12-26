package com.agri.sen.services.Impl;

import com.agri.sen.services.NotificationService;
import com.querydsl.core.BooleanBuilder;
import com.agri.sen.entity.QNotification;
import com.agri.sen.entity.Notification;
import com.agri.sen.entity.Utilisateur;
import com.agri.sen.entity.enums.TypeNotification;
import com.agri.sen.mapper.NotificationMapper;
import com.agri.sen.model.NotificationDTO;
import com.agri.sen.repository.NotificationRepository;
import com.agri.sen.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.querydsl.core.types.Predicate;


import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        // Vérifier que l'utilisateur existe
        if (!utilisateurRepository.existsById(notificationDTO.getUtilisateurId())) {
            throw new RuntimeException("Utilisateur non trouvé");
        }

        var entity = notificationMapper.asEntity(notificationDTO);
        var entitySave = notificationRepository.save(entity);
        return notificationMapper.asDto(entitySave);
    }

    @Override
    public NotificationDTO updateNotification(NotificationDTO notificationDTO) {
        if (!notificationRepository.existsById(notificationDTO.getId())) {
            throw new RuntimeException("Notification non trouvée");
        }

        var entityUpdate = notificationMapper.asEntity(notificationDTO);
        var updatedEntity = notificationRepository.save(entityUpdate);
        return notificationMapper.asDto(updatedEntity);
    }

    @Override
    public void deleteNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification non trouvée"));

        // Vérifier que la notification appartient à l'utilisateur actuel
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Utilisateur currentUser = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!notification.getUtilisateurId().equals(currentUser.getId())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer cette notification");
        }

        notificationRepository.deleteById(id);
    }

    @Override
    public NotificationDTO getNotification(Long id) {
        var entity = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification non trouvée"));
        return notificationMapper.asDto(entity);
    }

    @Override
    public Page<NotificationDTO> getAllNotifications(Map<String, String> searchParams, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);

        // Utilise booleanBuilder comme Predicate
        return notificationRepository.findAll((Predicate) booleanBuilder, pageable)
                .map(notificationMapper::asDto);
    }

    @Override
    public Page<NotificationDTO> getCurrentUserNotifications(Map<String, String> searchParams, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Utilisateur currentUser = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        var booleanBuilder = new BooleanBuilder();
        var qEntity = QNotification.notification;
        booleanBuilder.and(qEntity.utilisateurId.eq(currentUser.getId()));
        buildSearch(searchParams, booleanBuilder);

        return notificationRepository.findAll(booleanBuilder, pageable)
                .map(notificationMapper::asDto);
    }

    @Override
    public Page<NotificationDTO> getUnreadNotifications(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Utilisateur currentUser = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        var booleanBuilder = new BooleanBuilder();
        var qEntity = QNotification.notification;
        booleanBuilder.and(qEntity.utilisateurId.eq(currentUser.getId()))
                .and(qEntity.lu.eq(false));

        return notificationRepository.findAll(booleanBuilder, pageable)
                .map(notificationMapper::asDto);
    }

    @Override
    public Long countUnreadNotifications() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Utilisateur currentUser = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return notificationRepository.countUnreadByUser(currentUser.getId());
    }

    @Override
    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification non trouvée"));

        // Vérifier que la notification appartient à l'utilisateur actuel
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Utilisateur currentUser = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!notification.getUtilisateurId().equals(currentUser.getId())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier cette notification");
        }

        notification.setLu(true);
        notification.setDateLecture(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    @Override
    public void markAllAsRead() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Utilisateur currentUser = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        var notifications = notificationRepository.findByUserIdAndLuFalse(currentUser.getId());

        notifications.forEach(n -> {
            n.setLu(true);
            n.setDateLecture(LocalDateTime.now());
        });

        notificationRepository.saveAll(notifications);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QNotification.notification;

            if (searchParams.containsKey("type")) {
                String typeStr = searchParams.get("type");
                TypeNotification type = TypeNotification.valueOf(typeStr.toUpperCase());
                booleanBuilder.and(qEntity.type.eq(type));
            }

            if (searchParams.containsKey("lu")) {
                Boolean lu = Boolean.parseBoolean(searchParams.get("lu"));
                booleanBuilder.and(qEntity.lu.eq(lu));
            }

            if (searchParams.containsKey("titre"))
                booleanBuilder.and(qEntity.titre.containsIgnoreCase(searchParams.get("titre")));
        }
    }
}