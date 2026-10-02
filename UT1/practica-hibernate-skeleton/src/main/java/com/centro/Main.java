package com.centro;

import com.centro.modelo.Alumno;
import com.centro.modelo.Modulo;
import com.centro.modelo.Profesor;
import com.centro.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("\n=== INICIO DE LA PRÁCTICA HIBERNATE (ENTORNO: MYSQL) ===");

        try {
            // =========================================================================
            // TAREA 1: GUARDAR PROFESOR, MÓDULOS Y ALUMNOS (PERSIST / CASCADE)
            // =========================================================================
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                session.beginTransaction();

                System.out.println("\n---> TAREA 1: Creando Profesor, Módulos y Alumnos...");
                Profesor prof = new Profesor("Carlos Ruiz", "carlos.ruiz@centro.com");
                Profesor prof1 = new Profesor("Paulina María Cuello", "pcuelloote@educacion.navarra.es");
                Profesor prof2 = new Profesor("Eduardo Moreno", "emoreno84@educacion.mavarra.es");

                Modulo accDatos = new Modulo("Acceso a Datos", 5);
                Modulo psp = new Modulo("Procesos y Servicios", 3);
                Modulo pmdm = new Modulo("Programación Multimedia", 4);
                Modulo dweb = new Modulo("Desarrollo Web", 3);
                Modulo sgem = new Modulo("Sistemas de gestión Empresarial", 4);
                Modulo ing = new Modulo("Inglés profesional", 2);
                Modulo dein = new Modulo("Desarrollo de interfaces", 6);

                // Relación Profesor - Módulos
                prof1.addModulo(accDatos);
                prof.addModulo(psp);
                prof.addModulo(pmdm);
                prof1.addModulo(dweb);
                prof2.addModulo(sgem);
                prof2.addModulo(dein);
                prof.addModulo(ing);

                // Creación de Alumnos
                Alumno al1 = new Alumno("Juan Pérez", "EXP001", LocalDate.of(2002, 5, 15));
                Alumno al2 = new Alumno("María López", "EXP002", LocalDate.of(2003, 9, 22));
                Alumno al3 = new Alumno("Lucas Gómez", "EXP003", LocalDate.of(2001, 12, 10));

                // Establecer matrículas en Módulos (Relación Muchos a Muchos)
                al1.addModulo(accDatos);
                al1.addModulo(pmdm);

                al2.addModulo(accDatos);
                al2.addModulo(dein);

                al3.addModulo(pmdm);
                al3.addModulo(sgem);

                // Asignación de Tutor (Relación Uno a Uno)
                al1.setTutor(prof1);
                al2.setTutor(prof2);

                // Sincronización automática por CascadeType.ALL en profesores
                session.persist(prof);
                session.persist(prof1);
                session.persist(prof2);

                // Persistencia manual de las nuevas entidades Alumno
                session.persist(al1);
                session.persist(al2);
                session.persist(al3);

                session.getTransaction().commit();
                System.out.println("¡Profesores, módulos y alumnos guardados con éxito!");
            }

            // =========================================================================
            // TAREA 2: CONSULTA HQL (SELECT FILTRADO)
            // =========================================================================
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                System.out.println("\n---> TAREA 2: Consulta HQL (Módulos con horas > 3)...");

                List<Modulo> modulosHoras = session.createQuery(
                                "FROM Modulo m WHERE m.horasSemanales > :horas", Modulo.class)
                        .setParameter("horas", 3)
                        .getResultList();

                for (Modulo m : modulosHoras) {
                    System.out.println(" - Módulo: " + m.getNombre() + " (" + m.getHorasSemanales() + "h/semana)");
                }
            }

            // =========================================================================
            // TAREA 2.5: PRUEBA DE CONSULTA HQL DE ALUMNOS POR MÓDULO
            // =========================================================================
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                System.out.println("\n---> TAREA 2.5: Consulta HQL (Alumnos por módulo específico)...");

                String nombreModulo = "Acceso a Datos";

                List<Alumno> alumnosModulo = session.createQuery(
                                "SELECT a FROM Alumno a JOIN a.modulos m WHERE m.nombre = :nombreMod", Alumno.class)
                        .setParameter("nombreMod", nombreModulo)
                        .getResultList();

                System.out.println("Alumnos inscritos en '" + nombreModulo + "':");
                for (Alumno al : alumnosModulo) {
                    System.out.println(" - [Expediente: " + al.getExpediente() + "] " + al.getNombre());
                }
            }

            // =========================================================================
            // TAREA 3: ACTUALIZACIÓN AUTOMÁTICA POR DIRTY CHECKING (UPDATE)
            // =========================================================================
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                session.beginTransaction();

                System.out.println("\n---> TAREA 3: Actualizando datos...");
                Profesor p = session.get(Profesor.class, 1L);
                if (p != null) {
                    p.setEmail("carlos.ruiz@nuevo.com");
                    System.out.println("Email actualizado a: " + p.getEmail());
                }

                session.getTransaction().commit();
            }

            // =========================================================================
            // TAREA 4: ELIMINACIÓN DE UN MÓDULO (CON INTEGRIDAD REFERENCIAL ASEGURADA)
            // =========================================================================
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                session.beginTransaction();

                System.out.println("\n---> TAREA 4: Eliminando módulo...");
                Modulo moduloAEliminar = session.get(Modulo.class, 2L);

                if (moduloAEliminar != null) {
                    // Eliminación previa de referencias cruzadas en la tabla relacional intermedia
                    session.createNativeQuery("DELETE FROM alumnos_modulos WHERE modulo_id = :id")
                            .setParameter("id", moduloAEliminar.getId())
                            .executeUpdate();

                    session.remove(moduloAEliminar);
                    System.out.println("Módulo '" + moduloAEliminar.getNombre() + "' eliminado con éxito.");
                }

                session.getTransaction().commit();
            }

            // =========================================================================
            // PAUSA DE EJECUCIÓN PARA REVISAR EL ESQUEMA EN WORKBENCH
            // =========================================================================
            System.out.println("\n=======================================================");
            System.out.println("🚀 MIGRACIÓN COMPLETA. PERSISTENCIA EN DISCO ACTIVA.");
            System.out.println("1. Abre MySQL Workbench (Puerto 3308).");
            System.out.println("2. Revisa el esquema 'centro_db_mysql'.");
            System.out.println("3. Presiona ENTER en esta consola para finalizar.");
            System.out.println("=======================================================\n");

            System.in.read();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
            System.out.println("Fábrica de sesiones de Hibernate cerrada de forma segura.");
        }
    }
}
