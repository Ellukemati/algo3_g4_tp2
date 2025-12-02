package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Arista;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;

public class VistaArista extends Line {
    private final Arista arista;

    public VistaArista(Arista arista, double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
        this.arista = arista;

        this.setStrokeWidth(4);
        actualizarVisualizacion();

        this.setOnMouseClicked(e -> {
            System.out.println("Click en Arista");
        });
    }

    public void actualizarVisualizacion() {
        if (arista.verificarOcupado()) {
            this.setStroke(Color.BLUE); // Carretera construida
        } else {
            this.setStroke(Color.LIGHTGRAY); // Camino disponible (vacío)
            this.setOpacity(0.5); // trasparente
        }
    }
}