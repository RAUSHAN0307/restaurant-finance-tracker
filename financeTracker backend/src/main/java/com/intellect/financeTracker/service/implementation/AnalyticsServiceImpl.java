package com.intellect.financeTracker.service.implementation;

import com.intellect.financeTracker.service.*;

import com.intellect.financeTracker.repository.BillRepository;
import com.intellect.financeTracker.repository.ExpenceRepository;
import com.intellect.financeTracker.repository.SalaryPaymentRepository;
import com.intellect.financeTracker.dto.DashboardAnalyticsDTO;
import com.intellect.financeTracker.dto.ExpenseCategoryDTO;
import com.intellect.financeTracker.dto.IncomeExpenseResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ExpenceRepository expenseRepository;

    @Autowired
    private SalaryPaymentRepository salaryRepository;

    @Override
    public DashboardAnalyticsDTO getDashboardStats(Long userId, LocalDate startDate, LocalDate endDate) {
        // // 1. Fetch Income (Bills)
        // Double income = billRepository.sumNetAmtByDateRange(userId, startDate,
        // endDate);
        // if (income == null) income = 0.0;
        //
        // // 2. Fetch Salaries (Separate Table)
        // Double salaryExpense = salaryRepository.sumSalaryByDateRange(userId,
        // startDate, endDate);
        // if (salaryExpense == null) salaryExpense = 0.0;
        //
        // // 3. Fetch Categorized Expenses (Rent, Utility, Marketing, etc.)
        // List<ExpenseCategoryDTO> breakdown =
        // expenseRepository.getExpenseBreakdown(userId, startDate, endDate);
        //
        // // 4. Add Salary to the Breakdown List manually
        // if (salaryExpense > 0) {
        // breakdown.add(new ExpenseCategoryDTO("Salary", salaryExpense));
        // }
        //
        // // 5. Calculate Total Expense from the breakdown list
        // Double totalExpense = breakdown.stream()
        // .map(e -> e.getAmount().doubleValue())
        // .reduce(0.0, Double::sum);
        //
        // // 6. Final Calculation
        // Double netProfit = income - totalExpense;
        //
        // // Return the DTO (Ensure your DTO has the 'expenseBreakdown' field)
        // return new DashboardAnalyticsDTO(income, totalExpense, netProfit, breakdown);

        LocalDate today = LocalDate.now();
        LocalDate monthStart = today.withDayOfMonth(1);
        LocalDate monthEnd = today.withDayOfMonth(today.lengthOfMonth());

        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();

        LocalDate fyStart;
        LocalDate fyEnd;

        if (currentMonth >= 4) { // April or later
            fyStart = LocalDate.of(currentYear, 4, 1);
            fyEnd = LocalDate.of(currentYear + 1, 3, 31);
        } else { // Jan, Feb, or March
            fyStart = LocalDate.of(currentYear - 1, 4, 1);
            fyEnd = LocalDate.of(currentYear, 3, 31);
        }

        // --- 1. DATA FOR SELECTED RANGE (EXISTING) ---
        Double rangeIncome = billRepository.sumNetAmtByDateRange(userId, startDate, endDate);
        System.out.println("income" + rangeIncome);
        Double rangeSalary = salaryRepository.sumSalaryByDateRange(userId, startDate, endDate);
        System.out.println("salary" + rangeIncome);
        List<ExpenseCategoryDTO> breakdown = expenseRepository.getExpenseBreakdown(userId, startDate, endDate);

        if (rangeSalary != null && rangeSalary > 0) {
            breakdown.add(new ExpenseCategoryDTO("Salary", rangeSalary));
        }
        Double rangeTotalExpense = breakdown.stream().mapToDouble(ExpenseCategoryDTO::getAmount).sum();

        // --- 2. DATA FOR TODAY ---
        Double todayInc = billRepository.sumNetAmtByDateRange(userId, today, today);
        Double todayExp = expenseRepository.sumExpenseByDateRange(userId, today, today);
        // Note: Salaries are usually monthly, so today's salary expense is typically
        // 0.0

        // --- 3. DATA FOR CURRENT MONTH ---
        Double monthInc = billRepository.sumNetAmtByDateRange(userId, monthStart, monthEnd);
        Double monthGenExp = expenseRepository.sumExpenseByDateRange(userId, monthStart, monthEnd);
        Double monthSalary = salaryRepository.sumSalaryByDateRange(userId, monthStart, monthEnd);

        Double fyInc = billRepository.sumNetAmtByDateRange(userId, fyStart, fyEnd);
        Double fyGenExp = expenseRepository.sumExpenseByDateRange(userId, fyStart, fyEnd);
        Double fySalary = salaryRepository.sumSalaryByDateRange(userId, fyStart, fyEnd);

        Double fyTotalExp = (fyGenExp != null ? fyGenExp : 0.0) + (fySalary != null ? fySalary : 0.0);

        Double monthTotalExp = (monthGenExp != null ? monthGenExp : 0.0) + (monthSalary != null ? monthSalary : 0.0);

        // --- 4. ASSEMBLE DTO ---
        DashboardAnalyticsDTO dto = new DashboardAnalyticsDTO();

        // Range Stats
        dto.setTotalIncome(rangeIncome != null ? rangeIncome : 0.0);
        dto.setTotalExpense(rangeTotalExpense);
        dto.setNetProfit(dto.getTotalIncome() - dto.getTotalExpense());
        dto.setExpenseBreakdown(breakdown);

        // Today's Stats
        dto.setTodayIncome(todayInc != null ? todayInc : 0.0);
        dto.setTodayExpense(todayExp != null ? todayExp : 0.0);

        // Monthly Stats
        dto.setMonthlyIncome(monthInc != null ? monthInc : 0.0);
        dto.setMonthlyExpense(monthTotalExp);
        dto.setMonthlyNetProfit(dto.getMonthlyIncome() - dto.getMonthlyExpense());

        // Set Financial Year Data
        dto.setYearlyIncome(fyInc != null ? fyInc : 0.0);
        dto.setYearlyExpense(fyTotalExp);

        return dto;
    }

    @Override
    public IncomeExpenseResponseDTO getIncomeExpenseAnalytics(Long userId, String period, LocalDate startDate,
            LocalDate endDate, String category) {

        // 1. Determine Default Dates if missing based on Period
        LocalDate today = LocalDate.now();
        if (startDate == null || endDate == null) {
            switch (period.toUpperCase()) {
                case "DAILY":
                    startDate = today;
                    endDate = today;
                    break;
                case "WEEKLY":
                    startDate = today.minusDays(today.getDayOfWeek().getValue() - 1); // Monday
                    endDate = startDate.plusDays(6); // Sunday
                    break;
                case "MONTHLY":
                    startDate = today.withDayOfMonth(1);
                    endDate = today.withDayOfMonth(today.lengthOfMonth());
                    break;
                case "YEARLY":
                    startDate = today.withDayOfYear(1);
                    endDate = today.withDayOfYear(today.lengthOfYear());
                    break;
                case "QUARTERLY":
                case "FINANCIAL_YEAR":
                    int year = today.getYear();
                    if (today.getMonthValue() < 4) {
                        startDate = LocalDate.of(year - 1, 4, 1);
                        endDate = LocalDate.of(year, 3, 31);
                    } else {
                        startDate = LocalDate.of(year, 4, 1);
                        endDate = LocalDate.of(year + 1, 3, 31);
                    }
                    break;
                case "PREVIOUS_YEAR":
                    int py = today.getYear() - 1;
                    if (today.getMonthValue() < 4)
                        py--;
                    startDate = LocalDate.of(py, 4, 1);
                    endDate = LocalDate.of(py + 1, 3, 31);
                    break;
                default:
                    startDate = today.minusDays(30);
                    endDate = today;
            }
        }

        // 2. Fetch Raw Data using Existing Methods/Dependencies
        // Since we don't have direct list fetches in these repos, we pull everything
        // for the user
        // and filter in Java. This guarantees dialect-free cross-compatibility.
        List<com.intellect.financeTracker.model.Bill> allBills = billRepository.findByUser_UserId(userId);
        List<com.intellect.financeTracker.model.Expense> allExpenses = expenseRepository.findByUser_UserId(userId);
        List<com.intellect.financeTracker.model.SalaryPayments> allSalaries = salaryRepository
                .findByUser_UserId(userId);

        // Filter and Category checks
        final LocalDate sDate = startDate;
        final LocalDate eDate = endDate;
        final String cat = (category != null && !category.equalsIgnoreCase("ALL")) ? category.toLowerCase() : null;

        List<com.intellect.financeTracker.model.Bill> filteredBills = allBills.stream()
                .filter(b -> b.getBillDate() != null && !b.getBillDate().isBefore(sDate)
                        && !b.getBillDate().isAfter(eDate))
                .toList();

        List<com.intellect.financeTracker.model.Expense> filteredExpenses = allExpenses.stream()
                .filter(e -> e.getExpenseDate() != null && !e.getExpenseDate().isBefore(sDate)
                        && !e.getExpenseDate().isAfter(eDate))
                .filter(e -> cat == null
                        || (e.getExpenseType() != null && e.getExpenseType().toLowerCase().contains(cat)))
                .toList();

        List<com.intellect.financeTracker.model.SalaryPayments> filteredSalaries = allSalaries.stream()
                .filter(s -> s.getPaymentDate() != null && !s.getPaymentDate().isBefore(sDate)
                        && !s.getPaymentDate().isAfter(eDate))
                // Only include salaries if Category is ALL or Salary
                .filter(s -> cat == null || cat.equals("salary"))
                .toList();

        // 3. Aggregate Data Points
        List<com.intellect.financeTracker.dto.IncomeExpenseDataPointDTO> dataPoints = new java.util.ArrayList<>();

        switch (period.toUpperCase()) {
            case "DAILY":
                double tInc = filteredBills.stream().mapToDouble(b -> b.getNetAmt() != null ? b.getNetAmt() : 0.0)
                        .sum();
                double tExp = filteredExpenses.stream().mapToDouble(e -> e.getAmount() != null ? e.getAmount() : 0.0)
                        .sum()
                        + filteredSalaries.stream()
                                .mapToDouble(s -> s.getAmount() != null ? s.getAmount().doubleValue() : 0.0).sum();
                dataPoints.add(new com.intellect.financeTracker.dto.IncomeExpenseDataPointDTO("Today", tInc, tExp));
                break;

            case "WEEKLY":
                for (int i = 0; i < 7; i++) {
                    LocalDate targetDay = startDate.plusDays(i);
                    String dayName = targetDay.getDayOfWeek().toString().substring(0, 3);
                    double dInc = filteredBills.stream().filter(b -> b.getBillDate().equals(targetDay))
                            .mapToDouble(b -> b.getNetAmt() != null ? b.getNetAmt() : 0.0).sum();
                    double dExp = filteredExpenses.stream().filter(e -> e.getExpenseDate().equals(targetDay))
                            .mapToDouble(e -> e.getAmount() != null ? e.getAmount() : 0.0).sum()
                            + filteredSalaries.stream().filter(s -> s.getPaymentDate().equals(targetDay))
                                    .mapToDouble(s -> s.getAmount() != null ? s.getAmount().doubleValue() : 0.0).sum();
                    dataPoints.add(new com.intellect.financeTracker.dto.IncomeExpenseDataPointDTO(dayName, dInc, dExp));
                }
                break;

            case "MONTHLY":
                for (int i = 1; i <= 4; i++) {
                    final int startDay = (i - 1) * 7 + 1;
                    final int endDay = (i == 4) ? sDate.lengthOfMonth() : i * 7;
                    String weekLabel = "Week " + i;

                    double wInc = filteredBills.stream()
                            .filter(b -> b.getBillDate().getMonth() == sDate.getMonth()
                                    && b.getBillDate().getDayOfMonth() >= startDay
                                    && b.getBillDate().getDayOfMonth() <= endDay)
                            .mapToDouble(b -> b.getNetAmt() != null ? b.getNetAmt() : 0.0).sum();
                    double wExp = filteredExpenses.stream()
                            .filter(e -> e.getExpenseDate().getMonth() == sDate.getMonth()
                                    && e.getExpenseDate().getDayOfMonth() >= startDay
                                    && e.getExpenseDate().getDayOfMonth() <= endDay)
                            .mapToDouble(e -> e.getAmount() != null ? e.getAmount() : 0.0).sum()
                            + filteredSalaries.stream()
                                    .filter(s -> s.getPaymentDate().getMonth() == sDate.getMonth()
                                            && s.getPaymentDate().getDayOfMonth() >= startDay
                                            && s.getPaymentDate().getDayOfMonth() <= endDay)
                                    .mapToDouble(s -> s.getAmount() != null ? s.getAmount().doubleValue() : 0.0).sum();
                    dataPoints
                            .add(new com.intellect.financeTracker.dto.IncomeExpenseDataPointDTO(weekLabel, wInc, wExp));
                }
                break;

            case "YEARLY":
            case "FINANCIAL_YEAR":
            case "PREVIOUS_YEAR":
                // 12 months from Start Date
                for (int i = 0; i < 12; i++) {
                    LocalDate targetMonth = startDate.plusMonths(i);
                    String monthLabel = targetMonth.getMonth().toString().substring(0, 3);

                    double mInc = filteredBills.stream()
                            .filter(b -> b.getBillDate().getMonth() == targetMonth.getMonth()
                                    && b.getBillDate().getYear() == targetMonth.getYear())
                            .mapToDouble(b -> b.getNetAmt() != null ? b.getNetAmt() : 0.0).sum();
                    double mExp = filteredExpenses.stream()
                            .filter(e -> e.getExpenseDate().getMonth() == targetMonth.getMonth()
                                    && e.getExpenseDate().getYear() == targetMonth.getYear())
                            .mapToDouble(e -> e.getAmount() != null ? e.getAmount() : 0.0).sum()
                            + filteredSalaries.stream()
                                    .filter(s -> s.getPaymentDate().getMonth() == targetMonth.getMonth()
                                            && s.getPaymentDate().getYear() == targetMonth.getYear())
                                    .mapToDouble(s -> s.getAmount() != null ? s.getAmount().doubleValue() : 0.0).sum();
                    dataPoints.add(
                            new com.intellect.financeTracker.dto.IncomeExpenseDataPointDTO(monthLabel, mInc, mExp));
                }
                break;

            case "QUARTERLY":
                String[] qLabels = { "Q1 (Apr-Jun)", "Q2 (Jul-Sep)", "Q3 (Oct-Dec)", "Q4 (Jan-Mar)" };
                int[][] qMonths = { { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 }, { 1, 2, 3 } };

                for (int i = 0; i < 4; i++) {
                    final int qIndex = i;
                    double qInc = filteredBills.stream()
                            .filter(b -> isMonthInQuarter(b.getBillDate().getMonthValue(), qMonths[qIndex]))
                            .mapToDouble(b -> b.getNetAmt() != null ? b.getNetAmt() : 0.0).sum();
                    double qExp = filteredExpenses.stream()
                            .filter(e -> isMonthInQuarter(e.getExpenseDate().getMonthValue(), qMonths[qIndex]))
                            .mapToDouble(e -> e.getAmount() != null ? e.getAmount() : 0.0).sum()
                            + filteredSalaries.stream()
                                    .filter(s -> isMonthInQuarter(s.getPaymentDate().getMonthValue(), qMonths[qIndex]))
                                    .mapToDouble(s -> s.getAmount() != null ? s.getAmount().doubleValue() : 0.0).sum();
                    dataPoints.add(new com.intellect.financeTracker.dto.IncomeExpenseDataPointDTO(qLabels[qIndex], qInc,
                            qExp));
                }
                break;

        }

        return new com.intellect.financeTracker.dto.IncomeExpenseResponseDTO(period.toUpperCase(),
                category != null ? category : "ALL", dataPoints);
    }

    private boolean isMonthInQuarter(int monthValue, int[] quarterMonths) {
        for (int qm : quarterMonths) {
            if (qm == monthValue)
                return true;
        }
        return false;
    }
}
