package br.com.task.manager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class SQLiteConnection {

    private static final String DATABASE_URL = "jdbc:sqlite:task-manager.db";
    private static Connection connection;

    // Conecta ao banco de dados SQLite
    public static Connection connect() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    // Fecha a conexão com o banco de dados
    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Cria as tabelas "users" e "tasks" no banco de dados
    public static void createTables() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "email TEXT NOT NULL,"
                + "password TEXT NOT NULL"
                + ");";

        String createTasksTable = "CREATE TABLE IF NOT EXISTS tasks ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "title TEXT NOT NULL,"
                + "description TEXT NOT NULL,"
                + "status TEXT NOT NULL,"
                + "priority TEXT NOT NULL,"
                + "completed BOOLEAN NOT NULL,"
                + "creation_date TIMESTAMP NOT NULL,"
                + "user_id INTEGER NOT NULL,"
                + "FOREIGN KEY(user_id) REFERENCES users(id)"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createTasksTable);
            System.out.println("Tabelas criadas ou já existem.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

