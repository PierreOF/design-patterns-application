package br.com.task.manager.controller;

import br.com.task.manager.controller.validation.ResultValidationEnum;
import br.com.task.manager.controller.validation.UserValidation;
import br.com.task.manager.controller.validation.interfaces.UserInterfaceValidation;
import br.com.task.manager.db.proxy.UsuarioProxy;
import br.com.task.manager.db.proxy.UsuarioProxyDAOInterface;
import br.com.task.manager.email.EmailService;
import br.com.task.manager.model.Usuario;
import br.com.task.manager.observer.TaskNotifier;
import br.com.task.manager.observer.UsuarioTaskObserver;

import java.sql.Connection;
import java.util.List;

public class UsuarioController {
    private final UserInterfaceValidation userInterfaceValidation;
    private final UsuarioProxyDAOInterface usuarioProxy;
    private final TaskNotifier taskNotifier;
    private final EmailService emailService;

    public UsuarioController(Connection connection, TaskNotifier taskNotifier, EmailService emailService) {
        this.usuarioProxy = new UsuarioProxy(connection);
        this.taskNotifier = taskNotifier;
        this.emailService = emailService;
        this.userInterfaceValidation = new UserValidation();
    }

    public Usuario login(String email, String senha) {
        ResultValidationEnum resultValidation = userInterfaceValidation.validateUserLogin(email, senha);
        if (resultValidation == ResultValidationEnum.REJECTED) {
            return null;
        }

        Usuario usuario = usuarioProxy.userLogin(email, senha);

        resultValidation = userInterfaceValidation.usuarioIsNotNull(usuario);
        if (resultValidation == ResultValidationEnum.REJECTED) {

            return null;
        }

        taskNotifier.addObserver(new UsuarioTaskObserver(usuario, emailService));
        return usuario;
    }

    public boolean register(String nome, String email, String senha) {
        ResultValidationEnum resultValidationEnum = emailAlreadyExists(email);
        if (resultValidationEnum == ResultValidationEnum.REJECTED) {
            return false;
        }
        Usuario novoUsuario = new Usuario(nome, email, senha);
        ResultValidationEnum resultValidation = userInterfaceValidation.validateUser(novoUsuario);
        if (resultValidation == ResultValidationEnum.REJECTED) {
            return false;
        }
        usuarioProxy.insertUser(novoUsuario);
        System.out.println("Enviando email...");
        emailService.sendEmail(email, "Conta criada com sucesso", "Parabéns sua conta foi criado com sucesso!");
        System.out.println("Email enviado com sucesso!");
        return true;
    }

    private ResultValidationEnum emailAlreadyExists(String email) {
        List<Usuario> usuarios = usuarioProxy.getAllUsuarios();
        return userInterfaceValidation.emailAlreadyExists(usuarios, email);
    }
}
