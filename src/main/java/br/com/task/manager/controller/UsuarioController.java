package br.com.task.manager.controller;

import br.com.task.manager.db.proxy.TaskProxyDAOInterface;
import br.com.task.manager.db.proxy.UsuarioProxy;
import br.com.task.manager.db.proxy.UsuarioProxyDAOInterface;
import br.com.task.manager.email.EmailService;
import br.com.task.manager.model.Usuario;
import br.com.task.manager.observer.TaskNotifier;
import br.com.task.manager.observer.UsuarioTaskObserver;

import java.sql.Connection;
import java.util.List;

public class UsuarioController {

    private final UsuarioProxyDAOInterface usuarioProxy;
    private final TaskNotifier taskNotifier;
    private final EmailService emailService;

    public UsuarioController(Connection connection, TaskNotifier taskNotifier, EmailService emailService) {
        this.usuarioProxy = new UsuarioProxy(connection);
        this.taskNotifier = taskNotifier;
        this.emailService = emailService;
    }

    public Usuario login(String email, String senha) {
        Usuario usuario = usuarioProxy.userLogin(email, senha);
        if (usuario != null) {
            taskNotifier.addObserver(new UsuarioTaskObserver(usuario, emailService));
        } else {
            return null;
        }
        return usuario;
    }

    public boolean register(String nome, String email, String senha) {
        if (EmailAlreadyExists(email)) {
            return false;
        }
        Usuario novoUsuario = new Usuario(0, nome, email, senha);
        usuarioProxy.insertUser(novoUsuario);
        emailService.sendEmail(email, "Conta criada com sucesso", "Parabéns sua conta foi criado com sucesso!");
        return true;
    }



    private boolean EmailAlreadyExists(String email) {
        List<Usuario> usuarios = usuarioProxy.getAllUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
