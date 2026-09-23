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
                String anio = elementoCoche.getElementsByTagName("año").item(0).getTextContent();

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

    // OPCIÓN 2: Pedir datos por consola e insertar el nuevo coche en el XML
    private static void crearCoche(Scanner scanner) throws Exception {
        File archivo = new File(ARCHIVO_XML);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc;
        Element raiz;

        // Si el archivo ya existe, lo cargamos. Si no, creamos uno nuevo con su nodo raíz.
        if (archivo.exists()) {
            doc = dBuilder.parse(archivo);
            raiz = doc.getDocumentElement();
        } else {
            doc = dBuilder.newDocument();
            raiz = doc.createElement("concesionario");
            doc.appendChild(raiz);
        }

        System.out.println("\n--- INTRODUCIR DATOS DEL NUEVO COCHE ---");

        System.out.print("ID del coche: ");
        String id = scanner.nextLine();

        System.out.print("¿Está disponible? (true/false): ");
        String disponible = scanner.nextLine();

        System.out.print("Marca: ");
        String marca = scanner.nextLine();

        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();

        System.out.print("Año: ");
        String anio = scanner.nextLine();

        System.out.print("Precio: ");
        String precio = scanner.nextLine();

        System.out.print("Moneda (ej. EUR, USD): ");
        String moneda = scanner.nextLine();

        // Crear la estructura de nodos del nuevo coche
        Element nuevoCoche = doc.createElement("coche");
        nuevoCoche.setAttribute("id", id);
        nuevoCoche.setAttribute("disponible", disponible);

        Element elMarca = doc.createElement("marca");
        elMarca.appendChild(doc.createTextNode(marca));
        nuevoCoche.appendChild(elMarca);

        Element elModelo = doc.createElement("modelo");
        elModelo.appendChild(doc.createTextNode(modelo));
        nuevoCoche.appendChild(elModelo);

        Element elAnio = doc.createElement("año");
        elAnio.appendChild(doc.createTextNode(anio));
        nuevoCoche.appendChild(elAnio);

        Element elPrecio = doc.createElement("precio");
        elPrecio.setAttribute("moneda", moneda);
        elPrecio.appendChild(doc.createTextNode(precio));
        nuevoCoche.appendChild(elPrecio);

        // Añadir el nuevo coche a la raíz
        raiz.appendChild(nuevoCoche);

        // Guardar el documento actualizado de vuelta en el archivo físico
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // Configuración para que el XML de salida esté bien tabulado/identado
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://apache.org}indent-amount", "4");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(archivo);
        transformer.transform(source, result);

        System.out.println("\n¡Coche guardado correctamente en el archivo XML!");
    }
}
