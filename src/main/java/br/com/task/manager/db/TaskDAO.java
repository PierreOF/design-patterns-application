package br.com.task.manager.db;

import br.com.task.manager.model.Task;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class TaskDAO {
    private Connection connection;

    public TaskDAO(Connection connection) {
        this.connection = connection;
    }

    // Insere uma nova tarefa no banco de dados
    public void insert(Task task) {
        String sql = "INSERT INTO tasks (title, description, status, user_id, priority, completed, creation_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getTitulo());
            stmt.setString(2, task.getDescricao());
            stmt.setString(3, task.getStatus());
            stmt.setInt(4, task.getIdUsuario());
            stmt.setString(5, task.getPriority()); // Adiciona o campo priority
            stmt.setBoolean(6, task.isCompleted()); // Adiciona o campo completed
            stmt.setTimestamp(7, Timestamp.valueOf(task.getCreationDate())); // Adiciona o campo creation_date
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Busca as tarefas de um determinado usuário
    public List<Task> getTasksByUsuario(int usuarioId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("title");
                String descricao = rs.getString("description");
                String status = rs.getString("status");
                String priority = rs.getString("priority"); // Obtém o campo priority
                boolean completed = rs.getBoolean("completed"); // Obtém o campo completed
                LocalDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime(); // Obtém o campo creation_date


                tasks.add(new Task(id, titulo, descricao, status, priority, completed, creationDate, usuarioId));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }
}
