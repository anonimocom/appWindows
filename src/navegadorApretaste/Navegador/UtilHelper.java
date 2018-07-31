package navegadorApretaste.Navegador;

import com.google.gson.Gson;
import java.awt.image.BufferedImage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by cjam on 21/1/2018.
 */
public class UtilHelper {

    private static Random random;

    static {

        random = new Random();
    }

    private Date timestamp;
    private boolean saveInternal = false;

    private boolean returnContent = false;

    public void setReturnContent(boolean returnContent) {
        this.returnContent = returnContent;
    }

    public String genString() {
        String[] words = new String[5];
        words[0] = "buscar";
        words[1] = "caballo";
        words[2] = "cabeza";
        words[3] = "cabo";
        words[4] = "caber";

        String w1 = words[random.nextInt(words.length)];
        String w2 = words[random.nextInt(words.length)];
        String w3 = words[random.nextInt(words.length)];
        return w1 + " " + w2 + " " + w3;
    }

    public File Compress(String command) throws Exception {
        File f = File.createTempFile("apr", "zip");
        FileOutputStream fos = new FileOutputStream(f);
        OutputStream os = fos;
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(os));
        try {
            String filename = new UtilHelper().genString() + ".txt";
            ZipEntry entry = new ZipEntry(filename);
            zos.putNextEntry(entry);
            ComunicationJson comunicationJson = new ComunicationJson();
            comunicationJson.setCommand(command);
            comunicationJson.setTimestamp("");
            comunicationJson.setCommand(command);

            comunicationJson.setVersion("99");
            comunicationJson.setVersionSo("99");
            comunicationJson.setToken("");

            String text = new Gson().toJson(comunicationJson);

            // String base64 = Base64.encodeToString(appendedPassword.getBytes("UTF-8"), Base64.DEFAULT);
            final byte[] bytes = text.getBytes();

            zos.write(bytes);
            zos.closeEntry();

        } finally {
            zos.close();
        }
        return f;
    }

    public String takenumCache(String num) {
        return num.substring(0, num.length() - 6);

    }

}
