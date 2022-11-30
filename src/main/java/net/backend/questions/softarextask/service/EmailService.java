package net.backend.questions.softarextask.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EmailService {
    void send(String emailTo, String subject, String message);
}
