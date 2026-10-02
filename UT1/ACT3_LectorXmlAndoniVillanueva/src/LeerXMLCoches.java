import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Scanner;

public class LeerXMLCoches {
    private static final String ARCHIVO_XML = "coches.xml";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n=== MENÚ CONCESIONARIO ===");
            System.out.println("1. Ver el XML (Listar coches)");
            System.out.println("2. Crear coche nuevo");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        mostrarXML();
                        break;
                    case 2:
                        crearCoche(scanner);
                        break;
                    case 3:
                        System.out.println("¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduzca un número válido.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        } while (opcion != 3);

        scanner.close();
    }

    // OPCIÓN 1: Leer y mostrar el contenido del XML
    private static void mostrarXML() throws Exception {
        File archivo = new File(ARCHIVO_XML);
        if (!archivo.exists()) {
            System.out.println("El archivo " + ARCHIVO_XML + " no existe todavía. Crea un coche primero.");
            return;
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(archivo);
        doc.getDocumentElement().normalize();

        NodeList listaCoches = doc.getElementsByTagName("coche");
        System.out.println("\n--- LISTADO DE COCHES EN EL CONCESIONARIO ---");

        for (int i = 0; i < listaCoches.getLength(); i++) {
            Node nodo = listaCoches.item(i);

            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elementoCoche = (Element) nodo;

                String id = elementoCoche.getAttribute("id");
                String disponible = elementoCoche.getAttribute("disponible");
                String marca = elementoCoche.getElementsByTagName("marca").item(0).getTextContent();
                String modelo = elementoCoche.getElementsByTagName("modelo").item(0).getTextContent();

                // Corregido: Evita fallos si un coche antiguo no tiene año cargado
                Node nodoAnio = elementoCoche.getElementsByTagName("año").item(0);
                String anio = (nodoAnio != null) ? nodoAnio.getTextContent() : "N/A";

                Element elementoPrecio = (Element) elementoCoche.getElementsByTagName("precio").item(0);
                String precio = elementoPrecio.getTextContent();
                String moneda = elementoPrecio.getAttribute("moneda");

                System.out.println("\nID Coche: " + id + " (Disponible: " + disponible + ")");
                System.out.println("• Marca: " + marca);
                System.out.println("• Modelo: " + modelo);
                System.out.println("• Año: " + anio);
                System.out.println("• Precio: " + precio + " " + moneda);
                System.out.println("-------------------------------------------");
            }
        }
    }

    // OPCIÓN 2: Pedir datos por consola de forma fácil
    private static void crearCoche(Scanner scanner) throws Exception {
        File archivo = new File(ARCHIVO_XML);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc;
        Element raiz;

        // Comprobación ultra-simple para que no falle si borras el archivo .xml por error
        if (archivo.exists()) {
            doc = dBuilder.parse(archivo);
            raiz = doc.getDocumentElement();
        } else {
            doc = dBuilder.newDocument();
            raiz = doc.createElement("concesionario");
            doc.appendChild(raiz);
        }

        // Pedir solo los datos clave
        System.out.println("\n--- INTRODUCIR DATOS DEL NUEVO COCHE ---");
        System.out.print("ID del coche: ");      String id = scanner.nextLine();
        System.out.print("Marca: ");             String marca = scanner.nextLine();
        System.out.print("Modelo: ");            String modelo = scanner.nextLine();
        System.out.print("Precio: ");            String precio = scanner.nextLine();

        // Estructura XML acoplada con el "mostrarXML" para evitar errores de invocación
        Element nuevoCoche = doc.createElement("coche");
        nuevoCoche.setAttribute("id", id);
        nuevoCoche.setAttribute("disponible", "si");

        Element elMarca = doc.createElement("marca");
        elMarca.setTextContent(marca);
        nuevoCoche.appendChild(elMarca);

        Element elModelo = doc.createElement("modelo");
        elModelo.setTextContent(modelo);
        nuevoCoche.appendChild(elModelo);

        Element elAnio = doc.createElement("año");
        elAnio.setTextContent("2024"); // Lo autocompletamos para que 'mostrarXML' no falle
        nuevoCoche.appendChild(elAnio);

        Element elPrecio = doc.createElement("precio");
        elPrecio.setAttribute("moneda", "EUR");
        elPrecio.setTextContent(precio);
        nuevoCoche.appendChild(elPrecio);

        raiz.appendChild(nuevoCoche);

        // Guardar cambios
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(doc), new StreamResult(archivo));

        System.out.println("\n¡Coche guardado correctamente!");
    }
}
