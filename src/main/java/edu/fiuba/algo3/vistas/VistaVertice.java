package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Vertice;
import edu.fiuba.algo3.modelo.Jugador; // Importante
import edu.fiuba.algo3.modelo.Observador;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class VistaVertice extends Group implements Observador {
    private final Vertice vertice;
    private final Circle forma;
    private final Text textoDuennio; // Cambiamos textoId por textoDueño
    private final double radio = 10;

    public VistaVertice(Vertice vertice, double x, double y) {
        this.vertice = vertice;
        this.vertice.agregarObservador(this);

        // 1. Círculo principal
        this.forma = new Circle(radio);
        this.forma.setCenterX(x);
        this.forma.setCenterY(y);

        // Debug Click
        this.forma.setOnMouseClicked(e -> {
            System.out.println("Click en Vértice ID: " + vertice.getId());
        });

        this.getChildren().add(forma);

        // 2. Puerto (si tiene)
        if (vertice.obtenerPuerto() != null) {
            Rectangle barco = new Rectangle(10, 10);
            barco.setX(x + 8);
            barco.setY(y - 8);
            barco.setFill(Color.BLACK);
            this.getChildren().add(barco);
        }

        textoDuennio = new Text("");
        textoDuennio.setX(x - 4); // Ajuste centrado
        textoDuennio.setY(y + 4);
        textoDuennio.setFill(Color.WHITE); // Blanco para contrastar con Rojo
        textoDuennio.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        textoDuennio.setMouseTransparent(true);

        this.getChildren().add(textoDuennio);

        actualizarVisualizacion();
    }


    @Override
    public void actualizar() {
        actualizarVisualizacion();
    }

    private void actualizarVisualizacion() {
        if (vertice.tieneEdificio()) {
            this.forma.setFill(Color.RED);
            this.forma.setStroke(Color.DARKRED);

            Jugador dueño = vertice.obtenerDueño();
            if (dueño != null) {
                String nombre = dueño.obtenerNombre();
                String etiqueta = nombre.length() > 0 ? nombre.substring(nombre.length() - 1) : "?";
                textoDuennio.setText(etiqueta);
                textoDuennio.setVisible(true);
            }
        }
        else if (vertice.verificarOcupado()) {
            this.forma.setFill(Color.BLACK);
            this.forma.setStroke(Color.BLACK);
            textoDuennio.setVisible(false);
        }
        else {
            this.forma.setFill(Color.TRANSPARENT);
            this.forma.setStroke(Color.GRAY);
            textoDuennio.setVisible(false);
        }
    }
}