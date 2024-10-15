package br.com.task.manager.observer;

import br.com.task.manager.observer.email.EmailService;

public class EmailObserver implements TaskObserver {

    private final EmailService emailService;
    private final String emailDestinatario;

    public EmailObserver(EmailService emailService, String emailDestinatario) {
        this.emailService = emailService;
        this.emailDestinatario = emailDestinatario;
    }

    @Override
    public void update(String message) {
        emailService.sendEmail(emailDestinatario, "Notificação TaskManager", message);
    }
}
