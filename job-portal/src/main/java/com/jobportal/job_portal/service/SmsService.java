package com.jobportal.job_portal.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.account.sid:AC_dummy_sid}")
    private String accountSid;

    @Value("${twilio.auth.token:dummy_token}")
    private String authToken;

    @Value("${twilio.phone.number:+1234567890}")
    private String twilioPhoneNumber;

    public void sendOtpSms(String toPhoneNumber, String otp) {
        if (toPhoneNumber == null || toPhoneNumber.trim().isEmpty()) {
            System.err.println("No phone number provided. Skipping SMS, OTP is: " + otp);
            return;
        }

        try {
            // Attempt to initialize Twilio. If dummy credentials are used, it might fail upon creation.
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(twilioPhoneNumber),
                    "Your Job Portal Application OTP is: " + otp
            ).create();
            System.out.println("SMS sent successfully! SID: " + message.getSid());
        } catch (Exception e) {
            System.err.println("Failed to send SMS via Twilio. Ensure credentials are valid. Error: " + e.getMessage());
            System.out.println("Falling back to console OTP display.");
            System.out.println("=================================================");
            System.out.println("OTP FOR JOB APPLICATION (Fallback): " + otp);
            System.out.println("=================================================");
        }
    }
}
