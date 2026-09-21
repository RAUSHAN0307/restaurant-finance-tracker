package com.intellect.financeTracker.repository;

import com.intellect.financeTracker.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

    List<Vendor> findByUserId(Long userId);

    Optional<Vendor> findByVendorIdAndUserId(Long vendorId, Long userId);

    List<Vendor> findByAmountPendingGreaterThan(double amount);

    @Query("SELECT v FROM Vendor v WHERE v.userId = :userId AND v.amountPending > 0 AND v.dueDate <= :notifyBefore")
    List<Vendor> findVendorsWithUpcomingDuePayments(@Param("userId") Long userId,
            @Param("notifyBefore") LocalDate notifyBefore);

    @Query("SELECT v.vendorId, v.name FROM Vendor v WHERE v.userId = :userId")
    List<Object[]> findVendorIdAndNameByUserId(@Param("userId") Long userId);
}
