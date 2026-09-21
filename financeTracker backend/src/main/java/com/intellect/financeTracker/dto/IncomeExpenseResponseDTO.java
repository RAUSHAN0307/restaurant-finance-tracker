package com.intellect.financeTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeExpenseResponseDTO {
    private String period;
    private String category;
    private List<IncomeExpenseDataPointDTO> data;
}
