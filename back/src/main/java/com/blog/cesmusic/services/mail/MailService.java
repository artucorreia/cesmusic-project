package com.blog.cesmusic.services.mail;

import com.blog.cesmusic.data.DTO.v1.output.PendingUserDTO;
import com.blog.cesmusic.data.DTO.v1.output.UserDTO;
import com.blog.cesmusic.exceptions.mail.MailSendingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class MailService {
    private final Logger LOGGER = Logger.getLogger(MailService.class.getName());

    @Value("${website.url}")
    private String WEBSITE_URL;

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailValidationCode(PendingUserDTO pendingUser) {
        LOGGER.info("Sending validation code to user");

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("<" + pendingUser.getEmail() + ">");
            helper.setFrom("no-reply@cesmusic.blog");
            helper.setSubject("Validação de Email");

            String template = getMailTemplate("templates/code-mail-template.html");
            template = template.replace("${name}", pendingUser.getName());
            template = template.replace("${url}",WEBSITE_URL + "register/authenticate-code?code=" + pendingUser.getCode());
            template = template.replace("${currentYear}", String.valueOf(LocalDateTime.now().getYear()));

            helper.setText(
                    "Código de Validação:" + pendingUser.getCode(),
                    template
            );

            javaMailSender.send(message);
        }
        catch (Exception e) {
            throw new MailSendingException("Error when trying to send mail");
        }
    }

    public void sendNotificationOfNewRegistrationToAdmin(UserDTO newUser, String adminEmail) {
        LOGGER.info("Sending mail to admin");
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("<" + adminEmail + ">");
            helper.setFrom("no-reply@cesmusic.blog");
            helper.setSubject("New User");

            String template = getMailTemplate("templates/new-user-mail-template.html");
            template = template.replace("${name}", newUser.getName());
            template = template.replace("${login}", newUser.getEmail());
            template = template.replace("${url}", WEBSITE_URL + "admin/authenticate-user?id=" + newUser.getId());
            template = template.replace("${currentYear}", String.valueOf(LocalDateTime.now().getYear()));

            helper.setText(
                    "New user:\n" +
                            "\nName: " + newUser.getName() +
                            "\nLogin: " + newUser.getEmail(),
                    template
            );

            javaMailSender.send(message);
        }
        catch (Exception e) {
            throw new MailSendingException("Error when trying to send mail");
        }
    }

    public void sendAcceptedUserNotification(UserDTO user) {
        LOGGER.info("Sending mail to user");

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("<" + user.getEmail() + ">");
            helper.setFrom("no-reply@cesmusic.blog");
            helper.setSubject("Conta Ativada");

            String template = getMailTemplate("templates/user-accept-mail-template.html");
            template = template.replace("${name}", user.getName());
            template = template.replace("${currentYear}", String.valueOf(LocalDateTime.now().getYear()));

            helper.setText(
                    "Sua conta foi ativada com sucesso",
                    template
            );

            javaMailSender.send(message);
        }
        catch (Exception e) {
            throw new MailSendingException("Error when trying to send mail");
        }
    }

    private String getMailTemplate(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}