package br.com.task.manager.proxy;

import br.com.task.manager.db.TaskDAO;
import br.com.task.manager.db.UsuarioDAO;
import br.com.task.manager.model.Task;
import br.com.task.manager.model.Usuario;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class UsuarioProxy {
    private UsuarioDAO usuarioDAO;
    private TaskDAO taskDAO;
    private Map<Integer, Usuario> usuarioCache = new HashMap<>();
    private Map<Integer, List<Task>> taskCache = new HashMap<>();

    public UsuarioProxy(Connection connection) {
        usuarioDAO = new UsuarioDAO(connection);
        taskDAO = new TaskDAO(connection);
    }

    public Usuario getUsuarioById(int id) {
        if (usuarioCache.containsKey(id)) {
            return usuarioCache.get(id);
        }
        List<Usuario> usuarios = usuarioDAO.getAllUsuarios();
        for (Usuario usuario : usuarios) {
            usuarioCache.put(usuario.getId(), usuario);
        }
        return usuarioCache.get(id);
    }

    public List<Task> getTasksByUsuarioId(int id) {
        if (taskCache.containsKey(id)) {
            return taskCache.get(id);
        }
        List<Task> tasks = taskDAO.getTasksByUsuario(id);
        taskCache.put(id, tasks);
        return tasks;
    }

    public void addUsuario(Usuario usuario) {
        usuarioDAO.insert(usuario);
        usuarioCache.put(usuario.getId(), usuario);
    }

    public void addTask(Task task) {
        taskDAO.insert(task);
        if (taskCache.containsKey(task.getIdUsuario())) {
            taskCache.get(task.getIdUsuario()).add(task);
        } else {
            List<Task> tasks = new ArrayList<>();
            tasks.add(task);
            taskCache.put(task.getIdUsuario(), tasks);
        }
    }
}
