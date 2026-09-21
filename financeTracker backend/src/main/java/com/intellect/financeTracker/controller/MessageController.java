
package com.intellect.financeTracker.controller;

import com.intellect.financeTracker.model.Message;
import com.intellect.financeTracker.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/messages")
@PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/{userId}/generate")
    public List<Message> generateMessages(
            @PathVariable("userId") Long userId) {
        return messageService.generateMessages(userId);
    }

    @GetMapping("/{userId}/all")
    public List<Message> getAllMessages(
            @PathVariable("userId") Long userId) {
        return messageService.getAllMessages(userId);
    }

    @GetMapping("/{userId}/unread")
    public List<Message> getUnreadMessages(
            @PathVariable("userId") Long userId) {
        return messageService.getUnreadMessages(userId);
    }

    @GetMapping("/{userId}/type/{type}")
    public List<Message> getMessagesByType(
            @PathVariable("userId") Long userId,
            @PathVariable("type") String type) {
        return messageService.getMessagesByType(userId, type);
    }

    @PutMapping("/{userId}/read/{messageId}")
    public Message markAsRead(
            @PathVariable("userId") Long userId,
            @PathVariable("messageId") Long messageId) {
        return messageService.markAsRead(userId, messageId);
    }

    @PutMapping("/{userId}/read-all")
    public String markAllAsRead(
            @PathVariable("userId") Long userId) {
        messageService.markAllAsRead(userId);
        return "All messages marked as read";
    }
}
