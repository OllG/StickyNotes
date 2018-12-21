package pl.olpinski.stickynotes.service;

public interface MailService {

    void sendConfirmationMail (String to, String subject, String text);
}
