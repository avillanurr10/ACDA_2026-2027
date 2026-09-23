package es.mariana.acda.ut1.examen.utils;

import org.json.JSONObject;

import java.io.*;

public class LecturaJSON {

    public static JSONObject lecturaJSON(String rutaFichero) throws IOException {
        String contenidoFichero = lecturaFichero(rutaFichero);

        // TODO START parsear para devolver un JSONObject
        JSONObject jsonObject = new JSONObject(contenidoFichero);
        // TODO END
        return jsonObject;
    }

    private static String lecturaFichero(String rutaFichero) throws IOException {
        StringBuilder contenidoFichero = new StringBuilder();

        // TODO START lee el fichero que recibe por parámetro, eliminando las líneas con comentarios (empiezan por //)
        try (BufferedReader br = new BufferedReader(new FileReader(rutaFichero))) {
            while (br.ready()) {
                String line = br.readLine();
                if (!line.startsWith("//"))
                    contenidoFichero.append(line);
            }
        }
        // TODO END

        return contenidoFichero.toString();
    }
}
