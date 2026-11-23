package com.centro;


import com.centro.connection.ConexionBD;
import com.centro.dao.MotoDAO;
import com.centro.model.Moto;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        MotoDAO motoDAO = new MotoDAO();

        try (Scanner scanner = new Scanner(System.in)) {
            int opcion;
            do {
                mostrarMenu();
                opcion = leerEntero(scanner, "Elige una opción: ");

                switch (opcion) {
                    case 1 -> insertarMoto(scanner, motoDAO);
                    case 2 -> listarMotos(motoDAO);
                    case 3 -> actualizarPrecioMoto(scanner, motoDAO);
                    case 4 -> eliminarMoto(scanner, motoDAO);
                    case 0 -> {
                        ConexionBD.cerrarConexion();
                        System.out.println("Saliendo del programa...");
                    }
                    default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            } while (opcion != 0);
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n=== MENÚ MOTOS (CRUD + SINGLETON) ===");
        System.out.println("1. Insertar moto");
        System.out.println("2. Listar todas las motos");
        System.out.println("3. Actualizar precio de una moto (por ID)");
        System.out.println("4. Eliminar una moto (por ID)");
        System.out.println("0. Salir");
    }

    private static void insertarMoto(Scanner scanner, MotoDAO motoDAO) {
        System.out.print("Marca: ");
        String marca = scanner.nextLine().trim();

        System.out.print("Modelo: ");
        String modelo = scanner.nextLine().trim();

        int cilindrada = leerEntero(scanner, "Cilindrada (cc): ");
        double precio = leerDouble(scanner, "Precio (€): ");

        Moto moto = new Moto(marca, modelo.isEmpty() ? null : modelo, cilindrada, precio);

        try {
            int filas = motoDAO.insertar(moto);
            System.out.println("Moto insertada correctamente. Filas afectadas: " + filas);
        } catch (SQLException e) {
            System.err.println("Error al insertar la moto: " + e.getMessage());
        }
    }

    private static void listarMotos(MotoDAO motoDAO) {
        try {
            List<Moto> motos = motoDAO.listarTodas();
            System.out.println("\n--- LISTADO DE MOTOS ---");
            if (motos.isEmpty()) {
                System.out.println("No hay motos en la base de datos.");
            } else {
                motos.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar las motos: " + e.getMessage());
        }
    }

    private static void actualizarPrecioMoto(Scanner scanner, MotoDAO motoDAO) {
        int id = leerEntero(scanner, "ID de la moto a actualizar: ");
        double nuevoPrecio = leerDouble(scanner, "Nuevo precio (€): ");

        try {
            int filas = motoDAO.actualizarPrecio(id, nuevoPrecio);
            if (filas > 0) {
                System.out.println("Precio actualizado correctamente.");
            } else {
                System.out.println("No se encontró ninguna moto con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el precio: " + e.getMessage());
        }
    }

    private static void eliminarMoto(Scanner scanner, MotoDAO motoDAO) {
        int id = leerEntero(scanner, "ID de la moto a eliminar: ");

        try {
            int filas = motoDAO.eliminar(id);
            if (filas > 0) {
                System.out.println("Moto eliminada correctamente.");
            } else {
                System.out.println("No se encontró ninguna moto con ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar la moto: " + e.getMessage());
        }
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número entero válido.");
            }
        }
    }

    private static double leerDouble(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número decimal válido.");
            }
        }
    }
}