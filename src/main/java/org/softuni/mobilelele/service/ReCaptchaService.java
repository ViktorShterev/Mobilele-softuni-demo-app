package org.softuni.mobilelele.service;

import org.softuni.mobilelele.model.dto.ReCaptchaResponseDTO;

import java.util.Optional;

public interface ReCaptchaService {

    Optional<ReCaptchaResponseDTO> verify(String token);
}
