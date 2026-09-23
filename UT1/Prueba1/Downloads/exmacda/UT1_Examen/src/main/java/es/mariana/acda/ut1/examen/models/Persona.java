package es.mariana.acda.ut1.examen.models;

import es.mariana.acda.ut1.examen.calculations.Calculations;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Persona {
    private int id;
    private String nombre;
    private int edad;
    private List<Ruta> rutas = new ArrayList<>();
    private double altitudMaxima = 0;
    private double altitudMinima = 10000;

    // Constructor
    public Persona(int id, String nombre, int edad, JSONArray rutas) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;

        // TODO START parsear las rutas de JSONArray al List<Ruta> rutas
        for (int i = 0; i < rutas.length(); i++) {
            this.rutas.add(new Ruta(
                    rutas.getJSONArray(i)
            ));
        }
        // TODO END

        calcAltitudMaximaYMinima();
    }

    public void calcAltitudMaximaYMinima() {
        // TODO START busca la altitud máxima en la que haya estado esta persona
        for (Ruta ruta : rutas) {
            for (Checkpoint checkpoint : ruta.getCheckpoints()) {
                if (checkpoint.getElevacion() > altitudMaxima)
                    altitudMaxima = checkpoint.getElevacion();
                if (checkpoint.getElevacion() < altitudMinima)
                    altitudMinima = checkpoint.getElevacion();
            }
        }
        // TODO END
    }

    public double getDistanciaTotal() {
        double distanciaTotal = 0;
        // TODO START calcula la distancia total recorrida por la persona
        //         Utiliza la clase Calculations con el método getDistanciaEntreCheckpoints
        for (Ruta ruta : rutas) {
            Checkpoint checkpointAnterior = null;
            for (Checkpoint checkpointActual : ruta.getCheckpoints()) {
                if (checkpointAnterior != null)
                    distanciaTotal += Calculations.getDistanciaEntreCheckpoints(
                            checkpointAnterior,
                            checkpointActual);
                checkpointAnterior = checkpointActual;
            }
        }
        //TODO END
        return distanciaTotal;
    }

    public double getTiempoTotalEnHoras() {
        double tiempoTotalEnHoras = 0;
        // TODO START calcula el tiempo total de recorrido por la persona
        //          Utiliza la clase Calculations con el método getDiferenciaTimestampsEnHoras
        for (Ruta ruta : rutas) {
            Checkpoint checkpointAnterior = null;
            for (Checkpoint checkpointActual : ruta.getCheckpoints()) {
                if (checkpointAnterior != null)
                    tiempoTotalEnHoras += Calculations.getDiferenciaTimestampsEnHoras(
                            checkpointAnterior.getTimestamp(),
                            checkpointActual.getTimestamp());
                checkpointAnterior = checkpointActual;
            }
        }
        // TODO END
        return tiempoTotalEnHoras;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public List<Ruta> getRutas() {
        return rutas;
    }

    public double getAltitudMaxima() {
        return altitudMaxima;
    }

    public double getAltitudMinima() {
        return altitudMinima;
    }
}
