import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeerJSONConBuffer {
    public static void main(String[] args) {
        // Abrimos el archivo coches.json usando try-with-resources
        try (BufferedReader br = new BufferedReader(new FileReader("coches.json"))) {
            String linea;

            System.out.println("--- LEYENDO COCHES DESDE JSON CON BUFFEREDREADER ---");

            while ((linea = br.readLine()) != null) {
                linea = linea.trim(); // Quitamos espacios en blanco y tabulaciones

                // Cada coche empieza con una llave de apertura en el array
                if (linea.equals("{")) {
                    System.out.println("\n--- COCHE ENCONTRADO ---");
                }
                // Buscamos las claves y extraemos sus valores manualmente
                else if (linea.contains("\"id\":")) {
                    System.out.println("ID: " + extraerValor(linea));
                }
                else if (linea.contains("\"marca\":")) {
                    System.out.println("Marca: " + extraerValor(linea));
                }
                else if (linea.contains("\"modelo\":")) {
                    System.out.println("Modelo: " + extraerValor(linea));
                }
                else if (linea.contains("\"precio\":")) {
                    System.out.println("Precio: " + extraerValor(linea));
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }
    }

    // Método auxiliar para limpiar la línea y quedarnos solo con el dato
    private static String extraerValor(String linea) {
        // Dividimos la línea por los dos puntos ":"
        String[] partes = linea.split(":");
        if (partes.length < 2) return "";

        // Tomamos la parte del valor, quitamos comas, comillas y espacios sueltos
        return partes[1]
                .replace("\"", "")
                .replace(",", "")
                .trim();
    }
}
