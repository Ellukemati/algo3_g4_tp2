package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Vertice;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class VistaVertice extends Group {
    private final Vertice vertice;
    private final Circle forma;
    private final double radio = 8; // Tamaño del vértice

    public VistaVertice(Vertice vertice, double x, double y) {
        this.vertice = vertice;

        // 1. Dibujar el punto del vértice
        this.forma = new Circle(radio);
        this.forma.setCenterX(x);
        this.forma.setCenterY(y);

        actualizarVisualizacion();

        // Evento de click (para construir)
        this.forma.setOnMouseClicked(e -> {
            System.out.println("Click en Vértice ID: " + vertice.getId());
            // aca llamar al controlador
        });

        this.getChildren().add(forma);

        // 2. Dibujar Barco/Puerto si existe (El "punto negro")
        if (vertice.obtenerPuerto() != null) {
            Rectangle barco = new Rectangle(10, 10);
            barco.setX(x + 5); // Un poco desplazado
            barco.setY(y - 5);
            barco.setFill(Color.BLACK);
            this.getChildren().add(barco);
        }
    }

    public void actualizarVisualizacion() {
        if (vertice.verificarOcupado()) {
            this.forma.setFill(Color.RED); // Asumimos rojo por ahora
            this.forma.setStroke(Color.DARKRED);
        } else {
            this.forma.setFill(Color.TRANSPARENT);
            this.forma.setStroke(Color.GRAY);
            this.forma.setStrokeWidth(2);
        }
    }
}