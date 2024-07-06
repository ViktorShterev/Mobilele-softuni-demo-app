package org.softuni.mobilelele.service;

import org.softuni.mobilelele.model.events.UserRegisteredEvent;

public interface UserActivationService {

    void userRegistered(UserRegisteredEvent event);

    void cleanupObsoleteActivationLinks();

    String createActivationCode(String userEmail);
}
