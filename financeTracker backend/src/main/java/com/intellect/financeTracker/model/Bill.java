package com.intellect.financeTracker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
// import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate billDate;
    private Double totalAmt;
    private Double taxAmt;
    private Double netAmt;
    private String paymentMode;
    private String customerType;
    private String phoneNo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user-bill")
    private User user;

    @ManyToOne
    @JoinColumn(name = "voucher_id", nullable = true)
    @JsonBackReference(value = "voucher-bill")
    private Voucher voucher;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<BillItem> billItems;
}
