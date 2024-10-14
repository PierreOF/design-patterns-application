package br.com.task.manager;

import br.com.task.manager.db.dao.SQLiteConnection;
import br.com.task.manager.db.proxy.TaskProxyDAOInterface;
import br.com.task.manager.db.proxy.TasksProxy;
import br.com.task.manager.db.proxy.UsuarioProxyDAOInterface;
import br.com.task.manager.db.proxy.UsuarioProxy;
import br.com.task.manager.email.EmailService;
import br.com.task.manager.email.EmailServiceImpl;
import br.com.task.manager.observer.TaskNotifier;
import br.com.task.manager.view.UsuarioView;

import java.sql.Connection;

public class Main {
    
    public static void main(String[] args) {

        SQLiteConnection.createTables();

        Connection conn = SQLiteConnection.connect();
        UsuarioProxyDAOInterface usuarioProxy = new UsuarioProxy(conn);
        TaskProxyDAOInterface taskProxy = new TasksProxy(conn);
        TaskNotifier taskNotifier = new TaskNotifier();
        EmailService emailService = new EmailServiceImpl();

        UsuarioView usuarioView = new UsuarioView(conn, taskProxy, usuarioProxy, taskNotifier, emailService);
        usuarioView.menu();

        SQLiteConnection.close();
    }
}
