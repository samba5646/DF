package com.geethamsoft.userservice.service;

import com.geethamsoft.userservice.dto.*;
import com.geethamsoft.userservice.exception.*;
import com.geethamsoft.userservice.model.User;
import com.geethamsoft.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${spring.mail.from}")

    private String fromEmail;

    @Transactional
    public User register(UserRegistrationDTO registrationDTO) {
        validateRegistrationDTO(registrationDTO);

        if (userRepository.findByEmail(registrationDTO.getEmail()) != null) {
            logger.error("Email {} is already registered.", registrationDTO.getEmail());
            throw new UserAlreadyExistsException("Email is already registered.");
        }

        String otp = generateOTP();
        emailService.sendVerificationEmail(registrationDTO.getEmail(), otp);

        User user = new User();
        user.setName(registrationDTO.getName());
        user.setEmail(registrationDTO.getEmail());
        user.setMobileNumber(registrationDTO.getMobileNumber());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setOtp(otp);
        user.setAccountVerified(false);
        user.setRememberMe(false);

        return userRepository.save(user);
    }

    public User login(UserLoginDTO loginDTO) {
        validateLoginDTO(loginDTO);
        User user = null;

        if (loginDTO.getLoginType() == LoginType.EMAIL) {
            user = userRepository.findByEmail(loginDTO.getEmail());
        } else if (loginDTO.getLoginType() == LoginType.GOOGLE) {
            String googleId = loginDTO.getGoogleId();

            if (googleId != null) {
                user = findByGoogleId(googleId);
            }
        } else if (loginDTO.getLoginType() == LoginType.FACEBOOK) {
            String facebookId = loginDTO.getFacebookId();

            if (facebookId != null) {
                user = findByFacebookId(facebookId);
            }
        }

        if (user == null) {
            throw new UserNotFoundException("User not found.");
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password.");
        }

        return user;
    }

    @Transactional
    public void changePassword(String userId, PasswordChangeDTO changeDTO) {
        validatePasswordChangeDTO(changeDTO);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found for ID: {}", userId);
                    return new UserNotFoundException("User not found.");
                });

        if (!changeDTO.getNewPassword().equals(changeDTO.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match.");
        }

        user.setPassword(passwordEncoder.encode(changeDTO.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void requestPasswordReset(PasswordResetRequestDTO requestDTO) {
        validatePasswordResetRequestDTO(requestDTO);
        User user = userRepository.findByEmail(requestDTO.getEmail());

        if (user == null) {
            logger.error("User not found for email: {}", requestDTO.getEmail());
            throw new UserNotFoundException("User not found.");
        }

        String otp = generateOTP();
        emailService.sendPasswordResetEmail(requestDTO.getEmail(), otp);

        user.setOtp(otp);
        userRepository.save(user);
    }

    @Transactional
    public void resetPassword(String email, PasswordResetDTO resetDTO) {
        validatePasswordResetDTO(resetDTO);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            logger.error("User not found for email: {}", email);
            throw new UserNotFoundException("User not found.");
        }

        if (!resetDTO.getOtp().equals(user.getOtp())) {
            throw new InvalidOTPException("Invalid OTP.");
        }

        if (!resetDTO.getNewPassword().equals(resetDTO.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match.");
        }

        user.setPassword(passwordEncoder.encode(resetDTO.getNewPassword()));
        user.setOtp(null);
        userRepository.save(user);
    }

    private String generateOTP() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    private void validateRegistrationDTO(UserRegistrationDTO registrationDTO) {
        if (registrationDTO == null || registrationDTO.getName() == null ||
                registrationDTO.getEmail() == null || registrationDTO.getMobileNumber() == null ||
                registrationDTO.getPassword() == null) {
            logger.error("Invalid registration data.");
            throw new InvalidRequestException("Invalid registration data.");
        }
    }

    private void validateLoginDTO(UserLoginDTO loginDTO) {
        if (loginDTO == null || loginDTO.getEmail() == null || loginDTO.getPassword() == null) {
            logger.error("Invalid login data.");
            throw new InvalidRequestException("Invalid login data.");
        }
    }


    private void validatePasswordChangeDTO(PasswordChangeDTO changeDTO) {
        if (changeDTO == null || changeDTO.getNewPassword() == null ||
                changeDTO.getConfirmPassword() == null) {
            logger.error("Invalid password change data.");
            throw new InvalidRequestException("Invalid password change data.");
        }
    }

    private void validatePasswordResetRequestDTO(PasswordResetRequestDTO requestDTO) {
        if (requestDTO == null || requestDTO.getEmail() == null) {
            logger.error("Invalid password reset request.");
            throw new InvalidRequestException("Invalid password reset request.");
        }
    }

    private void validatePasswordResetDTO(PasswordResetDTO resetDTO) {
        if (resetDTO == null || resetDTO.getOtp() == null || resetDTO.getNewPassword() == null ||
                resetDTO.getConfirmPassword() == null) {
            logger.error("Invalid password reset data.");
            throw new InvalidRequestException("Invalid password reset data.");
        }
    }

    public User findByFacebookId(String facebookId) {
        return userRepository.findByFacebookId(facebookId);
    }

    public User findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId);
    }

    public User findById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found for ID: {}", userId);
                    return new UserNotFoundException("User not found.");
                });
    }
}
