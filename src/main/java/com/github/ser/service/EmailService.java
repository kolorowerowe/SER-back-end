package com.github.ser.service;

import com.github.ser.exception.runtime.MailNotSentException;
import com.github.ser.model.requests.MailMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@Log4j2
public class EmailService {

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendMessage(MailMessage mailMessage) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(mailMessage.getTo());
            helper.setSubject(mailMessage.getSubject());
            helper.setText(mailMessage.getBody(), mailMessage.getIsHtml());

            emailSender.send(message);
            log.info("Sent message to " + mailMessage.getTo() + " with subject: " + mailMessage.getSubject());

        } catch (MessagingException e) {
            log.error("Couldn't sent message, ", e);
            throw new MailNotSentException("Mail not sent");
        }

    }
}