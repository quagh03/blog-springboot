package org.quagh.blogbackend.services.mailsender;

import jakarta.mail.MessagingException;
import org.quagh.blogbackend.entities.User;

import java.io.UnsupportedEncodingException;

public interface IMailSenderService {
    void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException;
}
