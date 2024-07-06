package org.softuni.mobilelele.web;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class UserRegisterControllerTestIT {

    @Autowired
    private MockMvc mvc;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.host}")
    private String host;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    private GreenMail greenMail;

    @BeforeEach
    void setUp() {
        this.greenMail = new GreenMail(new ServerSetup(this.port, this.host, "smtp"));

        this.greenMail.start();
        this.greenMail.setUser(this.username, this.password);
    }

    @AfterEach
    void tearDown() {
        this.greenMail.stop();
    }

    @Test
    void testRegistration() throws Exception {

        mvc.perform(
                MockMvcRequestBuilders.post("/users/register")
                        .param("firstName", "Petar")
                        .param("lastName", "Petrov")
                        .param("email", "test@example.com")
                        .param("password", "topsecret")
                        .param("confirmPassword", "topsecret")
                        .with(csrf())

        ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        this.greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = this.greenMail.getReceivedMessages();

        assertEquals(1, receivedMessages.length);

        MimeMessage registrationMessage = receivedMessages[0];

        assertTrue(registrationMessage.getContent().toString().contains("Petar"));
        assertEquals(1, registrationMessage.getAllRecipients().length);
        assertEquals("test@example.com", registrationMessage.getAllRecipients()[2].toString());
    }

}