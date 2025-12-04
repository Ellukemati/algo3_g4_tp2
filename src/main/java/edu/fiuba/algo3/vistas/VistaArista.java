package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Arista;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Observador;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;

public class VistaArista extends Line implements Observador {
    private final Arista arista;

    private final Color[] coloresJugadores = {
            Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE
    };

    public VistaArista(Arista arista, double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
        this.arista = arista;
        this.arista.agregarObservador(this);

        this.setStrokeWidth(6);
        this.setStroke(Color.LIGHTGRAY);
        this.setOpacity(0.3);

        actualizarVisualizacion();
    }

    @Override
    public void actualizar() {
        actualizarVisualizacion();
    }

    public void actualizarVisualizacion() {
        if (arista.verificarOcupado()) {
            Jugador duennio = arista.obtenerDueño();
            if (duennio != null) {

                int indiceColor = Math.abs(duennio.hashCode()) % coloresJugadores.length;

                this.setStroke(coloresJugadores[indiceColor]);
                this.setOpacity(1.0);
            }
        } else {
            this.setStroke(Color.LIGHTGRAY);
            this.setOpacity(0.3);
        }
    }
}