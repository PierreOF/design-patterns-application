package br.com.task.manager.controller;


import br.com.task.manager.controller.validation.ResultValidationEnum;
import br.com.task.manager.controller.validation.TaskValidation;
import br.com.task.manager.controller.validation.interfaces.TaskInterfaceValidation;
import br.com.task.manager.db.proxy.TaskProxyDAOInterface;
import br.com.task.manager.db.proxy.UsuarioProxyDAOInterface;
import br.com.task.manager.model.Task;
import br.com.task.manager.observer.TaskNotifier;
import br.com.task.manager.strategy.SortByCreationDate;
import br.com.task.manager.strategy.TaskSortingStrategy;

import java.util.List;

public class TaskController {

    private final TaskInterfaceValidation taskValidation;
    private final TaskProxyDAOInterface database;
    private final TaskNotifier taskNotifier;
    private TaskSortingStrategy taskSortingStrategy;

    public TaskController(TaskProxyDAOInterface databaseTask, TaskNotifier taskNotifier) {
        this.database = databaseTask;
        this.taskNotifier = taskNotifier;
        this.taskValidation = new TaskValidation();
        this.taskSortingStrategy = new SortByCreationDate();
    }

    public void setSortingStrategy(TaskSortingStrategy taskSortingStrategy){
        this.taskSortingStrategy = taskSortingStrategy;
    }

    public ResultValidationEnum addTask(Task task) {
        ResultValidationEnum result = taskValidation.validateTask(task);
        if (result == ResultValidationEnum.REJECTED) {
            return result;
        }
        database.insertTask(task);
        taskNotifier.notifyObservers(task.getTitulo());
        return result;
    }

    public ResultValidationEnum updateTask(Task task) {
        ResultValidationEnum result = taskValidation.validateTask(task);
        if (result == ResultValidationEnum.REJECTED) {
            return result;
        }
        database.updateTask(task);
        taskNotifier.notifyObservers("Task atualizada: " + task.getTitulo());
        return result;
    }

    public Task getTaskById(int taskId) {
        Task task = database.getTaskById(taskId);
        ResultValidationEnum result = taskValidation.validateTask(task);
        if (result == ResultValidationEnum.REJECTED) {
            return null;
        }
        return task;
    }

    public void deleteTask(int taskId) {
        database.deleteTaskById(taskId);
        taskNotifier.notifyObservers("Task com ID: " + taskId + " deletado");
    }

    public List<Task> getAllTasks(int userId) {
        List<Task> tasks = database.getTasksByUserId(userId);
        return taskSortingStrategy.sort(tasks);
    }


}
