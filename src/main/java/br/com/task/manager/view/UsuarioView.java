package br.com.task.manager.view;

import br.com.task.manager.controller.TaskController;
import br.com.task.manager.controller.UsuarioController;
import br.com.task.manager.controller.validation.ResultValidationEnum;
import br.com.task.manager.db.proxy.TaskProxyDAOInterface;
import br.com.task.manager.db.proxy.TasksProxy;
import br.com.task.manager.db.proxy.UsuarioProxy;
import br.com.task.manager.db.proxy.UsuarioProxyDAOInterface;
import br.com.task.manager.email.EmailService;
import br.com.task.manager.model.Usuario;
import br.com.task.manager.observer.TaskNotifier;

import java.sql.Connection;
import java.util.Scanner;

public class UsuarioView {

    private final UsuarioController usuarioController;
    private final Scanner scanner;
    private final TaskProxyDAOInterface taskProxyDAO;
    private final TaskNotifier taskNotifier;

    public UsuarioView(Connection connection, TaskNotifier taskNotifier, EmailService emailService) {
        this.usuarioController = new UsuarioController(connection, taskNotifier, emailService);
        this.scanner = new Scanner(System.in);
        this.taskProxyDAO = new TasksProxy(connection);
        this.taskNotifier = taskNotifier;
    }

    public void menu() {
        int opcao = 0;
        do {
            System.out.println("---- Menu de Usuário ----");
            System.out.println("1. Login");
            System.out.println("2. Cadastrar novo usuário");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.\n");
                continue;
            }

            switch (opcao) {
                case 1:
                    login();
                    break;
                case 2:
                    cadastrar();
                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 3);
    }

    private void login() {
        System.out.println("---- Login de Usuário ----");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = usuarioController.login(email, senha);

        if (usuario != null) {
            System.out.println("Login realizado com sucesso! Bem-vindo, " + usuario.getNome() + "!");
            TaskController taskController = new TaskController(taskProxyDAO, taskNotifier);
            TaskView taskView = new TaskView(taskController, usuario.getId(), usuarioController);
            taskView.menu();
        } else {
            System.out.println("Email ou senha inválidos.");
        }
    }

    private void cadastrar() {
        System.out.println("---- Cadastro de Novo Usuário ----");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        ResultValidationEnum resultValidation = usuarioController.register(nome, email, senha);

        if (resultValidation == ResultValidationEnum.APPROVED) {
            System.out.println("Usuário cadastrado com sucesso!");
            return;
        }
        System.out.println("Erro ao cadastrar usuário.");
    }
}
