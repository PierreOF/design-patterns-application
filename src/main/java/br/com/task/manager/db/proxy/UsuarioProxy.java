package br.com.task.manager.db.proxy;

import br.com.task.manager.db.dao.UsuarioDAO;
import br.com.task.manager.model.Usuario;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class UsuarioProxy implements UsuarioProxyDAOInterface {
    private UsuarioDAO usuarioDAO;
    private Map<Integer, Usuario> usuarioCache = new HashMap<>();
    private Map<Integer, Usuario> usuarioLoginCache = new HashMap<>();

    public UsuarioProxy(Connection connection) {
        usuarioDAO = new UsuarioDAO(connection);
    }

    @Override
    public Usuario userLogin(String email, String senha) {
        for (Usuario usuario : usuarioCache.values()) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                return usuario;
            }
        }

        Usuario usuario = usuarioDAO.userLogin(email, senha);
        if (usuario == null) {
            return null;
        }
        usuarioLoginCache.put(usuario.getId(), usuario);
        return usuarioLoginCache.get(usuario.getId());
    }

    @Override
    public void insertUser(Usuario usuario) {
        usuarioDAO.insertUser(usuario);
        usuarioCache.put(usuario.getId(), usuario);
    }

    @Override
    public void updateUser(Usuario usuario) {
        usuarioDAO.updateUser(usuario);
        usuarioCache.put(usuario.getId(), usuario);
    }

    @Override
    public void deleteUser(int id) {
        usuarioDAO.deleteUser(id);
        usuarioCache.remove(id);
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioDAO.getAllUsuarios();
    }

    @Override
    public Usuario getUsuarioById(int id) {
        if (usuarioCache.containsKey(id)) {
            return usuarioCache.get(id);
        }

        Usuario usuario = usuarioDAO.getUsuarioById(id);
        if(usuario != null) {
            usuarioCache.put(id, usuario);
        }
        return usuarioCache.get(id);
    }
}
