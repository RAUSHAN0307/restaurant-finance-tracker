package com.intellect.financeTracker.service;

import com.intellect.financeTracker.model.Message;

import java.util.List;

public interface MessageService {

    List<Message> generateMessages(Long userId);

    List<Message> getAllMessages(Long userId);

    List<Message> getUnreadMessages(Long userId);

    List<Message> getMessagesByType(Long userId, String type);

    Message markAsRead(Long userId, Long messageId);

    void markAllAsRead(Long userId);
}
