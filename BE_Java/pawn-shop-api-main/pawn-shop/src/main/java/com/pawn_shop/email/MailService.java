package com.pawn_shop.email;

import com.pawn_shop.model.contract.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {
    private static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";

    @Value("${config.mail.host}")
    private String host;
    @Value("${config.mail.port}")
    private String port;
    @Value("${config.mail.username}")
    private String email;
    @Value("ufgknjtbrihadbns")
    private String password;

    @Autowired
    private ThymeleafService thymeleafService;

    public void sendMail(Contract contract, String email) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("pawnshopc04@gmail.com", "ufgknjtbrihadbns");
                    }
                });
        Message message = new MimeMessage(session);
        try {
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(email)});

            message.setFrom(new InternetAddress(email));
            message.setSubject("Thông tin hợp đồng");
            message.setContent(thymeleafService.getContent(contract), CONTENT_TYPE_TEXT_HTML);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
