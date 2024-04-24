package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SignUpRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void getEmail() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        assertThat(signupRequest.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void getFirstName() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setFirstName("John");
        assertThat(signupRequest.getFirstName()).isEqualTo("John");
    }

    @Test
    void getLastName() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setLastName("Doe");
        assertThat(signupRequest.getLastName()).isEqualTo("Doe");
    }

    @Test
    void getPassword() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setPassword("password");
        assertThat(signupRequest.getPassword()).isEqualTo("password");
    }

    @Test
    void testEmailNotBlank() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("email");
    }

    @Test
    void testFirstNameNotBlank() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("firstName");
    }

    @Test
    void testLastNameNotBlank() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setPassword("password");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("lastName");
    }

    @Test
    void testPasswordNotBlank() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("password");
    }

    @Test
    void testEquals() {
        SignupRequest signupRequest1 = new SignupRequest();
        SignupRequest signupRequest2 = new SignupRequest();
        assertThat(signupRequest1).isEqualTo(signupRequest2);
    }

    @Test
    void testHashCode() {
        SignupRequest signupRequest1 = new SignupRequest();
        SignupRequest signupRequest2 = new SignupRequest();
        assertThat(signupRequest1.hashCode()).isEqualTo(signupRequest2.hashCode());
    }

    @Test
    void testToString() {
        SignupRequest signupRequest = new SignupRequest();
        assertThat(signupRequest.toString()).isNotNull();
    }

    @Test
    void testCanEqual() {
        SignupRequest signupRequest1 = new SignupRequest();
        SignupRequest signupRequest2 = new SignupRequest();
        assertThat(signupRequest1.canEqual(signupRequest2)).isTrue();
    }
}
