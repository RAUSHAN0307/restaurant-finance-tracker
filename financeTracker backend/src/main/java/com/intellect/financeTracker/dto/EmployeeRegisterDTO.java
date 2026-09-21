package com.intellect.financeTracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

import java.math.BigDecimal;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeRegisterDTO {

    private String empName;

    private String role;

    private BigDecimal salary;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate joinDate;

    private String phone;

    private String email;

    private String userName;

    private String password;

}