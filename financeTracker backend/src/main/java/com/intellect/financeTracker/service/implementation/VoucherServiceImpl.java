package com.intellect.financeTracker.service.implementation;

import com.intellect.financeTracker.service.*;

import com.intellect.financeTracker.repository.VoucherRepository;
import com.intellect.financeTracker.model.User;
import com.intellect.financeTracker.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherServiceInterface {
    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public Voucher insertRecord(Voucher voucher, Long userId) {
        // Validation for the URL parameter
        if (userId == null) {
            throw new RuntimeException("User ID must be provided in the URL.");
        }

        // Basic data validation
        if (voucher.getCode() == null || voucher.getCode().trim().isEmpty()) {
            throw new RuntimeException("Voucher code cannot be empty.");
        }
        if (voucher.getPercentage() == null || voucher.getPercentage() <= 0) {
            throw new RuntimeException("Voucher percentage must be greater than 0.");
        }

        // Create a User proxy object to link the ID
        User user = new User();
        user.setUserId(userId);

        // Associate the User with the Voucher
        voucher.setUser(user);

        return voucherRepository.save(voucher);
    }

    @Override
    public List<Voucher> getVouchersByUserId(Long userId) {
        if (userId == null) {
            throw new RuntimeException("User ID must be provided to fetch vouchers.");
        }
        return voucherRepository.findByUser_UserId(userId);
    }

    @Override
    public Voucher updateRecord(Long id, Voucher updatedVoucher) {
        Voucher existing = getById(id);
        if (updatedVoucher.getCode() != null)
            existing.setCode(updatedVoucher.getCode());
        if (updatedVoucher.getPercentage() != null)
            existing.setPercentage(updatedVoucher.getPercentage());
        if (updatedVoucher.getMinAmount() != null)
            existing.setMinAmount(updatedVoucher.getMinAmount());
        if (updatedVoucher.getExpireDate() != null)
            existing.setExpireDate(updatedVoucher.getExpireDate());
        if (updatedVoucher.getStatus() != null)
            existing.setStatus(updatedVoucher.getStatus());
        return voucherRepository.save(existing);
    }

    @Override
    public void deleteRecord(Long id) {
        if (!voucherRepository.existsById(id)) {
            throw new RuntimeException("Voucher not found.");
        }
        voucherRepository.deleteById(id);
    }

    @Override
    public List<Voucher> getAll() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher getById(Long id) {
        return voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found with ID: " + id));
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void removeExpiredVouchers() {
        Date today = new Date();

        // This will find all vouchers where expireDate < today and delete them
        voucherRepository.deleteByExpireDateBefore(today);

        System.out.println("Cron Job executed: Expired vouchers removed on " + today);
    }
}
