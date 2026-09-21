package com.intellect.financeTracker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class BillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billItemId;

    private Integer quantity;

    private Double cost;

    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    @JsonBackReference(value = "bill-billItems")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @JsonBackReference(value = "menu-billItems")
    private MenuItem menuItem;
}
