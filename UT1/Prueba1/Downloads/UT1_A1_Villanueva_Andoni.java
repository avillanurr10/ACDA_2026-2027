import java.io.File;

public class UT1_A1_Villanueva_Andoni {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No hay argumentos");
            return; // Detiene la ejecución aquí si no hay argumentos
        }

        System.out.println("Argumentos " + args[0]);

        String dir = args[0]; // Directorio o fichero a comprobar
        File f = new File(dir); // Creo objeto File

        if (!f.exists()) {
            System.out.println("El fichero o directorio no existe."); // Compruebo si existe
        } else if (f.isFile()) { // Compruebo si es fichero
            System.out.println("Es un fichero");
            System.out.println("Nombre: " + f.getName());
            System.out.println("Tamaño: " + f.length() + " bytes");
        } else if (f.isDirectory()) { // Si es directorio
            System.out.println("Es un directorio.");
            System.out.println("Nombre:" + f.getName());
            String[] contenido = f.list(); // Lista de ficheros y directorios
            long tamanyoTotal = 0; // Acumulador del tamaño

            if (contenido != null) { // Compruebo que no sea null
                System.out.println("Contenido:");
                for (String nombre : contenido) { // Recorro el array de nombres
                    System.out.println("  " + nombre);
                    File elemento = new File(f, nombre); // Creo objeto File para cada elemento
                    if (elemento.isFile()) { // Si es fichero
                        tamanyoTotal += elemento.length(); // Sumo tamaño
                    }
                }
            }
            System.out.println("Tamaño total del directorio (solo si hay ficheros dentro): " + tamanyoTotal + " bytes");
        }
    }
}