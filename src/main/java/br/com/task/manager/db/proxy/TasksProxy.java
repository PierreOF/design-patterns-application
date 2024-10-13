package br.com.task.manager.db.proxy;

import br.com.task.manager.db.dao.TaskDAO;
import br.com.task.manager.model.Task;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TasksProxy implements TaskProxyDAOInterface {
    private TaskDAO taskDAO;
    private Map<Integer, Task> taskCache = new HashMap<>();
    private Map<Integer, List<Task>> taskCacheByUser = new HashMap<>();

    public TasksProxy(Connection connection) {
        taskDAO = new TaskDAO(connection);
    }

    @Override
    public void insertTask(Task task) {
        taskDAO.insertTask(task);
        taskCache.put(task.getId(), task);
        taskCacheByUser.put(task.getIdUsuario(), getTasksByUserId(task.getIdUsuario()));
    }

    @Override
    public void updateTask(Task task) {
        taskDAO.updateTask(task);
        taskCache.put(task.getId(), task);
        taskCacheByUser.put(task.getIdUsuario(), getTasksByUserId(task.getIdUsuario()));
    }

    @Override
    public void deleteTaskById(int id) {
        taskDAO.deleteTaskById(id);
        taskCache.remove(id);
    }

    @Override
    public void deleteTasksByUsuarioId(int id) {
        int userId = taskCache.get(id).getIdUsuario();
        taskCacheByUser.remove(userId);
        taskDAO.deleteTaskById(id);
        taskCache.remove(id);
    }

    @Override
    public List<Task> getTasksByUserId(int id) {
        if (taskCacheByUser.containsKey(id)) {
            return taskCacheByUser.get(id);
        }

        List<Task> tasks = taskDAO.getTasksByUserId(id);
        if(tasks != null) {
            taskCacheByUser.put(id, tasks);
        }
        return taskCacheByUser.get(id);
    }

    @Override
    public Task getTaskById(int id) {
        if (taskCache.containsKey(id)) {
            return taskCache.get(id);
        }

        Task task = taskDAO.getTaskById(id);
        if(task != null) {
            taskCache.put(id, task);
        }
        return taskCache.get(id);
    }
}
