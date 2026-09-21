package com.intellect.financeTracker.controller;

import com.intellect.financeTracker.model.Vendor;
import com.intellect.financeTracker.service.VendorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/vendors")
@PreAuthorize("hasAnyRole('OWNER')")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping("/{userId}/add")
    public Vendor addVendor(
            @PathVariable("userId") Long userId,
            @RequestBody Vendor vendor) {

        return vendorService.addVendor(userId, vendor);
    }

    @PutMapping("/{userId}/update/{vendorId}")
    public Vendor updateVendor(
            @PathVariable("userId") Long userId,
            @PathVariable("vendorId") Long vendorId,
            @RequestBody Vendor vendor) {

        return vendorService.updateVendor(userId, vendorId, vendor);
    }

    @DeleteMapping("/{userId}/delete/{vendorId}")
    public String deleteVendor(
            @PathVariable("userId") Long userId,
            @PathVariable("vendorId") Long vendorId) {

        vendorService.deleteVendor(userId, vendorId);
        return "Vendor deleted successfully";
    }

    @GetMapping("/{userId}/get/{vendorId}")
    public Vendor getVendorById(
            @PathVariable("userId") Long userId,
            @PathVariable("vendorId") Long vendorId) {

        return vendorService.getVendorById(userId, vendorId);
    }

    @GetMapping("/{userId}/all")
    public List<Vendor> getAllVendors(
            @PathVariable("userId") Long userId) {

        return vendorService.getAllVendors(userId);
    }

    @GetMapping("/{userId}/names")
    public List<Map<String, Object>> getVendorNames(
            @PathVariable("userId") Long userId) {

        return vendorService.getVendorNames(userId);
    }
}
