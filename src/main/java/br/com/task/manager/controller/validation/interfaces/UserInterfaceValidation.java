package br.com.task.manager.controller.validation.interfaces;

import br.com.task.manager.controller.validation.ResultValidationEnum;
import br.com.task.manager.model.Usuario;

import java.util.List;

public interface UserInterfaceValidation {
    ResultValidationEnum validateUserLogin(String email, String senha);
    ResultValidationEnum usuarioIsNotNull(Usuario user);
    ResultValidationEnum validateUser(Usuario user);
    ResultValidationEnum emailAlreadyExists(List<Usuario> usuarios, String email);

}
