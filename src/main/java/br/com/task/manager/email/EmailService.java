package br.com.task.manager.email;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
