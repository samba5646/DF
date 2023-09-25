package com.geethamsoft.userservice.service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@PropertySource("classpath:application.yml")
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationEmail(String to, String otp) {
        String subject = "Email Verification";
        String text = "Your verification OTP is: " + otp;

        sendEmail(to, subject, text);
    }

    public void sendPasswordResetEmail(String to, String otp) {
        String subject = "Password Reset";
        String text = "Your password reset OTP is: " + otp;

        sendEmail(to, subject, text);
    }

    private void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // Enable HTML content if needed

            emailSender.send(message);
        } catch (MessagingException e) {
            // Handle email sending exceptions
            e.printStackTrace();
            // You can log or throw a custom exception here
        }
    }
}
