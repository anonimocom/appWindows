/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package navegadorApretaste.Navegador;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class mail {

    public static final String MIXED = "mixed";
    public static final String PLAIN = "plain";
    public static final String UTF_8 = "UTF-8";
    public static final String TXT1 = ".txt";
    public static final String ZIP = ".zip";
    public static final String APR = "apr";
    public static String username = "david@localhost";
    public static String password = "admin";
    public static final String BIT = "7bit";
    public static final String APPLICAION_ZIP = "applicaion/zip";
    public static Properties props = new Properties();
    private static Object message;
    public boolean sendRequestError = false;
    public String lastTicket;

    public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";

    public void setSendRequestError(boolean sRE) {
        this.sendRequestError = sRE;
    }

    public void setlastTicket(String lastTicket) {
        this.lastTicket = lastTicket;
    }

    public static void main(String[] args) {
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "false");
//        props.put("mail.smtp.host", "localhost");
//        props.put("mail.smtp.port", "25");
//        Session session = Session.getInstance(props, new Authenticator() {
//            protected PasswordAuthentication
//                    getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//        try {
//            Message message = new MimeMessage(session);
////Creamos un nuevo mensaje, y le pasamos nuestra sesión  iniciada en el paso anterior.Message message = newMimeMessage(session);
////Seteamos la dirección desde la cual enviaremos el mensaje
//            message.setFrom(new InternetAddress("david@localhost"));
////Seteamos el destino de nuestro mensaje
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("david@localhost"));
////Seteamos el asunto
//            message.setSubject("random");
//
////Y por ultimo el texto.
//            message.setText("");
////Esta orden envía el mensaje
//            Transport.send(message);
//
////Con esta imprimimos en consola que el mensaje fue enviado
//            System.out.println("Mensaje Enviado");
//        } catch (MessagingException e) {
////Si existiera un error en el envío lo hacemos saber al cliente y lanzamos una excepcion.
//            System.out.println("Hubo un error al enviar el mensaje.");
//            throw new RuntimeException(e);
//        }
        mail m = new mail();
        m.sendMail();
    }

    void sendMail() {
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.port", "25");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication
                    getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
             message.setFrom(new InternetAddress("david@localhost"));
////Seteamos el destino de nuestro mensaje
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("david@localhost"));

            String ticket = new UtilHelper().genString();
            if (!sendRequestError) {
                message.setSubject(ticket);
                setlastTicket(ticket);
            } else {
                message.setSubject(lastTicket);
            }

            //Creamos el mensaje que vamos a enviar
            //====================================================
            //parte del cuerpo del mensaje
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText(new UtilHelper().genString(), UTF_8, PLAIN);
            messagePart.setDisposition(Part.INLINE);
            messagePart.setHeader(CONTENT_TRANSFER_ENCODING, BIT);

            Multipart multipart = new MimeMultipart(MIXED);
            multipart.addBodyPart(messagePart);//agregamos la parte del texto del mensaje

            //parte del adjunto
            try {
                File file = new UtilHelper().Compress("FACEBOOK");//obtenemos el archivo adjunto que vamos a enviar

                FileInputStream str = new FileInputStream(file);//abrimos un strea para leer el archivo
                byte[] buffer = new byte[(int) file.length()];//creamos un buffer para almacenar el contenido del archivo
                if (str.read(buffer, 0, (int) file.length()) == -1) {
                    throw new Exception("Archivo vacío");//si el archivo esta vacio paso algo y lanzamos un error
                }
                //creamos la parte que contendra el adjunto
                MimeBodyPart attachmentPart = new MimeBodyPart();
                DataSource source = new ByteArrayDataSource(buffer, APPLICAION_ZIP);//creamos la fuente de donde se va a obtener los datos;
                attachmentPart.setDataHandler(new DataHandler(source));//creamos un manejador para la fuente de datos
                attachmentPart.setFileName(new UtilHelper().genString() + ZIP);//le ponemos un nombre random al archivo q vamos a enviar
                multipart.addBodyPart(attachmentPart);//agregamos la parte del adjunto al mensaje que vamos a enviar
                attachmentPart.setDisposition(Part.ATTACHMENT);//y decimos q esta parte es un adunto
            } catch (Exception e) {
                e.printStackTrace();//si ocurrio algun error lo lanzamos y cancelamos la tarea

                return;
            }

            if (!sendRequestError) {
                message.setContent(multipart);
            } else {

                message.setText("");
            }
             Transport.send(message);
        } catch (MessagingException e) {
//Si existiera un error en el envío lo hacemos saber al cliente y lanzamos una excepcion.
            System.out.println("Hubo un error al enviar el mensaje.");
            throw new RuntimeException(e);
        }
    }

}
