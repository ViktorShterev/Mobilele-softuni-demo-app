package org.softuni.mobilelele.testUtils;

import org.softuni.mobilelele.model.entity.User;
import org.softuni.mobilelele.model.entity.UserRole;
import org.softuni.mobilelele.model.enums.RolesEnum;
import org.softuni.mobilelele.repository.UserRepository;
import org.softuni.mobilelele.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserTestDataUtil {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public User createTestUser() {
        return createUser(List.of(RolesEnum.USER));
    }

    public User createTestAdmin() {
        return createUser(List.of(RolesEnum.ADMIN));
    }

    public User createUser(List<RolesEnum> roles) {

        List<UserRole> allRoles = this.userRoleRepository.findAllByNameIn(roles);

        User user = new User()
                .setIsActive(true)
                .setEmail("test@mail.com")
                .setFirstName("Test First Name")
                .setLastName("Test Last Name")
                .setRoles(allRoles);

        return this.userRepository.save(user);
    }

    public void cleanUp() {
        this.userRepository.deleteAll();
    }
}
