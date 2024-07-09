package org.softuni.mobilelele.testUtils;

import org.softuni.mobilelele.model.entity.UserRole;
import org.softuni.mobilelele.model.enums.RolesEnum;
import org.softuni.mobilelele.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRoleDBInit implements CommandLineRunner {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (this.userRoleRepository.count() == 0) {

            this.userRoleRepository.saveAll(
                    List.of(
                            new UserRole().setRole(RolesEnum.USER),
                            new UserRole().setRole(RolesEnum.ADMIN)
                    )
            );
        }
    }
}
