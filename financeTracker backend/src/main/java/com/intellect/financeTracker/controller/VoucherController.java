package com.intellect.financeTracker.controller;

import com.intellect.financeTracker.model.Voucher;
import com.intellect.financeTracker.service.VoucherServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vouchers")
@CrossOrigin("http://localhost:4200")
@PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
public class VoucherController {

    @Autowired
    private VoucherServiceInterface voucherService;

    // 1. INSERT: POST /vouchers/1
    @PostMapping("/{userId}")
    public ResponseEntity<Voucher> create(@PathVariable("userId") Long userId, @RequestBody Voucher voucher) {
        return ResponseEntity.ok(voucherService.insertRecord(voucher, userId));
    }

    // 2. FIND ALL BY USER: GET /vouchers/user/1
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Voucher>> getByUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(voucherService.getVouchersByUserId(userId));
    }

    // 3. GET SINGLE: GET /vouchers/5
    @GetMapping("/{id}")
    public ResponseEntity<Voucher> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(voucherService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voucher> updateVoucher(
            @PathVariable("id") Long id,
            @RequestBody Voucher voucher) {

        Voucher updated = voucherService.updateRecord(id, voucher);
        return ResponseEntity.ok(updated);
    }

    // 4. DELETE: DELETE /vouchers/5
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        voucherService.deleteRecord(id);
        return ResponseEntity.ok("Voucher Deleted");
    }
}
