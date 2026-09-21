package com.intellect.financeTracker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId;

    private String empName;

    private String role;

    private BigDecimal salary;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate joinDate;

    private String phone;

    private String email;

    // @JsonManagedReference
    // @OneToMany(mappedBy = "employee",
    // cascade = CascadeType.ALL,
    // orphanRemoval = true)
    // private List<SalaryPayments> salaryPayments;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    @JsonBackReference(value = "user-employees")
    @ToString.Exclude
    private User user;
}
