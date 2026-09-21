package com.intellect.financeTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeExpenseDataPointDTO {
    private String label;
    private Double income;
    private Double expense;
}
