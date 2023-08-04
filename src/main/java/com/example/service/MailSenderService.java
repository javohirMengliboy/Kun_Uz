package com.example.service;

import com.example.entity.EmailHistoryEntity;
import com.example.util.JWTUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MailSenderService {
    private JavaMailSender javaMailSender;
    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private EmailHistoryService emailHistoryService;
    @Autowired
    public void setEmailHistoryService(EmailHistoryService emailHistoryService) {
        this.emailHistoryService = emailHistoryService;
    }

    @Value("${server.url}")
    private String serverUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;

//    void sendMimeEmail(String toAccount, String text) {
//        try {
//            MimeMessage msg = javaMailSender.createMimeMessage();
//            msg.setFrom(fromEmail);
//            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//            helper.setTo(toAccount);
//            helper.setSubject("Kun uz registration compilation");
//            helper.setText(text, true);
//            javaMailSender.send(msg);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void sendMimeEmail(String toAccount, String text){
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    MimeMessage msg=javaMailSender.createMimeMessage();
                    MimeMessageHelper helper=new MimeMessageHelper(msg,true);
                    helper.setTo(toAccount);
                    helper.setSubject("Kun uz registration compilation");
                    helper.setText("nimadur",text);
                    javaMailSender.send(msg);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        emailExecutor.shutdown();
    }

    public void sendEmailVerification(String toAccount,String name, Integer id) {
        String jwt = JWTUtil.encodeEmailJwt(id);
        String url = serverUrl + "/api/v1/auth/verification/email/" + jwt;
        EmailHistoryEntity emailHistoryEntity = new EmailHistoryEntity();
        emailHistoryEntity.setMessage(url);
        emailHistoryEntity.setEmail(toAccount);
        emailHistoryService.create(emailHistoryEntity);

        String builder = String.format("<h1 style=\"text-align: center\">Hello %s</h1>", name) +
                "<p>" +
                String.format("<a href=\"%s\"> Click link to complete registration </a>", url) +
                "</p>";

        sendMimeEmail(toAccount, builder);
    }
}
