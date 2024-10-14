package br.com.task.manager.db.proxy;

import br.com.task.manager.model.Task;

import java.util.List;

public interface TaskProxyDAOInterface {
    Integer insertTask(Task task);
    void updateTask(Task task);
    void deleteTaskById(int id);
    List<Task> getTasksByUserId(int userId);
    Task getTaskById(int taskId);
    void deleteTasksByUsuarioId(int userId);
    void clearCacheByUserId(int userId);
}
