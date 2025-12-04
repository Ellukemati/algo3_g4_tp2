package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Hexagono;
import edu.fiuba.algo3.modelo.Recurso;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class VistaHexagono extends StackPane {
    private final Hexagono hexagono;
    private final double radio;
    private final ImageView imagenLadron;
    private final Button btnMover; // NUEVO: El botón para mover
    private boolean tieneLadron = false;

    private static final Map<Recurso, String> imagenesRecursos = new HashMap<>();
    private static Image imagenLadronGlobal;

    static {
        imagenesRecursos.put(Recurso.LADRILLO, "/recursos/ladrillo.png");
        imagenesRecursos.put(Recurso.LANA, "/recursos/lana.png");
        imagenesRecursos.put(Recurso.MINERAL, "/recursos/mineral.png");
        imagenesRecursos.put(Recurso.MADERA, "/recursos/madera.png");
        imagenesRecursos.put(Recurso.GRANO, "/recursos/trigo.png");

        try {
            imagenLadronGlobal = new Image(VistaHexagono.class.getResourceAsStream("/imagenes/ladron.png"));
        } catch (Exception e) {
            System.out.println("No se pudo cargar la imagen del ladrón.");
            imagenLadronGlobal = null;
        }
    }

    public VistaHexagono(Hexagono hexagono, double radio) {
        this.hexagono = hexagono;
        this.radio = radio;

        this.imagenLadron = crearImagenLadron();

        // --- CONFIGURACIÓN DEL BOTÓN ---
        this.btnMover = new Button("ROBAR AQUÍ");
        this.btnMover.setVisible(false); // Oculto por defecto
        // Estilo rojo semi-transparente para que se note
        this.btnMover.setStyle("-fx-background-color: rgba(255, 0, 0, 0.7); -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 10px;");
        this.btnMover.setMaxSize(80, 30);

        this.dibujarForma();
        this.dibujarFichaNumero();

        this.getChildren().add(imagenLadron);
        this.getChildren().add(btnMover); // Agregamos el botón al StackPane

        verificarLadronInicial();
    }

    public void mostrarBotonMover(Consumer<Hexagono> accion) {
        if (tieneLadron) return;

        btnMover.setVisible(true);
        btnMover.toFront();

        btnMover.setOnAction(e -> {
            System.out.println("Click en botón del hexágono: " + hexagono.getNumero());
            accion.accept(this.hexagono);
        });
    }

    public void ocultarBotonMover() {
        btnMover.setVisible(false);
    }

    private ImageView crearImagenLadron() {
        ImageView imageView;

        if (imagenLadronGlobal != null) {
            imageView = new ImageView(imagenLadronGlobal);
            double tamano = radio * 0.7;
            imageView.setFitWidth(tamano);
            imageView.setFitHeight(tamano);
        } else {
            Circle circuloLadron = new Circle(radio * 0.3);
            circuloLadron.setFill(Color.RED);
            circuloLadron.setStroke(Color.BLACK);
            circuloLadron.setStrokeWidth(2);
            imageView = new ImageView();
        }
        imageView.setVisible(false);
        return imageView;
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
            } catch (Exception e) { }
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
            fichaFondo.setFill(Color.rgb(255, 255, 255, 0.7));
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

    private void verificarLadronInicial() {
        if (hexagono.getNumero() == 0) {
            mostrarLadron(true);
        }
    }

    public void mostrarLadron(boolean mostrar) {
        this.tieneLadron = mostrar;
        imagenLadron.setVisible(mostrar);
        if (mostrar) imagenLadron.toFront();
    }

    public boolean tieneLadron() {
        return this.tieneLadron;
    }

    public Hexagono getHexagono() {
        return hexagono;
    }
}