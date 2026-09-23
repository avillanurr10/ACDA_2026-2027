import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce la ruta del fichero o directorio: ");
        File f = new File(scanner.nextLine());
        scanner.close();

        if (!f.exists()) {
            System.out.println("No existe.");
            return;
        }

        if (f.isFile()) {
            System.out.println("Fichero: " + f.getName() + " | Tamaño: " + f.length() + "B");
            System.out.println("Lectura: " + f.canRead() + " | Escritura: " + f.canWrite() + " | Ejecución: " + f.canExecute());
        } else if (f.isDirectory()) {
            System.out.println("Directorio: " + f.getName() + " | Tamaño total: " + calcularTam(f) + "B");
            mostrarContenido(f, "  ");
        }
    }

    private static long calcularTam(File dir) {
        long tam = 0;
        File[] ficheros = dir.listFiles();
        if (ficheros != null) {
            for (File f : ficheros) {
                if (f.isDirectory()) {
                    tam += calcularTam(f);
                } else {
                    tam += f.length();
                }
            }
        }
        return tam;
    }

    private static void mostrarContenido(File dir, String tab) {
        File[] ficheros = dir.listFiles();
        if (ficheros != null) {
            for (File f : ficheros) {
                if (f.isDirectory()) {
                    System.out.println(tab + "[DIR] " + f.getName());
                    mostrarContenido(f, tab + "  ");
                } else {
                    System.out.println(tab + "[FILE] " + f.getName() + " (" + f.length() + "B)");
                }
            }
        }
    }
}