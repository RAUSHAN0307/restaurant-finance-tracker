package com.intellect.financeTracker.repository;

import com.intellect.financeTracker.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
        List<Bill> findByUser_UserId(Long userId);

        // @Query(value = "SELECT SUM(net_amt) FROM Bill WHERE user_id = :userId AND
        // bill_date BETWEEN :startDate AND :endDate", nativeQuery = true)
        // Double sumNetAmtByDateRange(@Param("userId") Integer userId,
        // @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate
        // endDate);

        @Query(value = "SELECT SUM(net_amt) FROM BILL " +
                        "WHERE user_id = :userId " +
                        "AND bill_date BETWEEN :startDate AND :endDate", nativeQuery = true)
        Double sumNetAmtByDateRange(@Param("userId") Long userId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

}
