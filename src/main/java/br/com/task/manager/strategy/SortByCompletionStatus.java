package br.com.task.manager.strategy;

import br.com.task.manager.model.Task;

import java.util.Comparator;
import java.util.List;

public class SortByCompletionStatus implements TaskSortingStrategy {
    @Override
    public List<Task> sort(List<Task> tasks) {
        tasks.sort(Comparator.comparing(Task::getStatus));
        return tasks;
    }
}
