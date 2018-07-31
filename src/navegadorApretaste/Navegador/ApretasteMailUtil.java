/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package navegadorApretaste.Navegador;

import com.chilkatsoft.CkEmail;
import com.chilkatsoft.CkEmailBundle;
import com.chilkatsoft.CkMailMan;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author David24
 */
public class ApretasteMailUtil {

    static {
        try {
            System.loadLibrary("chilkat");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);
            System.exit(1);
        }
    }

    public void enviarMail(String destino,String asunto) {
        CkMailMan mailman = new CkMailMan();
        //  Any string argument automatically begins the 30-day trial.
        boolean success = mailman.UnlockComponent("30-day trial");
        if (success != true) {
            System.out.println(mailman.lastErrorText());
            return;
        }

        //  Set the SMTP server.
        mailman.put_SmtpHost("localhost");
        //  Set SMTP login and password (if necessary)        
        mailman.put_SmtpUsername("david@localhost");
        mailman.put_SmtpPassword("admin");
        mailman.put_SmtpSsl(false);
        mailman.put_SmtpPort(25);
//        //  Create a new email object
        CkEmail email = new CkEmail();
        email.put_Subject(asunto);
        email.put_Body("pagina web");
        //email.put_From("<admin@localhost>");
        success = email.AddTo("Admin", destino);
//        //String contentType = email.addFileAttachment("page.zip");
        success = mailman.SendEmail(email);
        if (success != true) {
            System.out.println(mailman.lastErrorText());
            return;
        }
        success = mailman.CloseSmtpConnection();
        if (success != true) {
            System.out.println("Connection to SMTP server not closed cleanly.");
        }
        System.out.println("Correo Enviado");
    }

    public String leerMail() {
        CkMailMan mailman = new CkMailMan();
        //  Any string argument automatically begins the 30-day trial.
        boolean success = mailman.UnlockComponent("30-day trial");
        if (success != true) {
            System.out.println("Component unlock failed");
            return "";
        }
        //  Set the POP3 server's hostname
        mailman.put_MailHost("localhost");
        //  Set the POP3 login/password.
        mailman.put_PopUsername("david@localhost");
        mailman.put_PopPassword("admin");
        //  Indicate that we want TLS/SSL.  Also, set the port to 995:
        mailman.put_MailPort(110);
        mailman.put_PopSsl(false);
        CkEmailBundle bundle;
        //  Read mail headers and one line of the body.
        bundle = mailman.CopyMail();
        if (bundle == null) {
            System.out.println(mailman.lastErrorText());
            return "";
        }

        CkEmail email;
        for (int i = 0; i <= bundle.get_MessageCount() - 1; i++) {
            email = bundle.GetEmail(i);
             // /System.out.println("From: " + email.ck_from());
            // System.out.println("Subject: " + email.subject());
             // System.out.println("Body: " + email.getPlainTextBody());  
              
            if (email.subject().trim().equals("Facebook")) {
                System.out.println("Recibiendo un mensaje");
                String plainTextBody = email.getHtmlBody();
                System.out.println(plainTextBody);
                //mensaje.parsear(plainTextBody, email.fromAddress());
                /////////////Borra el correo
                
                
                if (success != true) {
                    System.out.println(mailman.lastErrorText());
                    return"";
                }
                try {
                BufferedWriter out = new BufferedWriter(new FileWriter("temp/index.html"));
                out.write(plainTextBody);  //Replace with the string 
                out.close();
                success = mailman.DeleteEmail(email);
            } catch (IOException e) {
                System.out.println("Exception ");

            }

                return plainTextBody;
            }
        }
        return  "";
    }
    public static void main(String[] args) {
        ApretasteMailUtil apretasteMailUtil=new ApretasteMailUtil();
        //apretasteMailUtil.enviarMail("david@localhost","facebook");
        apretasteMailUtil.leerMail();
    }

}
