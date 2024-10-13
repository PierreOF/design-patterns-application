package br.com.task.manager;

import br.com.task.manager.db.dao.SQLiteConnection;
import br.com.task.manager.db.proxy.TaskProxyDAOInterface;
import br.com.task.manager.db.proxy.TasksProxy;
import br.com.task.manager.db.proxy.UsuarioProxyDAOInterface;
import br.com.task.manager.db.proxy.UsuarioProxy;
import br.com.task.manager.model.Enum.TaskPriorityEnum;
import br.com.task.manager.model.Task;
import br.com.task.manager.model.Usuario;
import br.com.task.manager.view.UsuarioView;

import java.sql.Connection;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        SQLiteConnection.createTables();

        Connection conn = SQLiteConnection.connect();
        UsuarioProxyDAOInterface usuarioProxy = new UsuarioProxy(conn);
        TaskProxyDAOInterface taskProxy = new TasksProxy(conn);

        UsuarioView usuarioView = new UsuarioView(conn, taskProxy);
        usuarioView.menu();

        SQLiteConnection.close();
    }
}
