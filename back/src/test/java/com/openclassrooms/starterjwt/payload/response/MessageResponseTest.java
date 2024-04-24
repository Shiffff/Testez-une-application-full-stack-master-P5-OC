package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageResponseTest {

    @Test
    void getMessage() {
        MessageResponse messageResponse = new MessageResponse("Test message");
        assertThat(messageResponse.getMessage()).isEqualTo("Test message");
    }

    @Test
    void setMessage() {
        MessageResponse messageResponse = new MessageResponse("Test message");
        messageResponse.setMessage("New message");
        assertThat(messageResponse.getMessage()).isEqualTo("New message");
    }
}
