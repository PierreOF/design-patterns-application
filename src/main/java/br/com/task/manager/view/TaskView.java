package br.com.task.manager.view;

import br.com.task.manager.controller.TaskController;
import br.com.task.manager.model.Enum.TaskPriorityEnum;
import br.com.task.manager.model.Task;
import br.com.task.manager.strategy.*;

import java.util.List;
import java.util.Scanner;

public class TaskView {

    private final Scanner scanner = new Scanner(System.in);
    private final TaskController taskController;
    private final int userId;

    public TaskView(TaskController taskController, int userId) {
        this.taskController = taskController;
        this.userId = userId;
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n");
            System.out.println("---- Menu de Task ----");
            System.out.println("1. Mostrar as tasks");
            System.out.println("2. Criar uma task");
            System.out.println("3. Insira um id para deletar uma task");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    displayTasks();
                    break;
                case 2:
                    addNewTask();
                    break;
                case 3:
                    deleteTask();
                    break;
                case 4:
                    System.out.println("Saindo...");
                    taskController.clearCacheByUserId(userId);
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 4);
    }

    private void displayTasks() {
        System.out.print("Escolha a estratégia de ordenação (1: PRIORIDADE, 2: STATUS, 3: DATA, 4 TITULO): ");
        int sortOption = Integer.parseInt(scanner.nextLine());
        TaskSortingStrategy sortingStrategy = getSortingStrategy(sortOption);

        List<Task> tasks = taskController.getAllTasks(userId, sortingStrategy);
        if (tasks.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
        } else {
            System.out.println("==========================\n" +
                               "         Task Info         ");
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    private void addNewTask() {
        Task newTask = getNewTaskDetails();
        taskController.addTask(newTask);
        System.out.println("Tarefa adicionada com sucesso!");
    }

    private void deleteTask() {
        int taskId = getTaskIdForDeletion();
        taskController.deleteTask(taskId);
        System.out.println("Tarefa deletada com sucesso!");
    }

    private Task getNewTaskDetails() {
        System.out.print("Insira o titulo da task: ");
        String title = scanner.nextLine();
        System.out.print("Digite a descrição da tarefa: ");
        String description = scanner.nextLine();
        System.out.print("Digite a prioridade da tarefa (Urgente, Importante, Normal, Baixa): ");
        String priority = scanner.nextLine();
        return new Task(title, description, userId, TaskPriorityEnum.fromDescription(priority));
    }

    private int getTaskIdForDeletion() {
        System.out.print("Digite o ID da tarefa para deletar: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private TaskSortingStrategy getSortingStrategy(int sortOption) {
        switch (sortOption) {
            case 1:
                return new SortByPriority();
            case 2:
                return new SortByCompletionStatus();
            case 3:
                return new SortByCreationDate();
            case 4:
                return new SortByTitle();
            default:
                System.out.println("Opção inválida. Usando a ordenação padrão por data de criação.");
                return new SortByCreationDate();
        }
    }
}
