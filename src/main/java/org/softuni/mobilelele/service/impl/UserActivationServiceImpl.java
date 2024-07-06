package org.softuni.mobilelele.service.impl;

import org.softuni.mobilelele.exceptions.ObjectNotFoundException;
import org.softuni.mobilelele.model.entity.UserActivationCode;
import org.softuni.mobilelele.model.events.UserRegisteredEvent;
import org.softuni.mobilelele.repository.UserActivationCodeRepository;
import org.softuni.mobilelele.repository.UserRepository;
import org.softuni.mobilelele.service.EmailService;
import org.softuni.mobilelele.service.UserActivationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Random;

@Service
public class UserActivationServiceImpl implements UserActivationService {

    private static final String ACTIVATION_CODE_SYMBOLS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ACTIVATION_CODE_LENGTH = 20;

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final UserActivationCodeRepository activationCodeRepository;

    public UserActivationServiceImpl(EmailService emailService, UserRepository userRepository, UserActivationCodeRepository activationCodeRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.activationCodeRepository = activationCodeRepository;
    }

    @EventListener(UserRegisteredEvent.class)
    @Override
    public void userRegistered(UserRegisteredEvent event) {

        String activationCode = createActivationCode(event.getUserEmail());

        this.emailService.sendRegistrationEmail(
                event.getUserEmail(),
                event.getUsername(),
                activationCode);
    }

    @Override
    public void cleanupObsoleteActivationLinks() {
        System.out.println("NOT NOW");
    }

    @Override
    public String createActivationCode(String userEmail) {

        String activationCode = generateActivationCode();

        UserActivationCode userActivationCode = new UserActivationCode();

        userActivationCode.setActivationCode(activationCode);
        userActivationCode.setCreated(Instant.now());
        userActivationCode.setUser(this.userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ObjectNotFoundException("User not found!")));

        this.activationCodeRepository.save(userActivationCode);

        return activationCode;
    }

    private static String generateActivationCode() {
        StringBuilder activationCode = new StringBuilder();

        Random random = new SecureRandom();

        for (int i = 0; i < ACTIVATION_CODE_LENGTH; i++) {
            int nextInt = random.nextInt(ACTIVATION_CODE_SYMBOLS.length());
            activationCode.append(ACTIVATION_CODE_SYMBOLS.charAt(nextInt));
        }

        return activationCode.toString();
    }
}
