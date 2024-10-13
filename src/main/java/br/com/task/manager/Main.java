package br.com.task.manager;

import br.com.task.manager.db.SQLiteConnection;
import br.com.task.manager.model.Task;
import br.com.task.manager.model.Usuario;
import br.com.task.manager.proxy.UsuarioProxy;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SQLiteConnection.createTables();

        Connection conn = SQLiteConnection.connect();
        UsuarioProxy proxy = new UsuarioProxy(conn);

        proxy.addUsuario(new Usuario("João", "Joao@gmail.com", "123"));
        proxy.addTask(new Task("Estudar Java", "Estudar Java", "pendente", 1));
        proxy.addTask(new Task("Estudar Python", "Estudar Python", "pendente", 1));
        proxy.addTask(new Task("Estudar JavaScript", "Estudar JavaScript", "pendente", 1));

        Usuario usuario = proxy.getUsuarioById(1);
        System.out.println("Usuário: " + usuario.getNome());

        List<Task> tasks = proxy.getTasksByUsuarioId(1);
        for (Task t : tasks) {
            System.out.println("Task: " + t.getDescricao() + " | Status: " + t.getStatus());
        }

        SQLiteConnection.close();
    }
}
