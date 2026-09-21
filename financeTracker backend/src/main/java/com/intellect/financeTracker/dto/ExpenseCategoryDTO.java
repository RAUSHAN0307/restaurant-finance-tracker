package com.intellect.financeTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpenseCategoryDTO {
    private String category;
    private Double amount;
}
