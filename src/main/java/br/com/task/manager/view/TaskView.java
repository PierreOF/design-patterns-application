package br.com.task.manager.view;

import br.com.task.manager.controller.TaskController;
import br.com.task.manager.controller.UsuarioController;
import br.com.task.manager.controller.validation.ResultValidationEnum;
import br.com.task.manager.model.Enum.TaskPriorityEnum;
import br.com.task.manager.model.Task;
import br.com.task.manager.strategy.SortByCompletionStatus;
import br.com.task.manager.strategy.SortByCreationDate;
import br.com.task.manager.strategy.SortByPriority;
import br.com.task.manager.strategy.TaskSortingStrategy;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TaskView {

    private final Scanner scanner = new Scanner(System.in);
    private final TaskController taskController;
    private final UsuarioController usuarioController;
    private final int userId;

    public TaskView(TaskController taskController, int userId, UsuarioController usuarioController) {
        this.taskController = taskController;
        this.usuarioController = usuarioController;
        this.userId = userId;
    }

    public void menu() {
        Integer opcao = 0;
        do {
            System.out.println("\n");
            System.out.println("---- Menu de Task ----");
            System.out.println("1. Mostrar as tasks");
            System.out.println("2. Criar uma task");
            System.out.println("3. Deletar uma task");
            System.out.println("4. Atualizar uma task");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.\n");
                continue;
            }
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
                    atualizarTask();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    usuarioController.clearCacheLogout(userId);
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 5);
    }

    private void atualizarTask() {
        int taskId = getTaskIdForUse();
        Task task = taskController.getTaskById(taskId);
        if (task == null) {
            System.out.println("Tarefa não encontrada.");
            return;
        }
        showTask(task);
        task = setNewDataToTask(task);
        task.setId(taskId);
        ResultValidationEnum resultValidation = taskController.updateTask(task);
        if (resultValidation == ResultValidationEnum.REJECTED) {
            System.out.println("Erro ao atualizar a tarefa.");
        } else {
            System.out.println("Tarefa atualizada com sucesso!");
        }
    }

    private Task setNewDataToTask(Task task) {
        System.out.println("---- Digite os novos valores ----");
        System.out.print("Titulo: ");
        String title = scanner.nextLine();
        System.out.print("Descrição: ");
        String description = scanner.nextLine();
        System.out.print("Status (Pendente, Cancelada, Concluida): ");
        String status = scanner.nextLine();
        System.out.print("Prioridade (Urgente, Importante, Normal, Baixa): ");
        String priority = scanner.nextLine();
        return new Task(title, description, status, task.getIdUsuario(), priority, task.getCreationDate());
    }

    private void showTask(Task task) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm");
        String formattedDate = task.getCreationDate().format(formatter);

        System.out.println("---- Detalhes da Task ----");
        System.out.println("ID: " + task.getId());
        System.out.println("Titulo: " + task.getTitulo());
        System.out.println("Descrição: " + task.getDescricao());
        System.out.println("Status: " + task.getStatus());
        System.out.println("Prioridade: " + task.getPriority());
        System.out.println("Data de criação: " + formattedDate);
        System.out.println("--------------------------");
    }

    private void displayTasks() {
        int sortOption = 0;
        do {
            try {
                System.out.println("---- Opções de ordenação ----");
                System.out.println("1. Ordenar por prioridade");
                System.out.println("2. Ordenar por status de conclusão");
                System.out.println("3. Ordenar por data de criação");
                System.out.println("4. Voltar");
                System.out.print("Escolha uma opção: ");
                sortOption = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.\n");
                continue;
            }
        } while (sortOption < 1 || sortOption > 4);

        if (sortOption == 4) {
            return;
        }

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
        int taskId = getTaskIdForUse();
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

    private int getTaskIdForUse() {
        System.out.print("Digite o ID da task: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private TaskSortingStrategy getSortingStrategy(int sortOption) {
        return switch (sortOption) {
            case 1 -> new SortByPriority();
            case 2 -> new SortByCompletionStatus();
            case 3 -> new SortByCreationDate();
            default -> {
                System.out.println("Opção inválida. Usando a ordenação padrão por data de criação.");
                yield new SortByCreationDate();
            }
        };
    }
}
