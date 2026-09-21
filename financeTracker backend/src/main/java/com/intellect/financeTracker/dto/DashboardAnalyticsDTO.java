package com.intellect.financeTracker.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DashboardAnalyticsDTO {
    private Double totalIncome;
    private Double totalExpense;
    private Double netProfit;

    private List<ExpenseCategoryDTO> expenseBreakdown;

    // Today's Stats
    private Double todayIncome;
    private Double todayExpense;

    // Monthly Stats
    private Double monthlyIncome;
    private Double monthlyExpense;
    private Double monthlyNetProfit;

    // Financial Year Stats
    private Double yearlyIncome;
    private Double yearlyExpense;
}
