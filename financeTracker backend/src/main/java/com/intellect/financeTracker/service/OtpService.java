//package com.intellect.financeTracker.serviceImpl;
//
//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import jakarta.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class OtpService {
//
//    @Value("${twilio.account_sid}")
//    private String accountSid;
//
//    @Value("${twilio.auth_token}")
//    private String authToken;
//
//    @Value("${twilio.phone_number}")
//    private String twilioPhone;
//
//    // Use a ConcurrentHashMap if multiple users register at once
//    private final Map<String, String> otpCache = new HashMap<>();
//
//    @PostConstruct
//    public void initTwilio() {
//        Twilio.init(accountSid, authToken);
//    }
//
//    public String sendOtp(String phone) {
//        String otp = String.valueOf((int)(Math.random() * 9000) + 1000);
//        otpCache.put(phone, otp);
//
//        try {
//            Message.creator(
//                new PhoneNumber(phone),    // To
//                new PhoneNumber(twilioPhone), // From
//                "Your Verification Code is: " + otp
//            ).create();
//            return "OTP Sent successfully";
//        } catch (Exception e) {
//            return "Error sending SMS: " + e.getMessage();
//        }
//    }
//
//    public boolean validateOtp(String phone, String otp) {
//        return otp != null && otp.equals(otpCache.get(phone));
//    }
//    
//    public void clearOtp(String phone) {
//        otpCache.remove(phone);
//    }
//}