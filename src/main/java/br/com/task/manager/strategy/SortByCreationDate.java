package br.com.task.manager.strategy;

import br.com.task.manager.model.Task;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByCreationDate implements TaskSortingStrategy {
    @Override
    public List<Task> sort(List<Task> tasks) {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getCreationDate))
                .collect(Collectors.toList());
    }
}
