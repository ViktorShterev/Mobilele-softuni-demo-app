package org.softuni.mobilelele.repository;

import org.softuni.mobilelele.model.dto.BrandDTO;
import org.softuni.mobilelele.model.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

}
