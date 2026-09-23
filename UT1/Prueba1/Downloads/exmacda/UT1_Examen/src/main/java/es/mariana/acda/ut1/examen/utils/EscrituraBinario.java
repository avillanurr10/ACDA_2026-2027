package es.mariana.acda.ut1.examen.utils;

import es.mariana.acda.ut1.examen.models.Persona;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class EscrituraBinario {

    public static void escribirBinarios(List<Persona> personas) throws IOException {
        // TODO START crea un fichero binario para cada persona con el contenido adecuado "id.bin"
        for (Persona persona : personas) {
            try (RandomAccessFile raf = new RandomAccessFile(
                    new File("src\\main\\resources\\out\\"+ persona.getId() +".bin"), "rw")) {
                raf.writeUTF(persona.getNombre());
                raf.writeInt(persona.getEdad());
                raf.writeDouble(persona.getAltitudMaxima());
                raf.writeDouble(persona.getAltitudMinima());
                double tiempoTotal = persona.getTiempoTotalEnHoras();
                double distanciaTotal = persona.getDistanciaTotal();
                raf.writeDouble(tiempoTotal);
                raf.writeDouble(distanciaTotal);
                raf.writeDouble(distanciaTotal / tiempoTotal);
            }
        }
        // TODO END
    }
}
