package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginRequestTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void getEmail() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        assertThat(loginRequest.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void getPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("password");
        assertThat(loginRequest.getPassword()).isEqualTo("password");
    }

    @Test
    void testEmailNotBlank() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("password");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("email");
    }

    @Test
    void testPasswordNotBlank() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("password");
    }
}
