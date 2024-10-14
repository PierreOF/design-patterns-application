package br.com.task.manager.db.proxy;

import br.com.task.manager.db.dao.TaskDAO;
import br.com.task.manager.model.Task;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TasksProxy implements TaskProxyDAOInterface {
    private final TaskDAO taskDAO;
    private final Map<Integer, Task> taskCache = new HashMap<>();
    private final Map<Integer, List<Task>> taskCacheByUser = new HashMap<>();

    public TasksProxy(Connection connection) {
        taskDAO = new TaskDAO(connection);
    }

    @Override
    public void insertTask(Task task) {
        taskDAO.insertTask(task);
        // Invalida o cache do usuário para forçar a atualização na próxima leitura
        taskCacheByUser.remove(task.getIdUsuario());
        taskCache.put(task.getId(), task);
    }

    @Override
    public void updateTask(Task task) {
        taskDAO.updateTask(task);
        // Invalida o cache do usuário
        taskCacheByUser.remove(task.getIdUsuario());
        taskCache.put(task.getId(), task);
    }

    @Override
    public void deleteTaskById(int id) {
        Task task = taskCache.get(id);
        if (task != null) {
            taskDAO.deleteTaskById(id);
            taskCache.remove(id);
            taskCacheByUser.remove(task.getIdUsuario());
        }
    }

    @Override
    public void deleteTasksByUsuarioId(int id) {
        int userId = taskCache.get(id).getIdUsuario();
        taskCacheByUser.remove(userId);
        taskDAO.deleteTaskById(id);
        taskCache.remove(id);
    }

    @Override
    public List<Task> getTasksByUserId(int userId) {
        if (!taskCacheByUser.containsKey(userId)) {
            List<Task> tasks = taskDAO.getTasksByUserId(userId);
            taskCacheByUser.put(userId, tasks);
        }
        return taskCacheByUser.get(userId);
    }

    @Override
    public Task getTaskById(int id) {
        if (taskCache.containsKey(id)) {
            return taskCache.get(id);
        }

        Task task = taskDAO.getTaskById(id);
        if (task != null) {
            taskCache.put(id, task);
        }
        return taskCache.get(id);
    }
}
