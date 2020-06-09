package utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class SendMail {
    private static SendMail sendMail = new SendMail();

    final Properties properties;
    private Session mailSession;

    private SendMail() {
        properties = new Properties();
        try {
            properties.load(SendMail.class.getClassLoader().getResourceAsStream("mail.properties"));
        } catch (IOException e) {
            System.err.println("Error in sending email");
        }
        mailSession = Session.getDefaultInstance(properties);
    }

    public static SendMail getInstance() {
        return sendMail;
    }

    public void sendLoginAndPassword(String login, String password, String email) {
        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress("noreply"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Your password for Space Marine");
            message.setText("Login: " + login + "\nPassword: " + password);

            Transport tr = mailSession.getTransport();
            tr.connect(null, "yofworxzvkkqxjax");
            tr.sendMessage(message, message.getAllRecipients());
            tr.close();
        } catch (MessagingException e) {
            System.err.println("Error while sending message");
        }
    }
}
