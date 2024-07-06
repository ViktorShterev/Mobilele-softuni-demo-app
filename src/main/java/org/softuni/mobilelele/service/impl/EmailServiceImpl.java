package org.softuni.mobilelele.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.softuni.mobilelele.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    private final String mobileleEmail;

    public EmailServiceImpl(TemplateEngine templateEngine,
                            JavaMailSender mailSender,
                            @Value("${mail.mobilele}") String mobileleEmail) {

        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
        this.mobileleEmail = mobileleEmail;
    }

    @Override
    public void sendRegistrationEmail(String userEmail, String username, String activationCode) {

        MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setFrom(mobileleEmail);
            mimeMessageHelper.setReplyTo(mobileleEmail);
            mimeMessageHelper.setSubject("Welcome to Mobilele!");
            mimeMessageHelper.setText(generateRegistrationEmailBody(username, activationCode), true);

            this.mailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateRegistrationEmailBody(String username, String activationCode) {

        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("activationCode", activationCode);

        return this.templateEngine.process("email/registration-mail", context);
    }
}
