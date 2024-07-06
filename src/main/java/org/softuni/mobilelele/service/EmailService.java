package org.softuni.mobilelele.service;

public interface EmailService {

    void sendRegistrationEmail(String userEmail, String username, String activationCode);
}
