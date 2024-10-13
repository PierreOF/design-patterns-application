package br.com.task.manager.controller;


import br.com.task.manager.db.proxy.TaskProxyDAOInterface;
import br.com.task.manager.model.Task;
import br.com.task.manager.strategy.TaskSortingStrategy;

import java.util.List;

public class TaskController {
    private TaskProxyDAOInterface database;

    public TaskController(TaskProxyDAOInterface database) {
        this.database = database;
    }

    public void addTask(Task task) {
        database.insertTask(task);
    }

    public void updateTask(Task task) {
        database.updateTask(task);
    }

    public void deleteTask(int taskId) {
        database.deleteTaskById(taskId);
    }

    public List<Task> getAllTasks(int userId, TaskSortingStrategy sortingStrategy) {
        List<Task> tasks = database.getTasksByUserId(userId);
        return sortingStrategy.sort(tasks);
    }
}
