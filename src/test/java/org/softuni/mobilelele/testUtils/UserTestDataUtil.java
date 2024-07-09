package org.softuni.mobilelele.testUtils;

import org.softuni.mobilelele.model.entity.User;
import org.softuni.mobilelele.model.entity.UserRole;
import org.softuni.mobilelele.model.enums.RolesEnum;
import org.softuni.mobilelele.repository.UserRepository;
import org.softuni.mobilelele.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserTestDataUtil {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public User createTestUser(String email) {
        return createUser(email, List.of(RolesEnum.USER));
    }

    public User createTestAdmin(String email) {
        return createUser(email, List.of(RolesEnum.ADMIN, RolesEnum.USER));
    }

    public User createUser(String email, List<RolesEnum> roles) {

        List<UserRole> allRoles = this.userRoleRepository.findAllByNameIn(roles);

        User user = new User()
                .setIsActive(true)
                .setEmail(email)
                .setPassword("topsecret")
                .setCreated(LocalDateTime.now())
                .setFirstName("Test First Name")
                .setLastName("Test Last Name")
                .setRoles(allRoles);

        this.userRepository.save(user);

        return user;
    }

    public void cleanUp() {
        this.userRepository.deleteAll();
    }
}
