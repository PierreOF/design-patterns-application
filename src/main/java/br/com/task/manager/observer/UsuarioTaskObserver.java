package br.com.task.manager.observer;

import br.com.task.manager.email.EmailService;
import br.com.task.manager.model.Usuario;

public class UsuarioTaskObserver implements TaskObserver {

    private final Usuario usuario;
    private final EmailService emailService;

    public UsuarioTaskObserver(Usuario usuario, EmailService emailService) {
        this.usuario = usuario;
        this.emailService = emailService;
    }

    @Override
    public void update(String taskDetails) {
        System.out.println("Notificando usuário " + usuario.getNome() + " sobre task " + taskDetails);
        emailService.sendEmail(usuario.getEmail(), "Atualização de Tarefa", taskDetails);
    }
}
