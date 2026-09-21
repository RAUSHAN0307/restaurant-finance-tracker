package com.intellect.financeTracker.controller;

import com.intellect.financeTracker.dto.DashboardAnalyticsDTO;
import com.intellect.financeTracker.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/analytics")
@CrossOrigin("http://localhost:4200")
@PreAuthorize("hasAnyRole('OWNER', 'MANAGER', 'WAITER')")
// @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<DashboardAnalyticsDTO> getStats(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {

        return ResponseEntity.ok(analyticsService.getDashboardStats(userId, startDate, endDate));
    }

    @GetMapping("/income-expense")
    public ResponseEntity<com.intellect.financeTracker.dto.IncomeExpenseResponseDTO> getIncomeExpense(
            @RequestParam("ownerId") Long ownerId,
            @RequestParam("period") String period,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(required = false, defaultValue = "ALL") String category) {

        return ResponseEntity
                .ok(analyticsService.getIncomeExpenseAnalytics(ownerId, period, startDate, endDate, category));
    }
}
