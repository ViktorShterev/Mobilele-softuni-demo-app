package org.softuni.mobilelele.service.impl;

import org.softuni.mobilelele.model.dto.BrandDTO;
import org.softuni.mobilelele.model.dto.ModelDTO;
import org.softuni.mobilelele.model.entity.Brand;
import org.softuni.mobilelele.model.entity.Model;
import org.softuni.mobilelele.repository.BrandRepository;
import org.softuni.mobilelele.repository.ModelRepository;
import org.softuni.mobilelele.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BrandServiceImpl implements BrandService {

    private final ModelRepository modelRepository;

    public BrandServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public List<BrandDTO> getAllBrands() {

        Map<String, BrandDTO> brands = new TreeMap<>();

        for (Model model : this.modelRepository.findAll()) {
            String brandName = model.getBrand().getName();

            if (!brands.containsKey(brandName)) {
                brands.put(brandName,
                        new BrandDTO(brandName, new ArrayList<>()));
            }

            brands.get(brandName)
                    .models()
                    .add(new ModelDTO(model.getId(), model.getName()));
        }

        return brands.values().stream().toList();
    }
}
