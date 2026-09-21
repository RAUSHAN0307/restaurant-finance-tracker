package com.intellect.financeTracker.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RESTAURENT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restaurent_name", nullable = false, length = 30)
    private String restaurantName;

    @Column(name = "GST_Number", length = 20)
    private String gstNumber;

    @Column(length = 50)
    private String address;

    @Column(name = "user_id")
    private Long userId;
}
