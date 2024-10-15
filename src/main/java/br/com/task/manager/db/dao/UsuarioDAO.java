package br.com.task.manager.db.dao;

import br.com.task.manager.db.proxy.UsuarioProxyDAOInterface;
import br.com.task.manager.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements UsuarioProxyDAOInterface {
    private Connection connection;
    private TaskDAO tasksdao;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
        this.tasksdao = new TaskDAO(connection);
    }

    public Usuario userLogin(String email, String senha) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("name");
                return new Usuario(id, nome, email, senha);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Usuario insertUser(Usuario usuario) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    usuario.setId(generatedId);
                } else {
                    throw new SQLException("Falha ao inserir o usuário, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    public void updateUser(Usuario usuario) {
        String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setInt(4, usuario.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
            tasksdao.deleteTasksByUsuarioId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Usuario getUsuarioById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("name");
                String email = rs.getString("email");
                String senha = rs.getString("password");
                return new Usuario(id, nome, email, senha);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // Método implementado apenas para o Proxy
    @Override
    public void clearCache() {}

    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("name");
                String email = rs.getString("email");
                String senha = rs.getString("password");
                usuarios.add(new Usuario(id, nome, email, senha));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }
}
