package com.intellect.financeTracker.repository;

import com.intellect.financeTracker.model.SalaryPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalaryPaymentRepository extends JpaRepository<SalaryPayments, Long> {

        List<SalaryPayments> findByEmployee_EmpId(Long empId);

        @Query("SELECT s FROM SalaryPayments s WHERE s.employee.user.userId = :userId")
        List<SalaryPayments> findByUser_UserId(@Param("userId") Long userId);

        @Query("SELECT SUM(s.amount) FROM SalaryPayments s " +
                        "WHERE s.employee.user.userId = :userId " +
                        "AND s.paymentDate BETWEEN :startDate AND :endDate")
        Double sumSalaryByDateRange(
                        @Param("userId") Long userId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

        @Query("SELECT COUNT(s) > 0 FROM SalaryPayments s WHERE s.employee.empId = :empId " +
                        "AND MONTH(s.paymentDate) = :month AND YEAR(s.paymentDate) = :year")
        boolean existsByEmployeeAndMonth(@Param("empId") Long empId,
                        @Param("month") int month,
                        @Param("year") int year);

        // @Query("SELECT SUM(s.amount) FROM SalaryPayments s WHERE
        // s.employee.user.userId = :userId AND s.paymentDate BETWEEN :startDate AND
        // :endDate")
        // BigDecimal sumSalaryByDateRange(@Param("userId") Integer userId,
        // @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate
        // endDate);

}
