package com.intellect.financeTracker.service.implementation;

import com.intellect.financeTracker.service.*;

import com.intellect.financeTracker.model.Inventory;
import com.intellect.financeTracker.model.Message;
import com.intellect.financeTracker.model.Vendor;
import com.intellect.financeTracker.repository.InventoryRepository;
import com.intellect.financeTracker.repository.MessageRepository;
import com.intellect.financeTracker.repository.UserRepository;
import com.intellect.financeTracker.repository.VendorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MessageServiceImplementation implements MessageService {

    private static final int NOTIFY_DAYS_BEFORE_DUE = 5;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private UserRepository userRepository;

    private void validateUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
    }

    // ──────────────────────────────────────────────
    // Generate messages by scanning vendors & items
    // ──────────────────────────────────────────────
    @Override
    public List<Message> generateMessages(Long userId) {

        validateUserExists(userId);
        List<Message> generated = new ArrayList<>();

        // 1. Pending Vendor Payments (due within 5 days)
        LocalDate today = LocalDate.now();
        LocalDate notifyBefore = today.plusDays(NOTIFY_DAYS_BEFORE_DUE);

        List<Vendor> vendorsApproachingDue = vendorRepository.findVendorsWithUpcomingDuePayments(userId, notifyBefore);

        for (Vendor vendor : vendorsApproachingDue) {

            // Remove old messages for this vendor so we don't create duplicates
            messageRepository.deleteByReferenceIdAndReferenceType(
                    vendor.getVendorId(), "VENDOR");

            long daysUntilDue = ChronoUnit.DAYS.between(today, vendor.getDueDate());

            Message msg = new Message();
            msg.setType("PENDING_PAYMENT");
            msg.setTitle("Pending Payment for " + vendor.getName());
            msg.setContent(
                    "Vendor " + vendor.getName()
                            + " (ID: " + vendor.getVendorId() + ")"
                            + " has a pending payment of ₹"
                            + vendor.getAmountPending()
                            + ". Due date: " + vendor.getDueDate()
                            + " (" + daysUntilDue + " days remaining).");
            msg.setReferenceId(vendor.getVendorId());
            msg.setReferenceType("VENDOR");
            msg.setUserId(userId);

            generated.add(messageRepository.save(msg));
        }

        // 2. Low Stock Alerts (remaining quantity <= item's minimumQuantity)
        List<Inventory> lowStockItems = inventoryRepository.findLowStockItemsByUserId(userId);

        for (Inventory item : lowStockItems) {

            int remaining = item.getQuantity() - item.getUsedQuantity();

            // Remove old messages for this item so we don't create duplicates
            messageRepository.deleteByReferenceIdAndReferenceType(
                    item.getInventoryId(), "INVENTORY");

            Message msg = new Message();
            msg.setType("LOW_STOCK");
            msg.setTitle("Low Stock: " + item.getItemName());
            msg.setContent(
                    "Item " + item.getItemName()
                            + " (ID: " + item.getInventoryId() + ")"
                            + " has only " + remaining
                            + " " + item.getUnit() + " remaining"
                            + " (minimum required: " + item.getMinimumQuantity() + ").");
            msg.setReferenceId(item.getInventoryId());
            msg.setReferenceType("INVENTORY");
            msg.setUserId(userId);

            generated.add(messageRepository.save(msg));
        }

        return generated;
    }

    // ──────────────────────────────────────────────
    // Retrieval helpers
    // ──────────────────────────────────────────────
    @Override
    public List<Message> getAllMessages(Long userId) {
        validateUserExists(userId);
        return messageRepository.findByUserId(userId);
    }

    @Override
    public List<Message> getUnreadMessages(Long userId) {
        validateUserExists(userId);
        return messageRepository.findByUserIdAndIsReadFalse(userId);
    }

    @Override
    public List<Message> getMessagesByType(Long userId, String type) {
        validateUserExists(userId);
        return messageRepository.findByUserIdAndType(userId, type);
    }

    // ──────────────────────────────────────────────
    // Read-status management
    // ──────────────────────────────────────────────
    @Override
    public Message markAsRead(Long userId, Long messageId) {
        validateUserExists(userId);

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        message.setRead(true);
        return messageRepository.save(message);
    }

    @Override
    public void markAllAsRead(Long userId) {
        validateUserExists(userId);

        List<Message> unread = messageRepository.findByUserIdAndIsReadFalse(userId);
        for (Message msg : unread) {
            msg.setRead(true);
        }
        messageRepository.saveAll(unread);
    }
}
