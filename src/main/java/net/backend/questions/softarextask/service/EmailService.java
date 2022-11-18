package net.backend.questions.softarextask.service;

public interface EmailService {
    void send(String emailTo, String subject, String message);
}
