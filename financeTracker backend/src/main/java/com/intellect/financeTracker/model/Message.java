package com.intellect.financeTracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    private String type; // PENDING_PAYMENT or LOW_STOCK
    private String title;
    @Column(length = 500)
    private String content;
    private Long referenceId; // vendorId or inventoryId
    private String referenceType; // VENDOR or INVENTORY
    private boolean isRead;
    private LocalDateTime createdAt;
    private Long userId;

}
