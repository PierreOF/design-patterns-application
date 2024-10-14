package br.com.task.manager.controller.validation;

import br.com.task.manager.controller.validation.interfaces.UserInterfaceValidation;
import br.com.task.manager.model.Usuario;

import java.util.List;

public class UserValidation implements UserInterfaceValidation {

    @Override
    public ResultValidationEnum validateUserLogin(String email, String senha) {
        if (email == null || email.isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        if (senha == null || senha.isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        return ResultValidationEnum.APPROVED;
    }

    @Override
    public ResultValidationEnum usuarioIsNotNull(Usuario user) {
        if (user == null) {
            return ResultValidationEnum.REJECTED;
        }
        return ResultValidationEnum.APPROVED;
    }

    @Override
    public ResultValidationEnum validateUser(Usuario user) {
        if (usuarioIsNotNull(user) == ResultValidationEnum.REJECTED) {
            return ResultValidationEnum.REJECTED;
        }
        if (user.getNome() == null || user.getNome().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        if (user.getSenha() == null || user.getSenha().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        return ResultValidationEnum.APPROVED;
    }

    @Override
    public ResultValidationEnum emailAlreadyExists(List<Usuario> usuarios, String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return ResultValidationEnum.REJECTED;
            }
        }
        return ResultValidationEnum.APPROVED;
    }
}
