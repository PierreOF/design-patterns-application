package br.com.task.manager.proxy;

import br.com.task.manager.db.UsuarioDAO;
import br.com.task.manager.model.Usuario;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class UsuarioProxy {
    private UsuarioDAO usuarioDAO;
    private Map<Integer, Usuario> usuarioCache = new HashMap<>();

    public UsuarioProxy(Connection connection) {
        usuarioDAO = new UsuarioDAO(connection);
    }

    public void insert(Usuario usuario) {
        usuarioDAO.insert(usuario);
        usuarioCache.put(usuario.getId(), usuario);
    }

    public void update(Usuario usuario) {
        usuarioDAO.update(usuario);
        usuarioCache.put(usuario.getId(), usuario);
    }

    public void delete(int id) {
        usuarioDAO.delete(id);
        usuarioCache.remove(id);
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioDAO.getAllUsuarios();
    }

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
