package br.com.task.manager.db.dao;

import br.com.task.manager.db.proxy.TaskProxyDAOInterface;
import br.com.task.manager.model.Task;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class TaskDAO implements TaskProxyDAOInterface {
    private Connection connection;

    public TaskDAO(Connection connection) {
        this.connection = connection;
    }

    public void deleteTasksByUsuarioId(int usuarioId) {
        String sql = "DELETE FROM tasks WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTaskById(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTask(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, status = ?, priority = ?, completed = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getTitulo());
            stmt.setString(2, task.getDescricao());
            stmt.setString(3, task.getStatus());
            stmt.setString(4, task.getPriority());
            stmt.setBoolean(5, task.isCompleted());
            stmt.setInt(6, task.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertTask(Task task) {
        String sql = "INSERT INTO tasks (title, description, status, user_id, priority, completed, creation_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getTitulo());
            stmt.setString(2, task.getDescricao());
            stmt.setString(3, task.getStatus());
            stmt.setInt(4, task.getIdUsuario());
            stmt.setString(5, task.getPriority());
            stmt.setBoolean(6, task.isCompleted());
            stmt.setTimestamp(7, Timestamp.valueOf(task.getCreationDate()));
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert task, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> getTasksByUserId(int usuarioId) {
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
                String priority = rs.getString("priority");
                boolean completed = rs.getBoolean("completed");
                LocalDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime();

                tasks.add(new Task(id, titulo, descricao, status, priority, completed, creationDate, usuarioId));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    public Task getTaskById(int id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String titulo = rs.getString("title");
                String descricao = rs.getString("description");
                String status = rs.getString("status");
                String priority = rs.getString("priority");
                boolean completed = rs.getBoolean("completed");
                LocalDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime();
                int idUsuario = rs.getInt("user_id");
                return new Task(id, titulo, descricao, status, priority, completed, creationDate, idUsuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
