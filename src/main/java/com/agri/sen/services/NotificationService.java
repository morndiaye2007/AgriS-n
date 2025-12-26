package com.agri.sen.services;

import com.agri.sen.model.NotificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface NotificationService {
    NotificationDTO createNotification(NotificationDTO notificationDTO);
    NotificationDTO updateNotification(NotificationDTO notificationDTO);
    void deleteNotification(Long id);
    NotificationDTO getNotification(Long id);
    Page<NotificationDTO> getAllNotifications(Map<String, String> searchParams, Pageable pageable);
    Page<NotificationDTO> getCurrentUserNotifications(Map<String, String> searchParams, Pageable pageable);
    Page<NotificationDTO> getUnreadNotifications(Pageable pageable);
    Long countUnreadNotifications();
    void markAsRead(Long id);
    void markAllAsRead();
}