package com.maker.utils;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Esta clase sirve para arrancar el script en python. En el futuro la refactorizaré usando jython.
 */
public class PythonRunnable {
    private final ProcessBuilder processBuilder;

    /**
     * El constructor crea un proceso con la ruta y el archivo del script.
     * @param filePath Es la ruta del script.
     * @param file Es el archivo.
     */
    public PythonRunnable(String filePath, String file) {
        processBuilder = new ProcessBuilder("python", filePath+"/"+file);
        processBuilder.directory(new File(filePath));

        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
    }

    /**
     * Este metodo sirve para iniciar el script que descarga los archivos, deberia moverlo a una clase especifica, i know.
     * @param labNumber Es el numero del laboratorio para el cual se ejecutará el script.
     * @param uri Es la direccion web para la cual se ejecutara el script (por defecto o especifica).
     */
    public void download(String labNumber, String uri) {
        try {
            List<String> routeUserCred = Arrays.stream(Routes.pathToUserCredentials.getPath().split("/")).toList();

            processBuilder.command().add(labNumber);
            processBuilder.command().add(uri);
            processBuilder.command().add(routeUserCred.get(routeUserCred.size()-1));


            Process process = processBuilder.start();

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Python script has execute successfully.");
            } else {
                System.out.println("Failed to execute python script." + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
