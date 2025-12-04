package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Hexagono;
import edu.fiuba.algo3.modelo.Recurso;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class VistaHexagono extends StackPane {
    private final Hexagono hexagono;
    private final double radio;

    private static final Map<Recurso, String> imagenesRecursos = new HashMap<>();

    static {
        imagenesRecursos.put(Recurso.LADRILLO, "/recursos/ladrillo.png");
        imagenesRecursos.put(Recurso.LANA, "/recursos/lana.png");
        imagenesRecursos.put(Recurso.MINERAL, "/recursos/mineral.png");
        imagenesRecursos.put(Recurso.MADERA, "/recursos/madera.png");
        imagenesRecursos.put(Recurso.GRANO, "/recursos/trigo.png");
    }

    public VistaHexagono(Hexagono hexagono, double radio) {
        this.hexagono = hexagono;
        this.radio = radio;

        this.dibujarForma();
        this.dibujarFichaNumero();
    }

    private void dibujarForma() {
        Polygon forma = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angulo = Math.toRadians(60 * i + 30);
            double x = radio * Math.cos(angulo);
            double y = radio * Math.sin(angulo);
            forma.getPoints().addAll(x, y);
        }

        Recurso recurso = hexagono.obtenerRecurso();
        boolean imagenCargada = false;

        if (recurso != null && imagenesRecursos.containsKey(recurso)) {
            try {
                String ruta = imagenesRecursos.get(recurso);
                Image img = new Image(getClass().getResource(ruta).toExternalForm());
                forma.setFill(new ImagePattern(img));
                imagenCargada = true;
            } catch (Exception e) {
                System.out.println("No se pudo cargar imagen para el hexágono: " + recurso);
            }
        }

        if (!imagenCargada) {
            forma.setFill(MapaDeColores.obtenerColor(recurso));
        }

        forma.setStroke(Color.BLACK);
        forma.setStrokeWidth(2);

        this.getChildren().add(forma);
    }

    private void dibujarFichaNumero() {
        int numero = hexagono.getNumero();

        if (numero != 0) {
            Circle fichaFondo = new Circle(radio * 0.4);
            fichaFondo.setFill(Color.TRANSPARENT);
            fichaFondo.setStroke(Color.BLACK);

            Text textoNumero = new Text(String.valueOf(numero));
            textoNumero.setFont(Font.font("Verdana", 14));

            if (numero == 6 || numero == 8) {
                textoNumero.setFill(Color.RED);
            } else {
                textoNumero.setFill(Color.BLACK);
            }

            this.getChildren().addAll(fichaFondo, textoNumero);
        }
    }
}