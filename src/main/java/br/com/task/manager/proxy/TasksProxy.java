package br.com.task.manager.proxy;

import br.com.task.manager.db.TaskDAO;
import br.com.task.manager.model.Task;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TasksProxy {
    private TaskDAO taskDAO;
    private Map<Integer, Task> taskCache = new HashMap<>();
    private Map<Integer, List<Task>> taskCacheByUser = new HashMap<>();

    public TasksProxy(Connection connection) {
        taskDAO = new TaskDAO(connection);
    }

    public void insert(Task task) {
        taskDAO.insert(task);
        taskCache.put(task.getId(), task);
        taskCacheByUser.put(task.getIdUsuario(), getTaksByUserId(task.getIdUsuario()));
    }

    public void update(Task task) {
        taskDAO.updateTask(task);
        taskCache.put(task.getId(), task);
        taskCacheByUser.put(task.getIdUsuario(), getTaksByUserId(task.getIdUsuario()));
    }

    public void delete(int id) {
        int userId = taskCache.get(id).getIdUsuario();
        taskCacheByUser.remove(userId);
        taskDAO.deleteTaskById(id);
        taskCache.remove(id);
    }

    public List<Task> getTaksByUserId(int id) {
        if (taskCacheByUser.containsKey(id)) {
            return taskCacheByUser.get(id);
        }

        List<Task> tasks = taskDAO.getTasksByUsuario(id);
        if(tasks != null) {
            taskCacheByUser.put(id, tasks);
        }
        return taskCacheByUser.get(id);
    }

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
