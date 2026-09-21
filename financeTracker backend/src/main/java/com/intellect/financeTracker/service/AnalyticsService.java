package com.intellect.financeTracker.service;

import com.intellect.financeTracker.dto.DashboardAnalyticsDTO;

import com.intellect.financeTracker.dto.IncomeExpenseResponseDTO;

import java.time.LocalDate;

public interface AnalyticsService {
    DashboardAnalyticsDTO getDashboardStats(Long userId, LocalDate startDate, LocalDate endDate);

    IncomeExpenseResponseDTO getIncomeExpenseAnalytics(Long userId, String period, LocalDate startDate,
            LocalDate endDate, String category);
}
