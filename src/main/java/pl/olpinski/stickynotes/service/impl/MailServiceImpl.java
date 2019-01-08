package pl.olpinski.stickynotes.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.olpinski.stickynotes.service.MailService;

@Service
public class MailServiceImpl implements MailService {

    private JavaMailSender javaMailSender;


    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendConfirmationMail(String to, String subject, String text) {
        SimpleMailMessage confirmationMail = new SimpleMailMessage();
        confirmationMail.setTo(to);
        confirmationMail.setSubject(subject);
        confirmationMail.setText(text);
        javaMailSender.send(confirmationMail);
    }
}
