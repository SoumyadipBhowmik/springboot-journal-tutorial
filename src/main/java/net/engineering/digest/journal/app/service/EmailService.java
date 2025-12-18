package net.engineering.digest.journal.app.service;

public interface EmailService {

    void sendEmail(String to, String subject, String body);
}
