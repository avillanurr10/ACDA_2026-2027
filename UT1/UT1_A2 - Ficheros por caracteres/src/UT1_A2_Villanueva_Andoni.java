import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class UT1_A2_Villanueva_Andoni {
    public static void main(String[] args) throws IOException {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Escribe el nombre del fichero absoluta o relativa, (Quitale las comillas si coges la absoluta)");
        String ficheroNombre = teclado.nextLine();

        File file = new File(ficheroNombre);

        if (!file.exists() || !file.isFile()){
            System.out.println("La ruta que has escrito no existe o no corresponde");
            return;

            }
        cuentaVocales(file);
    }

    private static void cuentaVocales(File file) throws IOException {
        int contador = 0;


        try (FileReader fileReader = new FileReader(file)) {
            int caracter;
            while ((caracter = fileReader.read()) != -1) {
                if (Vocal((char) caracter)) {
                    contador++;
                }
            }
        }
        System.out.println("Total vocales: " + contador);
    }


    private static boolean Vocal(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }
}
