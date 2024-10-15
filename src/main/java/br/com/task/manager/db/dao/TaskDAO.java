package br.com.task.manager.db.dao;

import br.com.task.manager.db.proxy.TaskProxyDAOInterface;
import br.com.task.manager.model.Enum.TaskPriorityEnum;
import br.com.task.manager.model.Enum.TaskStatusEnum;
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

    // Esse método não é utilizado no banco de dados e sim no proxy
    @Override
    public void clearCacheByUserId(int userId) {}

    public void deleteTaskById(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTask(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, status = ?, priority = ? WHERE id = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getTitulo());
            stmt.setString(2, task.getDescricao());
            stmt.setString(3, task.getStatus().getDescription());
            stmt.setString(4, task.getPriority().getDescription());
            stmt.setInt(5, task.getId());
            stmt.setInt(6, task.getIdUsuario());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Tarefa atualizada com sucesso! Task ID: " + task.getId());
                System.out.println("Novos dados da task: " + task);
            } else {
                System.out.println("Nenhuma tarefa foi atualizada. Verifique o ID da tarefa.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar a tarefa", e);
        }
    }

    @Override
    public Integer insertTask(Task task) {
        String sql = "INSERT INTO tasks (title, description, status, user_id, priority, creation_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, task.getTitulo());
            stmt.setString(2, task.getDescricao());
            stmt.setString(3, task.getStatus().getDescription());
            stmt.setInt(4, task.getIdUsuario());
            stmt.setString(5, task.getPriority().getDescription());
            stmt.setTimestamp(6, Timestamp.valueOf(task.getCreationDate()));

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert task, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to insert task, no ID obtained.");
                }
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
                LocalDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime();

                tasks.add(new Task(id, titulo, descricao, TaskStatusEnum.fromDescription(status), TaskPriorityEnum.fromDescription(priority), creationDate, usuarioId));
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
                LocalDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime();
                int idUsuario = rs.getInt("user_id");
                return new Task(id, titulo, descricao, TaskStatusEnum.fromDescription(status), TaskPriorityEnum.fromDescription(priority), creationDate, idUsuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
