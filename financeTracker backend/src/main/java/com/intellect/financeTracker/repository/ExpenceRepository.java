package com.intellect.financeTracker.repository;

import com.intellect.financeTracker.dto.ExpenseCategoryDTO;
import com.intellect.financeTracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenceRepository extends JpaRepository<Expense, Long> {
        List<Expense> findByUser_UserId(Long userId);

        @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.userId = :userId AND e.expenseDate BETWEEN :startDate AND :endDate")
        Double sumExpenseByDateRange(@Param("userId") Long userId, @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

        // Group totals by category for a specific user and date range
        @Query("SELECT new com.intellect.financeTracker.dto.ExpenseCategoryDTO(e.expenseType, SUM(e.amount)) " +
                        "FROM Expense e WHERE e.user.userId = :userId " +
                        "AND e.expenseDate BETWEEN :startDate AND :endDate " +
                        "GROUP BY e.expenseType")
        List<ExpenseCategoryDTO> getExpenseBreakdown(@Param("userId") Long userId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

        // @Query("SELECT new
        // com.intellect.financeTracker.dto.ExpenseCategoryDTO(e.category,
        // SUM(e.amount)) " +
        // "FROM Expense e WHERE e.user.userId = :userId " +
        // "AND e.expenseDate BETWEEN :startDate AND :endDate " +
        // "GROUP BY e.category")
        // List<ExpenseCategoryDTO> getExpenseBreakdown(@Param("userId") Integer userId,
        // @Param("startDate") LocalDate startDate,
        // @Param("endDate") LocalDate endDate);
}
