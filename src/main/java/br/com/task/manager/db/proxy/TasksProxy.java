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
    public Integer insertTask(Task task) {
        Integer taskId = taskDAO.insertTask(task);
        task.setId(taskId);
        taskCacheByUser.remove(task.getIdUsuario());
        return taskId;
    }

    @Override
    public void updateTask(Task task) {
        taskDAO.updateTask(task);
        taskCache.put(task.getId(), task);
        taskCacheByUser.remove(task.getIdUsuario());
    }

    @Override
    public void deleteTaskById(int id) {
        Task task = taskCache.get(id);
        if (task != null) {
            taskCacheByUser.remove(task.getIdUsuario());
        }
        taskDAO.deleteTaskById(id);
        taskCache.remove(id);
    }


    @Override
    public void deleteTasksByUsuarioId(int userId) {
        taskDAO.deleteTasksByUsuarioId(userId);

        taskCacheByUser.remove(userId);

        taskCache.entrySet().removeIf(entry -> entry.getValue().getIdUsuario() == userId);
    }

    @Override
    public void clearCacheByUserId(int userId) {
        taskCacheByUser.remove(userId);
        taskCache.entrySet().removeIf(entry -> entry.getValue().getIdUsuario() == userId);
    }

    @Override
    public List<Task> getTasksByUserId(int userId) {
        if (taskCacheByUser.containsKey(userId)) {
            return taskCacheByUser.get(userId);
        }

        List<Task> tasks = taskDAO.getTasksByUserId(userId);

        taskCacheByUser.put(userId, tasks);

        for (Task task : tasks) {
            taskCache.put(task.getId(), task);
        }

        return tasks;
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
        return task;
    }
}
