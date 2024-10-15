package br.com.task.manager.controller;

import br.com.task.manager.controller.validation.ResultValidationEnum;
import br.com.task.manager.controller.validation.UserValidation;
import br.com.task.manager.controller.validation.interfaces.UserInterfaceValidation;
import br.com.task.manager.db.proxy.TaskProxyDAOInterface;
import br.com.task.manager.db.proxy.TasksProxy;
import br.com.task.manager.db.proxy.UsuarioProxy;
import br.com.task.manager.db.proxy.UsuarioProxyDAOInterface;
import br.com.task.manager.observer.EmailObserver;
import br.com.task.manager.observer.email.EmailService;
import br.com.task.manager.model.Usuario;
import br.com.task.manager.observer.TaskNotifier;
import br.com.task.manager.observer.UsuarioTaskObserver;

import java.sql.Connection;
import java.util.List;

public class UsuarioController {
    private final UserInterfaceValidation userInterfaceValidation;
    private final TaskProxyDAOInterface taskProxyDAO;
    private final UsuarioProxyDAOInterface usuarioProxy;
    private final TaskNotifier taskNotifier;
    private final EmailService emailService;

    public UsuarioController(Connection connection, TaskNotifier taskNotifier, EmailService emailService) {
        this.usuarioProxy = new UsuarioProxy(connection);
        this.taskProxyDAO = new TasksProxy(connection);
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

    public ResultValidationEnum register(String nome, String email, String senha) {
        ResultValidationEnum resultValidation = emailAlreadyExists(email);
        if (resultValidation == ResultValidationEnum.REJECTED) {
            return resultValidation;
        }

        Usuario novoUsuario = new Usuario(nome, email, senha);
        resultValidation = userInterfaceValidation.validateUser(novoUsuario);
        if (resultValidation == ResultValidationEnum.REJECTED) {
            return resultValidation;
        }

        usuarioProxy.insertUser(novoUsuario);
        taskNotifier.addObserver(new EmailObserver(emailService, novoUsuario.getEmail()));
        taskNotifier.notifyObservers("Sucesso ao registrar");
        return ResultValidationEnum.APPROVED;
    }

    private ResultValidationEnum emailAlreadyExists(String email) {
        List<Usuario> usuarios = usuarioProxy.getAllUsuarios();
        return userInterfaceValidation.emailAlreadyExists(usuarios, email);
    }

    public void clearCacheLogout(int userId) {
        taskProxyDAO.clearCacheByUserId(userId);
        usuarioProxy.clearCache();
    }
}
