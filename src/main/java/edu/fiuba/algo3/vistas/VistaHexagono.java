package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Hexagono;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class VistaHexagono extends StackPane {
    private final Hexagono hexagono;
    private final double radio;

    public VistaHexagono(Hexagono hexagono, double radio) {
        this.hexagono = hexagono;
        this.radio = radio;

        this.dibujarForma();
        this.dibujarFichaNumero();
    }

    private void dibujarForma() {
        Polygon forma = new Polygon();
        // Crear los 6 puntos del hexágono
        for (int i = 0; i < 6; i++) {
            double angulo = Math.toRadians(60 * i + 30); // +30 para que quede plano arriba/abajo
            double x = radio * Math.cos(angulo);
            double y = radio * Math.sin(angulo);
            forma.getPoints().addAll(x, y);
        }

        // Obtener color según el recurso del modelo
        forma.setFill(MapaDeColores.obtenerColor(hexagono.obtenerRecurso()));
        forma.setStroke(Color.BLACK);
        forma.setStrokeWidth(2);

        this.getChildren().add(forma);
    }

    private void dibujarFichaNumero() {
        int numero = hexagono.getNumero();

        // El desierto tiene número 0, no mostramos ficha
        if (numero != 0) {
            Circle fichaFondo = new Circle(radio * 0.4);
            fichaFondo.setFill(Color.BEIGE);
            fichaFondo.setStroke(Color.BLACK);

            Text textoNumero = new Text(String.valueOf(numero));
            textoNumero.setFont(Font.font("Verdana", 14));


            this.getChildren().addAll(fichaFondo, textoNumero);
        }
    }
}