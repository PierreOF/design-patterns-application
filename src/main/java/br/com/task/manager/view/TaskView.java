package br.com.task.manager.view;

import br.com.task.manager.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class TaskView {
    private final Scanner scanner = new Scanner(System.in);

    public void displayTasks(List<Task> tasks) {
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public Task getNewTaskDetails() {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        System.out.print("Enter task priority (Low, Medium, High): ");
        String priority = scanner.nextLine();
        return new Task(description, priority, LocalDateTime.now());
    }

    public int getTaskIdForDeletion() {
        System.out.print("Enter task ID to delete: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
