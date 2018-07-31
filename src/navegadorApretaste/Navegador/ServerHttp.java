/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package navegadorApretaste.Navegador;

import navegadorApretaste.Navegador.NanoHTTPD;
import java.util.ArrayList;

/**
 *
 * @author David
 */
import java.io.IOException;
import java.io.File;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerHttp extends NanoHTTPD {

    int estado = 0;
    final int MENSAJE_BIENVENIDA = 0;
    final int MENSAJE_ESPERAR = 1;

    public ServerHttp() throws IOException {
        super(8081, new File("."));
        System.out.println("Inicializando servidor en 80");
    }

    @Override
    public Response serve(String uri, String method, Properties header, Properties parms, Properties files) {
//
//        System.out.println("**********Peticion************* " + uri + " \n");
//        System.out.println("Metodo: " + method);
//        Enumeration e = parms.propertyNames();
//        Enumeration eh = header.propertyNames();

//            while (eh.hasMoreElements()) {
//                String value = (String) eh.nextElement();
//                
//            }
        
        //1- enviar correo
        //2- esperar respuesta
        //3- guardar en un fichero
        File f = new File("temp/index.html");
        if (f.exists()) {
            System.out.println("El archivo existe");
            return serveFile("/temp/", header, getMyRootDir(), true);
        } 
            else if (estado == MENSAJE_BIENVENIDA) {
            System.out.println("Bienvenida");
            return serveFile("/temp/bienvenida.html", header, getMyRootDir(), true);
        } else if (estado == MENSAJE_ESPERAR) {
            System.out.println("Esperar");
            return serveFile("/temp/esperar.html", header, getMyRootDir(), true);
        }
        System.out.println("Nad");
       return serveFile("/temp/", header, getMyRootDir(), true);
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

   
}
