package com.intellect.financeTracker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryPayments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salaryId;

    private BigDecimal amount;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate paymentDate;

    private String paymentMode;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;
}
