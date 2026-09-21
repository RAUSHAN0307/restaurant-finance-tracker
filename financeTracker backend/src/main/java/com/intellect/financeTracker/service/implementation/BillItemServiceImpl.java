package com.intellect.financeTracker.service.implementation;

import com.intellect.financeTracker.service.*;

import com.intellect.financeTracker.repository.BillItemRepository;
import com.intellect.financeTracker.repository.BillRepository;
import com.intellect.financeTracker.repository.MenuItemRepository;
import com.intellect.financeTracker.model.Bill;
import com.intellect.financeTracker.model.BillItem;
import com.intellect.financeTracker.model.MenuItem;
import com.intellect.financeTracker.model.Voucher;
// import com.intellect.financeTracker.service.BillServiceInteface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillItemServiceImpl implements BillItemServiceInterface {

    @Autowired
    private BillItemRepository billItemRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public BillItem insertRecord(BillItem billItem, Long billId, Long itemId) {
        // // Validation: Ensure IDs are present
        // if (billId == null || itemId == null) {
        // throw new RuntimeException("Bill ID and Menu Item ID must be provided in the
        // URL.");
        // }
        //
        // // Essential data check
        // if (billItem.getQuantity() == null || billItem.getQuantity() <= 0) {
        // throw new RuntimeException("Quantity must be at least 1.");
        // }
        //
        // // Link the Bill (Parent 1)
        // Bill bill = new Bill();
        // bill.setBillId(billId);
        // billItem.setBill(bill);
        //
        // // Link the MenuItem (Parent 2)
        // MenuItem item = new MenuItem();
        // item.setItemId(itemId);
        // billItem.setMenuItem(item);
        //
        // // Auto-calculate total price
        // if (billItem.getCost() != null) {
        // billItem.setTotalPrice(billItem.getCost() * billItem.getQuantity());
        // }
        //
        // return billItemRepository.save(billItem);

        // 1. Link parent Bill and MenuItem
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        billItem.setBill(bill);
        billItem.setMenuItem(menuItem);

        // --- FIX STARTS HERE ---
        // If the frontend didn't send a cost, use the price from the Menu
        if (billItem.getCost() == null) {
            billItem.setCost(menuItem.getPrice());
        }
        // --- FIX ENDS HERE ---

        // 2. Calculate Item Total (Now it won't crash because cost is guaranteed)
        double itemTotalPrice = billItem.getQuantity() * billItem.getCost();
        billItem.setTotalPrice(itemTotalPrice);

        // 3. Update Parent Bill Totals
        // Important: Ensure bill.getTotalAmt() is not null (default to 0.0)
        double currentBillTotal = (bill.getTotalAmt() != null) ? bill.getTotalAmt() : 0.0;
        double newTotal = currentBillTotal + itemTotalPrice;

        double gstRate = 18.0;
        double newTax = (newTotal * gstRate) / 100;
        double newNet = newTotal + newTax;

        // 4. Voucher Re-Validation
        if (bill.getVoucher() != null) {
            Voucher v = bill.getVoucher();
            if (newTotal >= v.getMinAmount()) {
                double discount = (newTotal * v.getPercentage()) / 100;
                newNet = newNet - discount;
            }
        }

        // Update the Bill object
        bill.setTotalAmt(newTotal);
        bill.setTaxAmt(newTax);
        bill.setNetAmt(newNet);

        // Save both
        billRepository.save(bill);
        return billItemRepository.save(billItem);
    }

    @Override
    public BillItem updateRecord(Long id, BillItem updatedItem) {
        BillItem existing = getById(id);

        if (updatedItem.getQuantity() != null)
            existing.setQuantity(updatedItem.getQuantity());
        if (updatedItem.getCost() != null)
            existing.setCost(updatedItem.getCost());

        // Recalculate total price on update
        existing.setTotalPrice(existing.getCost() * existing.getQuantity());

        return billItemRepository.save(existing);
    }

    @Override
    public void deleteRecord(Long id) {
        if (!billItemRepository.existsById(id)) {
            throw new RuntimeException("BillItem not found with ID: " + id);
        }
        billItemRepository.deleteById(id);
    }

    @Override
    public List<BillItem> getByBillId(Long billId) {
        return billItemRepository.findByBill_BillId(billId);
    }

    @Override
    public BillItem getById(Long id) {
        return billItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BillItem not found with ID: " + id));
    }
}
