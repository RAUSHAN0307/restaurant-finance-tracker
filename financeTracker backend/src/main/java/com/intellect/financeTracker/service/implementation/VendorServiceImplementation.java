package com.intellect.financeTracker.service.implementation;

import com.intellect.financeTracker.service.*;

import com.intellect.financeTracker.model.Vendor;
import com.intellect.financeTracker.repository.UserRepository;
import com.intellect.financeTracker.repository.VendorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VendorServiceImplementation implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private UserRepository userRepository;

    // A fast existence check rather than pulling the full object hierarchy
    private void validateUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public Vendor addVendor(Long userId, Vendor vendor) {
        validateUserExists(userId);
        vendor.setUserId(userId);
        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor updateVendor(Long userId, Long vendorId, Vendor vendor) {
        validateUserExists(userId);

        Vendor existingVendor = vendorRepository.findByVendorIdAndUserId(vendorId, userId)
                .orElseThrow(() -> new RuntimeException("Vendor not found for this user"));

        existingVendor.setName(vendor.getName());
        existingVendor.setEmail(vendor.getEmail());
        existingVendor.setPhoneNumber(vendor.getPhoneNumber());
        existingVendor.setAmountPending(vendor.getAmountPending());
        existingVendor.setAddress(vendor.getAddress());
        existingVendor.setNote(vendor.getNote());
        existingVendor.setDueDate(vendor.getDueDate());
        existingVendor.setCreatedAt(vendor.getCreatedAt());

        return vendorRepository.save(existingVendor);
    }

    @Override
    public void deleteVendor(Long userId, Long vendorId) {
        validateUserExists(userId);

        Vendor vendor = vendorRepository.findByVendorIdAndUserId(vendorId, userId)
                .orElseThrow(() -> new RuntimeException("Vendor not found for this user"));

        vendorRepository.delete(vendor);
    }

    @Override
    public Vendor getVendorById(Long userId, Long vendorId) {
        validateUserExists(userId);

        return vendorRepository.findByVendorIdAndUserId(vendorId, userId)
                .orElseThrow(() -> new RuntimeException("Vendor not found for this user"));
    }

    @Override
    public List<Vendor> getAllVendors(Long userId) {
        validateUserExists(userId);
        return vendorRepository.findByUserId(userId);
    }

    @Override
    public List<Map<String, Object>> getVendorNames(Long userId) {
        validateUserExists(userId);
        List<Object[]> results = vendorRepository.findVendorIdAndNameByUserId(userId);

        return results.stream()
                .map(row -> Map.of(
                        "vendorId", row[0],
                        "name", row[1]))
                .toList();
    }
}