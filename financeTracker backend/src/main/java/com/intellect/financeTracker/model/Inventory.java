package com.intellect.financeTracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "inventory")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    private String itemName;
    private String category;
    private String unit;
    private double unitCost;
    private int quantity;
    private int usedQuantity;
    private int minimumQuantity; // low-stock threshold per item
    private String note;

    @Column(name = "purchase_date")
    private LocalDate currentDate;

    private String vendorName;

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "user_id")
    private Long userId;
}
