package br.com.task.manager.observer;

import java.util.ArrayList;
import java.util.List;

public class TaskNotifier {
    private final List<TaskObserver> observers = new ArrayList<>();

    public void addObserver(TaskObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TaskObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String taskDetails) {
        for (TaskObserver observer : observers) {
            observer.update(taskDetails);
        }
    }
}
