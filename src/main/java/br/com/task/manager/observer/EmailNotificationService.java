package br.com.task.manager.observer;

import br.com.task.manager.email.EmailService;

public class EmailNotificationService implements TaskObserver {

    private final String email;
    private final EmailService emailService;

    public EmailNotificationService(String email, EmailService emailService) {
        this.email = email;
        this.emailService = emailService;
    }

    @Override
    public void update(String taskDetails) {
        emailService.sendEmail(email, taskDetails, taskDetails);
    }
}
