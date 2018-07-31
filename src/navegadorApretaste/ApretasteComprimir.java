/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package navegadorApretaste;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author David24
 */
public class ApretasteComprimir {

    private static List<String> fileList = new ArrayList< String>();
    private static final String OUTPUT_ZIP_FILE = "page.zip";

    public static void comprimirPagina(String file, String sourceFile) {
        generateFileList(new File(file), sourceFile);
        zipIt(OUTPUT_ZIP_FILE, file);
    }

    public static void zipIt(String zipFile, String sourceFile) {
        byte[] buffer = new byte[1024];
        String source = new File(sourceFile).getName();
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);

            System.err.println("Output to Zip : " + zipFile + "\n");
            FileInputStream in = null;

            for (String file : ApretasteComprimir.fileList) {
                System.err.println("File Added : " + file + "\n");
                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(sourceFile + File.separator + file);
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                } finally {
                    in.close();
                }
            }

            zos.closeEntry();
            System.out.println("Folder successfully compressed");

        } catch (IOException ex) {
            System.err.println(ex.getMessage() + "\n");
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
                System.err.println(e.getMessage() + "\n");
            }
        }
    }

    public static void generateFileList(File node, String sourceFile) {
        // add file only
        if (node.isFile()) {
            fileList.add(generateZipEntry(node.toString(), sourceFile));
        }

        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename : subNote) {
                generateFileList(new File(node, filename), sourceFile);
            }
        }
    }

    private static String generateZipEntry(String file, String sourceFile) {
        return file.substring(sourceFile.length() + 1, file.length());
    }
}
