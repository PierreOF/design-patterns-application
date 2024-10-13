package br.com.task.manager.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {

    private final String username = System.getenv("MAILTRAP_USERNAME");
    private final String password = System.getenv("MAILTRAP_PASSWORD");
    private final String host = System.getenv("MAILTRAP_HOST");
    private final String port = System.getenv("MAILTRAP_PORT");
    private final String fromEmail = System.getenv("MAILTRAP_FROM_EMAIL");

    public void sendEmail(String to, String subject, String content) {
        if (username == null || password == null || host == null || port == null || fromEmail == null) {
            System.out.println("Erro: Variáveis de ambiente não configuradas corretamente.");
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            System.out.println("E-mail enviado com sucesso para " + to);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}
