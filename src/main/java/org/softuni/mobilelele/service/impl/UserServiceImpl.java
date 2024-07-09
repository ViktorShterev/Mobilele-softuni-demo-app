package org.softuni.mobilelele.service.impl;

import org.softuni.mobilelele.model.dto.UserRegistrationDTO;
import org.softuni.mobilelele.model.entity.User;
import org.softuni.mobilelele.model.events.UserRegisteredEvent;
import org.softuni.mobilelele.repository.UserRepository;
import org.softuni.mobilelele.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {

        this.userRepository.save(map(userRegistrationDTO));

//        this.applicationEventPublisher
//                .publishEvent(new UserRegisteredEvent(
//                        "UserService",
//                        userRegistrationDTO.email(),
//                        userRegistrationDTO.fullName()));
    }

    private User map(UserRegistrationDTO userRegistrationDTO) {
        return new User()
                .setIsActive(false)
                .setFirstName(userRegistrationDTO.firstName())
                .setLastName(userRegistrationDTO.lastName())
                .setEmail(userRegistrationDTO.email())
                .setPassword(passwordEncoder.encode(userRegistrationDTO.password()));
    }
}
