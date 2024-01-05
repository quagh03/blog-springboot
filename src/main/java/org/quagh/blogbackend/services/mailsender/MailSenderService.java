package org.quagh.blogbackend.services.mailsender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.quagh.blogbackend.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class MailSenderService implements IMailSenderService{
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromAddress;

    @Override
    public void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = this.fromAddress;
        String senderName = "Blog Không Tên";
        String subject = "Xác thực đăng ký";
        String content =
                "Chào [[name]],<br>"
                + "Cám ơn bạn đăng đăng ký tài khoản tại <i>Blog Không Tên</i>. Vui lòng vào đường link dưới để xác thực tài khoản:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">XÁC NHẬN</h3>"
                + "Xin cám ơn, <br>"
                + "<i>Blog Không Tên.</i>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getLastName());
        String verifyURL = siteURL + "/verify?code=" + user.getVerifitaionCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);
        mailSender.send(message);
    }

    //forgot password verification

}
