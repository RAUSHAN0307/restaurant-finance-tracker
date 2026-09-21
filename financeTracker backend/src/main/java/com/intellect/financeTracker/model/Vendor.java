package com.intellect.financeTracker.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;

    private String name;
    private String phoneNumber;
    private String email;
    private double amountPending;
    private String address;
    private String note;
    private LocalDate createdAt;
    private LocalDate dueDate;

    @Column(name = "user_id")
    private Long userId;
}
