package com.intellect.financeTracker.repository;

import com.intellect.financeTracker.model.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillItemRepository extends JpaRepository<BillItem, Long> {

    // Fetch all items for a specific bill
    List<BillItem> findByBill_BillId(Long billId);
}
