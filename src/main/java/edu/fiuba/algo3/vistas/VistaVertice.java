package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Vertice;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.FontWeight;
import edu.fiuba.algo3.modelo.Observador;
import javafx.scene.paint.Color;

public class VistaVertice extends Group implements Observador {
    private final Vertice vertice;
    private final Circle forma;
    private final double radio = 10;

    public VistaVertice(Vertice vertice, double x, double y) {
        this.vertice = vertice;
        this.vertice.agregarObservador(this);
        // 1. Dibujar el punto del vértice
        this.forma = new Circle(radio);
        this.forma.setCenterX(x);
        this.forma.setCenterY(y);

        actualizarVisualizacion();

        this.forma.setOnMouseClicked(e -> {
            System.out.println("Click en Vértice ID: " + vertice.getId());
        });

        this.getChildren().add(forma);

        if (vertice.obtenerPuerto() != null) {
            Rectangle barco = new Rectangle(10, 10);
            barco.setX(x + 8);
            barco.setY(y - 8);
            barco.setFill(Color.BLACK);
            this.getChildren().add(barco);
        }

        Text textoId = new Text(String.valueOf(vertice.getId()));
        textoId.setX(x - 6); // Ajuste para centrar aprox
        textoId.setY(y + 4);
        textoId.setFill(Color.BLUE);
        textoId.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        textoId.setMouseTransparent(true);

        this.getChildren().add(textoId);
        // -------------------------
    }
    public void iluminar() {
        this.forma.setFill(Color.YELLOW);
        this.forma.setStroke(Color.ORANGE);
        this.forma.setStrokeWidth(4);
    }
    public void desiluminar() {
        actualizarVisualizacion();
    }
    @Override
    public void actualizar() {
        actualizarVisualizacion();
    }
    private void actualizarVisualizacion() {
        if (vertice.verificarOcupado()) {
            this.forma.setFill(Color.RED);
        } else {
            this.forma.setFill(Color.TRANSPARENT);
            this.forma.setStroke(Color.GRAY);
        }
    }
}