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


        Usuario novoUsuario = new Usuario("Carlos Silva", "carlos@example.com", "senha123");
        usuarioProxy.insertUser(novoUsuario);
        System.out.println("Usuário inserido: " + usuarioProxy.getUsuarioById(1));


        Usuario usuarioLogado = usuarioProxy.userLogin("carlos@example.com", "senha123");
        if (usuarioLogado != null) {
            System.out.println("Usuário logado: " + usuarioLogado);
        } else {
            System.out.println("Usuário não encontrado");
        }


        Usuario usuarioAtualizado = new Usuario(1, "Carlos Silva", "carlos@example.com", "novaSenha123");
        usuarioProxy.updateUser(usuarioAtualizado);
        System.out.println("Usuário atualizado: " + usuarioProxy.getUsuarioById(1));


        Task novaTarefa = new Task("Comprar leite", "Comprar leite no supermercado", "Pendente", usuarioLogado.getId(), "Baixa");
        taskProxy.insertTask(novaTarefa);
        System.out.println("Tarefa inserida: " + novaTarefa.getTitulo());


        List<Task> tarefas = taskProxy.getTasksByUserId(usuarioLogado.getId());
        System.out.println("Tarefas para o usuário " + usuarioLogado.getNome() + ":");
        for (Task tarefa : tarefas) {
            System.out.println("- " + tarefa.getTitulo() + " (Status: " + tarefa.getStatus() + ")");
        }


        if (!tarefas.isEmpty()) {
            Task tarefaParaAtualizar = tarefas.get(0);
            tarefaParaAtualizar.setTitulo("Comprar leite e pão");
            taskProxy.updateTask(tarefaParaAtualizar);
            System.out.println("Tarefa atualizada: " + tarefaParaAtualizar.getTitulo());
        }


        if (!tarefas.isEmpty()) {
            Task tarefaParaExcluir = tarefas.get(0);
            taskProxy.deleteTaskById(tarefaParaExcluir.getId());
            System.out.println("Tarefa excluída: " + tarefaParaExcluir.getTitulo());
        }


        usuarioProxy.deleteUser(usuarioLogado.getId());
        System.out.println("Usuário excluído: " + usuarioLogado.getNome());


        Usuario usuarioExcluido = usuarioProxy.getUsuarioById(usuarioLogado.getId());
        if (usuarioExcluido == null) {
            System.out.println("Usuário não encontrado (excluído com sucesso).");
        }

        SQLiteConnection.close();
        SQLiteConnection.deleteDatabase();
    }
}
