package com.intellect.financeTracker.service.implementation;

import com.intellect.financeTracker.service.*;

import com.intellect.financeTracker.repository.BillRepository;
import com.intellect.financeTracker.repository.VoucherRepository;
import com.intellect.financeTracker.model.Bill;
// import com.intellect.financeTracker.model.BillItem;
import com.intellect.financeTracker.model.User;
import com.intellect.financeTracker.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BillServiceImpl implements BillServiceInteface {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public Bill insertRecord(Bill bill, Long userId, Long voucherId) {
        // // Validation: IDs must be present
        // if (userId == null || voucherId == null) {
        // throw new RuntimeException("User ID and Voucher ID are mandatory in the
        // URL.");
        // }
        //
        // // Validation: Essential Data
        // if (bill.getNetAmt() == null || bill.getNetAmt() <= 0) {
        // throw new RuntimeException("Net Amount must be greater than zero.");
        // }
        // if (bill.getBillDate() == null) {
        // bill.setBillDate(LocalDate.now()); // Default to today if null
        // }
        //
        // // Link User
        // User user = new User();
        // user.setUserId(userId);
        // bill.setUser(user);
        //
        // if (voucherId != null && voucherId > 0) {
        // Voucher voucher = voucherRepository.findById(voucherId)
        // .orElseThrow(() -> new RuntimeException("Voucher not found with ID: " +
        // voucherId));
        //
        // // APPLY CONDITION: Only if voucher exists
        // if (bill.getTotalAmt() < voucher.getMinAmount()) {
        // throw new RuntimeException("Validation Failed: Bill total (" +
        // bill.getTotalAmt() +
        // ") is less than Voucher Minimum (" + voucher.getMinAmount() + ")");
        // }
        //
        // // Link the voucher to the bill
        // bill.setVoucher(voucher);
        //
        // // OPTIONAL: Auto-calculate Net Amount based on Voucher Percentage
        // double discount = (bill.getTotalAmt() * voucher.getPercentage()) / 100;
        // bill.setNetAmt(bill.getTotalAmt() + bill.getTaxAmt() - discount);
        //
        // } else {
        // // No voucher applied - standard calculation
        // bill.setVoucher(null);
        // bill.setNetAmt(bill.getTotalAmt() + bill.getTaxAmt());
        // }
        //
        // return billRepository.save(bill);

        User user = new User();
        user.setUserId(userId);
        bill.setUser(user);

        bill.setBillDate(LocalDate.now());
        bill.setTotalAmt(0.0);
        bill.setTaxAmt(0.0);
        bill.setNetAmt(0.0);

        // Voucher is usually added at the end, so we can leave it null for now
        // LOGIC: If voucherId is 0, we don't look for a voucher
        if (voucherId != null && voucherId > 0) {
            Voucher v = voucherRepository.findById(voucherId)
                    .orElseThrow(() -> new RuntimeException("Voucher not found"));
            bill.setVoucher(v);
        } else {
            bill.setVoucher(null); // This requires the ALTER TABLE fix above!
        }

        return billRepository.save(bill);
    }

    @Override
    public Bill updateRecord(Long id, Bill updatedBill) {
        Bill existing = getById(id);

        if (updatedBill.getTotalAmt() != null)
            existing.setTotalAmt(updatedBill.getTotalAmt());
        if (updatedBill.getTaxAmt() != null)
            existing.setTaxAmt(updatedBill.getTaxAmt());
        if (updatedBill.getNetAmt() != null)
            existing.setNetAmt(updatedBill.getNetAmt());
        if (updatedBill.getPaymentMode() != null)
            existing.setPaymentMode(updatedBill.getPaymentMode());
        if (updatedBill.getPhoneNo() != null)
            existing.setPhoneNo(updatedBill.getPhoneNo());

        return billRepository.save(existing);
    }

    @Override
    public void deleteRecord(Long id) {
        if (!billRepository.existsById(id))
            throw new RuntimeException("Bill not found.");
        billRepository.deleteById(id);
    }

    @Override
    public List<Bill> getAll() {
        return billRepository.findAll();
    }

    @Override
    public Bill getById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill ID " + id + " not found."));
    }

    @Override
    public List<Bill> getByUserId(Long userId) {
        return billRepository.findByUser_UserId(userId);
    }
}
