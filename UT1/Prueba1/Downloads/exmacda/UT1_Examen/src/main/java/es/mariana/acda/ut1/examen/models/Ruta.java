package es.mariana.acda.ut1.examen.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Ruta {
    private List<Checkpoint> checkpoints = new ArrayList<>();

    // Constructor y getter
    public Ruta(JSONArray ruta) {
        // TODO START parsea la ruta de un JSONArray a una lista de checkpoints
        for (int i = 0; i < ruta.length(); i++) {
            JSONObject checkpoint = ruta.getJSONObject(i);
            checkpoints.add(new Checkpoint(
                    checkpoint.getDouble("latitud"),
                    checkpoint.getDouble("longitud"),
                    checkpoint.getString("timestamp"),
                    checkpoint.getDouble("elevacion")
            ));
        }
        // TODO END
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }
}

