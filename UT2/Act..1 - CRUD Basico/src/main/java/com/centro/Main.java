package com.centro;

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
                    case 3 -> actualizarPrecio(scanner, motoDAO);
                    case 4 -> eliminarMoto(scanner, motoDAO);
                    case 0 -> System.out.println("Saliendo del programa...");
                    default -> System.out.println("Opción no válida.");
                }
            } while (opcion != 0);
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- GESTIÓN DE MOTOS (CRUD) ---");
        System.out.println("1. Insertar nueva moto");
        System.out.println("2. Mostrar todas las motos");
        System.out.println("3. Actualizar precio por ID");
        System.out.println("4. Eliminar moto por ID");
        System.out.println("0. Salir");
    }

    private static void insertarMoto(Scanner scanner, MotoDAO motoDAO) {
        System.out.print("Marca: ");
        String marca = scanner.nextLine().trim();

        System.out.print("Modelo: ");
        String modelo = scanner.nextLine().trim();

        int cilindrada = leerEntero(scanner, "Cilindrada (cc): ");
        double precio = leerDouble(scanner, "Precio (€): ");

        Moto nueva = new Moto(marca, modelo.isEmpty() ? null : modelo, cilindrada, precio);

        try {
            int filas = motoDAO.insertar(nueva);
            System.out.println("Moto insertada correctamente. Filas afectadas: " + filas);
        } catch (SQLException e) {
            System.err.println("Error al insertar moto: " + e.getMessage());
        }
    }

    private static void listarMotos(MotoDAO motoDAO) {
        try {
            List<Moto> motos = motoDAO.listarTodas();
            System.out.println("\n--- LISTADO DE MOTOS ---");
            if (motos.isEmpty()) {
                System.out.println("No hay motos registradas.");
            } else {
                motos.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar motos: " + e.getMessage());
        }
    }

    private static void actualizarPrecio(Scanner scanner, MotoDAO motoDAO) {
        int id = leerEntero(scanner, "ID de la moto a actualizar: ");
        double nuevoPrecio = leerDouble(scanner, "Nuevo precio (€): ");

        try {
            int filas = motoDAO.actualizarPrecio(id, nuevoPrecio);
            if (filas > 0) {
                System.out.println("Precio actualizado correctamente.");
            } else {
                System.out.println("No existe ninguna moto con el ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar precio: " + e.getMessage());
        }
    }

    private static void eliminarMoto(Scanner scanner, MotoDAO motoDAO) {
        int id = leerEntero(scanner, "ID de la moto a eliminar: ");

        try {
            int filas = motoDAO.eliminarPorId(id);
            if (filas > 0) {
                System.out.println("Moto eliminada correctamente.");
            } else {
                System.out.println("No existe ninguna moto con el ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar moto: " + e.getMessage());
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