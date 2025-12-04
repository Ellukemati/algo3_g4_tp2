package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Arista;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import edu.fiuba.algo3.modelo.Observador;

public class VistaArista extends Line implements Observador {
    private final Arista arista;

    public VistaArista(Arista arista, double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
        this.arista = arista;
        this.arista.agregarObservador(this);
        this.setStrokeWidth(4);
        actualizarVisualizacion();


    }
    @Override
    public void actualizar() {
        actualizarVisualizacion();
    }
    public void actualizarVisualizacion() {
        if (arista.verificarOcupado()) {
            this.setStroke(Color.BLUE); // Se pinta sola cuando el modelo cambia
            this.setOpacity(1.0);
        } else {
            this.setStroke(Color.LIGHTGRAY);
            this.setOpacity(0.5);
        }
    }
}