package org.softuni.mobilelele.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.softuni.mobilelele.model.validation.FieldMatch;
import org.softuni.mobilelele.model.validation.UniqueUserEmail;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "The passwords must match"
)
public record UserRegistrationDTO(

        @NotEmpty
        String firstName,

        @NotEmpty
        String lastName,

        @NotNull
        @Email
        @UniqueUserEmail
        String email,

        String password,

        String confirmPassword) {

        public String fullName() {
                return firstName + " " + lastName;
        }
}
