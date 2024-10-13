package br.com.task.manager.controller;

import br.com.task.manager.db.proxy.UsuarioProxy;
import br.com.task.manager.model.Usuario;

import java.sql.Connection;
import java.util.List;

public class UsuarioController {

    private UsuarioProxy usuarioProxy;

    public UsuarioController(Connection connection) {
        this.usuarioProxy = new UsuarioProxy(connection);
    }

    public Usuario login(String email, String senha) {
        return usuarioProxy.userLogin(email, senha);
    }

    public boolean cadastrar(String nome, String email, String senha) {
        if (isEmailJaCadastrado(email)) {
            return false;
        }
        Usuario novoUsuario = new Usuario(0, nome, email, senha);
        usuarioProxy.insertUser(novoUsuario);
        return true;
    }

    private boolean isEmailJaCadastrado(String email) {
        List<Usuario> usuarios = usuarioProxy.getAllUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void logout() {
    }
}
