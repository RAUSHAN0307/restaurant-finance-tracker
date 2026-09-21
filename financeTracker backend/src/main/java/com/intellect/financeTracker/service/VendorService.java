package com.intellect.financeTracker.service;

import com.intellect.financeTracker.model.Vendor;
import java.util.List;
import java.util.Map;

public interface VendorService {

    Vendor addVendor(Long userId, Vendor vendor);

    Vendor updateVendor(Long userId, Long vendorId, Vendor vendor);

    void deleteVendor(Long userId, Long vendorId);

    Vendor getVendorById(Long userId, Long vendorId);

    List<Vendor> getAllVendors(Long userId);

    List<Map<String, Object>> getVendorNames(Long userId);
}