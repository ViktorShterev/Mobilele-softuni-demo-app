package org.softuni.mobilelele.repository;

import org.softuni.mobilelele.model.entity.UserRole;
import org.softuni.mobilelele.model.enums.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findAllByNameIn(List<RolesEnum> roles);
}
