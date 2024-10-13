package br.com.task.manager.controller;

import br.com.task.manager.model.Task;
import br.com.task.manager.proxy.UsuarioProxy;

public class TaskController {
    private UsuarioProxy database;

    public TaskController(UsuarioProxy database) {
        this.database = database;
    }

    public void addTask(Task task) {
        database.addTask(task);
    }
}