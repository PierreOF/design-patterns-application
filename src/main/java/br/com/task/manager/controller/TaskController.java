package br.com.task.manager.controller;


import br.com.task.manager.db.proxy.TaskProxyDAOInterface;
import br.com.task.manager.model.Task;
import br.com.task.manager.observer.TaskNotifier;
import br.com.task.manager.strategy.TaskSortingStrategy;

import java.util.List;

public class TaskController {

    private final TaskProxyDAOInterface database;
    private final TaskNotifier taskNotifier;

    public TaskController(TaskProxyDAOInterface database, TaskNotifier taskNotifier) {
        this.database = database;
        this.taskNotifier = taskNotifier;
    }

    public void addTask(Task task) {
        database.insertTask(task);
        taskNotifier.notifyObservers("Nova task criada: " + task.getTitulo());
    }

    public void updateTask(Task task) {
        database.updateTask(task);
        taskNotifier.notifyObservers("Task atualizada: " + task.getTitulo());
    }

    public void deleteTask(int taskId) {
        database.deleteTaskById(taskId);
        taskNotifier.notifyObservers("Task com ID: " + taskId + " deletado");
    }

    public List<Task> getAllTasks(int userId, TaskSortingStrategy sortingStrategy) {
        List<Task> tasks = database.getTasksByUserId(userId);
        return sortingStrategy.sort(tasks);
    }

    public void clearCacheByUserId(int userId) {
        database.clearCacheByUserId(userId);
    }
}
