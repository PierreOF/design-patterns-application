package br.com.task.manager.controller;


import br.com.task.manager.controller.validation.ResultValidationEnum;
import br.com.task.manager.controller.validation.TaskValidation;
import br.com.task.manager.controller.validation.interfaces.TaskInterfaceValidation;
import br.com.task.manager.db.proxy.TaskProxyDAOInterface;
import br.com.task.manager.db.proxy.UsuarioProxyDAOInterface;
import br.com.task.manager.model.Task;
import br.com.task.manager.observer.TaskNotifier;
import br.com.task.manager.strategy.TaskSortingStrategy;

import java.util.List;

public class TaskController {

    private final TaskInterfaceValidation taskValidation;
    private final TaskProxyDAOInterface database;
    private final TaskNotifier taskNotifier;

    public TaskController(TaskProxyDAOInterface databaseTask, TaskNotifier taskNotifier) {
        this.database = databaseTask;
        this.taskNotifier = taskNotifier;
        this.taskValidation = new TaskValidation();
    }

    public ResultValidationEnum addTask(Task task) {
        ResultValidationEnum result = taskValidation.validateTask(task);
        if (result == ResultValidationEnum.REJECTED) {
            return result;
        }
        database.insertTask(task);
        taskNotifier.notifyObservers("task criada com nome " + task.getTitulo());
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

        if (task == null) {
            return null;
        }
        ResultValidationEnum result = taskValidation.validateTask(task);
        if (result == ResultValidationEnum.REJECTED) {
            return null;
        }
        return task;
    }

    public ResultValidationEnum deleteTask(int taskId) {
        Task task = getTaskById(taskId);
        if (task != null) {
            database.deleteTaskById(taskId);
            taskNotifier.notifyObservers("Task com ID: " + taskId + " deletada");
            return ResultValidationEnum.APPROVED;
        }
        System.out.println("Task não encontrada para o ID: " + taskId + ". Não é possível deletar.");
        return ResultValidationEnum.REJECTED;
    }

    public List<Task> getAllTasks(int userId, TaskSortingStrategy sortingStrategy) {
        List<Task> tasks = database.getTasksByUserId(userId);
        return sortingStrategy.sort(tasks);
    }
}
