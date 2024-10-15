package br.com.task.manager.db.proxy;

import br.com.task.manager.model.Usuario;

import java.util.List;

public interface UsuarioProxyDAOInterface {
    Usuario userLogin(String email, String senha);
    Usuario insertUser(Usuario usuario);
    void updateUser(Usuario usuario);
    void deleteUser(int id);
    List<Usuario> getAllUsuarios();
    Usuario getUsuarioById(int id);
}
