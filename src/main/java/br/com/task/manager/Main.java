package br.com.task.manager;

import br.com.task.manager.db.dao.SQLiteConnection;
import br.com.task.manager.db.proxy.TaskProxyDAOInterface;
import br.com.task.manager.db.proxy.TasksProxy;
import br.com.task.manager.db.proxy.UsuarioProxyDAOInterface;
import br.com.task.manager.db.proxy.UsuarioProxy;
import br.com.task.manager.model.Task;
import br.com.task.manager.model.Usuario;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SQLiteConnection.createTables();

        Connection conn = SQLiteConnection.connect();
        UsuarioProxyDAOInterface usuarioProxy = new UsuarioProxy(conn);
        TaskProxyDAOInterface taskProxy = new TasksProxy(conn);

//        Usuario user = new Usuario("Carlos", "email","senha");
//        usuarioProxy.insertUser(user);


        Usuario usuarioLogado = usuarioProxy.userLogin("email", "senha");
        if (usuarioLogado != null) {
            System.out.println("Usuário logado: " + usuarioLogado);
        } else {
            System.out.println("Usuário não encontrado");
        }

//        Task novaTarefa = new Task("Comprar agua", "Comprar leite no supermercado", usuarioLogado.getId(), TaskPriorityEnum.BAIXA);
//        taskProxy.insertTask(novaTarefa);
//        System.out.println("Tarefa inserida: " + novaTarefa.getTitulo());
//
//        novaTarefa = new Task("Comprar Pão", "Comprar pão", usuarioLogado.getId(), TaskPriorityEnum.BAIXA);
//        taskProxy.insertTask(novaTarefa);
//        System.out.println("Tarefa inserida: " + novaTarefa.getTitulo());
//
//        novaTarefa = new Task("Comprar café", "Comprar café", usuarioLogado.getId(), TaskPriorityEnum.BAIXA);
//        taskProxy.insertTask(novaTarefa);
//        System.out.println("Tarefa inserida: " + novaTarefa.getTitulo());

        List<Task> tarefas = taskProxy.getTasksByUserId(usuarioLogado.getId());
        System.out.println("Tarefas para o usuário " + usuarioLogado.getNome() + ":");
        for (Task tarefa : tarefas) {
            System.out.println("- " + tarefa.getTitulo() + " (Status: " + tarefa.getStatus() + ")");
        }

        SQLiteConnection.close();
    }
}
