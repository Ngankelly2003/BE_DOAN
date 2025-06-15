package com.Graduation_Be.controller;

import com.Graduation_Be.service.impl.VNPayServiceImpl;
import com.Graduation_Be.model.AdvertisementEntity;
import com.Graduation_Be.repository.AdveriserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping("/vnpay")
public class VNPayController {
    @Autowired
    private VNPayServiceImpl vnPayService;

    @Autowired
    private AdveriserRespository adveriserRespository;

    @PostMapping("/create-payment")
    public ResponseEntity<String> createPayment(@RequestParam long amount, @RequestParam String orderInfo) {
        try {
            String paymentUrl = vnPayService.createPayment(amount, orderInfo);
            return ResponseEntity.ok(paymentUrl);
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating payment URL");
        }
    }

    @GetMapping("/payment-callback")
    public ResponseEntity<String> paymentCallback(@RequestParam Map<String, String> queryParams) {
        String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");
        String vnp_TxnRef = queryParams.get("vnp_TxnRef");
        String advertisementIdStr = queryParams.get("advertisementId");
        if ("00".equals(vnp_ResponseCode) && vnp_TxnRef != null && advertisementIdStr != null) {
            try {
                Long advertisementId = Long.parseLong(advertisementIdStr);
                AdvertisementEntity ad = adveriserRespository.findById(advertisementId).orElse(null);
                if (ad != null) {
                    ad.setTransactionRef(vnp_TxnRef);
                    adveriserRespository.save(ad);
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
            }
            return ResponseEntity.ok("successful");
        } else {
            return ResponseEntity.ok("failed");
        }
    }
}