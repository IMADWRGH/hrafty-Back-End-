package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.common.PageResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {

    void sendEmail(String to, String subject, String body);

    void sendOrderConfirmation(Long orderId);

    void sendOrderStatusUpdate(Long orderId, String status);

    void sendWelcomeEmail(Long userId);

    void sendPasswordResetEmail(String email, String token);

    List<Object> getUserNotifications(Long userId);

    PageResponseDTO<Object> getUserNotifications(Long userId, Pageable pageable);

    void markNotificationAsRead(Long notificationId);

    void markAllNotificationsAsRead(Long userId);
}