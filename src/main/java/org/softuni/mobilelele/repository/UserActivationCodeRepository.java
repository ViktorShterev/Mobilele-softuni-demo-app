package org.softuni.mobilelele.repository;

import org.softuni.mobilelele.model.entity.UserActivationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivationCodeRepository extends JpaRepository<UserActivationCode, Long> {

}
