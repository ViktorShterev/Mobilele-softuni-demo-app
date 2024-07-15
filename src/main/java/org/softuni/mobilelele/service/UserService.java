package org.softuni.mobilelele.service;

import org.softuni.mobilelele.model.dto.UserRegistrationDTO;
import org.springframework.security.core.Authentication;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO);

    void createUserIfNotExist(String email, String name);

    Authentication login(String email);
}
