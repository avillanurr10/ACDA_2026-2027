package es.mariana.acda.ut1.examen;

import es.mariana.acda.ut1.examen.models.Persona;
import es.mariana.acda.ut1.examen.utils.EscrituraBinario;
import es.mariana.acda.ut1.examen.utils.LecturaJSON;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {



    public static void main(String[] args) {
        // TODO START define la ruta relativa al fichero datos.json contenido en el directorio src/main/resources
        String rutaJSON = "src\\main\\resources\\datos.json";
        // TODO END

        // TODO START maneja el error avisando de qué en la lectura mediante un Sistem.out
        //          y termina la ejecución del programa
        JSONObject jsonObject = null;
        try {
            jsonObject = LecturaJSON.lecturaJSON(rutaJSON);
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error durante la lectura del fichero: " + rutaJSON);
            return;
        }
        // TODO END

        // TODO START transforma el jsonObject a la lista de Personas
        List<Persona> personas = new ArrayList<>();
        JSONArray personasJSONArray = jsonObject.getJSONArray("personas");
        for (int i = 0; i < personasJSONArray.length(); i++) {
            JSONObject personaJSON = personasJSONArray.getJSONObject(i);
            personas.add(new Persona(
                    personaJSON.getInt("id"),
                    personaJSON.getString("nombre"),
                    personaJSON.getInt("edad"),
                    personaJSON.getJSONArray("rutas")
            ));
        }
        // TODO END

        // TODO START maneja el error avisando de de qué en la escritura mediante un Sistem.out
        try {
            EscrituraBinario.escribirBinarios(personas);
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error durante la creación de los ficheros binarios");
        }
        // TODO END
    }
}
