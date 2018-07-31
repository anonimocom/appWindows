/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package navegadorApretaste.Navegador;

/**
 *
 * @author David
 */
import java.io.File;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PopMail {

    private MimeMessage message;
    private Session session;
    private Folder folder;
    private Store store;
    private String mailHost = "localhost";
    private int mailPort;
    private String username = "david@localhost";
    private String password = "admin";

    private Properties properties = new Properties();

    public PopMail(boolean debug) {
        properties.put("mail.pop3.host", "localhost");
        properties.put("mail.pop3.port", "110");
        properties.put("mail.pop3.starttls.enable", "true");
        properties.put("mail.auth", "true");
         session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication
                    getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        session.setDebug(debug);
    }

    /**
     * ��ȡ�ʼ�
     */
    public void recieveEmail() {
        try {
            // Get the store
            store = session.getStore("pop3");
            store.connect("localhost", "admin@localhost", "admin");

            /* Get folder */
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            // Get directory
            Message message[] = folder.getMessages();

            for (int i = 0, n = message.length; i < n; i++) {
                System.out.println(i + ": " + message[i].getSentDate()
                        + "\t" + message[i].getSubject());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (folder != null) {
                try {
                    folder.close(false);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
            if (store != null) {
                try {
                    // Close connection
                    store.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) {
        PopMail se = new PopMail(false);
        se.recieveEmail();
    }
}
