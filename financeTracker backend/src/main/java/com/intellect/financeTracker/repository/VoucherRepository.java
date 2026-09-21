package com.intellect.financeTracker.repository;

import com.intellect.financeTracker.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    List<Voucher> findByUser_UserId(Long userId);

    // Method 1: Hard Delete (Physical Removal)
    @Transactional
    @Modifying
    void deleteByExpireDateBefore(Date date);

    // Method 2: Soft Deactivate (Set status to 0 instead of deleting)
    @Transactional
    @Modifying
    @Query("UPDATE Voucher v SET v.status = 0 WHERE v.expireDate < :today")
    void deactivateExpiredVouchers(Date today);
}