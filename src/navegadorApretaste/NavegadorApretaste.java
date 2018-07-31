package navegadorApretaste;

import navegadorApretaste.Navegador.ApretasteConfiguracion;
import navegadorApretaste.Navegador.ApretasteMailUtil;
import com.chilkatsoft.CkEmail;
import com.chilkatsoft.CkEmailBundle;
import com.chilkatsoft.CkMailMan;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author David24
 */
public class NavegadorApretaste {

    static {
        try {
            System.loadLibrary("chilkat");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);
            System.exit(1);
        }
    }

    ApretasteMailUtil mailUtil = new ApretasteMailUtil();
    ApretasteEstructuraMail mensaje = new ApretasteEstructuraMail();

    public void enviarMail() {
        mailUtil.enviarMail(mensaje.DESTINO,"Asunto");

    }

    public void leerMail() {
        CkMailMan mailman = new CkMailMan();
        //  Any string argument automatically begins the 30-day trial.
        boolean success = mailman.UnlockComponent("30-day trial");
        if (success != true) {
            System.out.println("Component unlock failed");
            return;
        }
        //  Set the POP3 server's hostname
        mailman.put_MailHost(ApretasteConfiguracion.popServer);
        //  Set the POP3 login/password.
        mailman.put_PopUsername(ApretasteConfiguracion.userMail);
        mailman.put_PopPassword(ApretasteConfiguracion.contrasenaMail);
        //  Indicate that we want TLS/SSL.  Also, set the port to 995:
        mailman.put_MailPort(ApretasteConfiguracion.portPOP);
        mailman.put_PopSsl(true);
        CkEmailBundle bundle;
        //  Read mail headers and one line of the body.
        bundle = mailman.CopyMail();
        if (bundle == null) {
            System.out.println(mailman.lastErrorText());
            return;
        }

        CkEmail email;
        for (int i = 0; i <= bundle.get_MessageCount() - 1; i++) {
            email = bundle.GetEmail(i);
            //    System.out.println("From: " + email.ck_from());
            // System.out.println("Subject: " + email.subject());
            //  System.out.println("Body: " + email.getPlainTextBody());  
            if (email.subject().trim().equals("internet")) {
                System.out.println("Recibiendo un mensaje");
                String plainTextBody = email.getPlainTextBody();
                mensaje.parsear(plainTextBody, email.fromAddress());
                /////////////Borra el correo
                success = mailman.DeleteEmail(email);
                if (success != true) {
                    System.out.println(mailman.lastErrorText());
                    return;
                }
            }
        }
    }

    public static void main(String args[]) {
        while (true) {
            File f = new File("temp");
            boolean exists = f.exists();
            if (!exists) {
                f.mkdir();
            }
            /*
            Inicializamos el Servidor mostrando la ultima pagina descargada ,sino una de biembenida
            Al hacer clic en un vinculo ,capturar la url y enviarla por correo
            Mostrar una pagina de espera
            Cuando llegue la respuesta correcta actualizar el servidor  y mostrar la pagina
            */
            NavegadorApretaste internetMail = new NavegadorApretaste();
            internetMail.mensaje = new ApretasteEstructuraMail();
            internetMail.leerMail();
            if (internetMail.mensaje.URL.equals("")) {
                continue;
            }
            System.out.println("recibido el mensaje  " + internetMail.mensaje.toString());

            System.out.println("descargado");
        }
        //ServerHttp serverHttp=new ServerHttp();
    }
}
