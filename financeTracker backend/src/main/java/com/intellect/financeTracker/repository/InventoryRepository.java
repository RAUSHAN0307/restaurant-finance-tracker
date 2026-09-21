package com.intellect.financeTracker.repository;

import com.intellect.financeTracker.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByUserId(Long userId);

    Optional<Inventory> findByInventoryIdAndUserId(Long inventoryId, Long userId);

    List<Inventory> findByVendorId(Long vendorId);

    List<Inventory> findByVendorIdAndUserId(Long vendorId, Long userId);

    @Query("SELECT i FROM Inventory i WHERE (i.quantity - i.usedQuantity) <= i.minimumQuantity")
    List<Inventory> findLowStockItems();

    @Query("SELECT i FROM Inventory i WHERE i.userId = :userId AND (i.quantity - i.usedQuantity) <= i.minimumQuantity")
    List<Inventory> findLowStockItemsByUserId(@Param("userId") Long userId);
}
