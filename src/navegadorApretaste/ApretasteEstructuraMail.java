package navegadorApretaste;

import java.util.ArrayList;
import java.util.Arrays;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author David24
 */
public class ApretasteEstructuraMail {
    String URL="";
    String METODO;
    String ID;
    String JS;
    String IMAGEN;
    String DESTINO;
    ArrayList<String> argumentos;

    public void parsear(String mensaje,String destino) {
        String[] split = mensaje.split("\n");
        URL = split[0];
        METODO = split[1];
        ID = split[2];
        JS = split[3];
        IMAGEN = split[4];
        DESTINO=destino;
        String arg= split[5];
        String[] argument = arg.split("&");
        argumentos=new ArrayList<String>();
        argumentos.addAll(Arrays.asList(argument));      
    }

    public String crearMensaje() {
        String mensage = null;
        return mensage;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMETODO() {
        return METODO;
    }

    public void setMETODO(String METODO) {
        this.METODO = METODO;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getJS() {
        return JS;
    }

    public void setJS(String JS) {
        this.JS = JS;
    }

    public String getIMAGEN() {
        return IMAGEN;
    }

    public void setIMAGEN(String IMAGEN) {
        this.IMAGEN = IMAGEN;
    }

    public String getDESTINO() {
        return DESTINO;
    }

    public void setDESTINO(String DESTINO) {
        this.DESTINO = DESTINO;
    }

    
    @Override
    public String toString() {
        return URL + METODO + ID + JS + IMAGEN+DESTINO+argumentos.toString();
    }

}
