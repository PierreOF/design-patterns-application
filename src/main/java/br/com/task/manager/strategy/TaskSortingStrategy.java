package br.com.task.manager.strategy;

import br.com.task.manager.model.Task;

import java.util.List;

public interface TaskSortingStrategy {
    List<Task> sort(List<Task> tasks);
}
