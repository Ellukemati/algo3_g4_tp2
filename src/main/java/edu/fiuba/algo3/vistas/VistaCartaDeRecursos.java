package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class VistaCartaDeRecursos {

    private Map<Recurso, VistaCartaRecurso> mapaVistas;

    public HBox inicializarVistaCarta() {
        this.mapaVistas = new HashMap<>();
        HBox contenedor = new HBox();
        aplicarFondo(contenedor);

        agregarCarta(contenedor, Recurso.LADRILLO, "/recursos/ladrillo.png");
        agregarCarta(contenedor, Recurso.LANA, "/recursos/lana.png");
        agregarCarta(contenedor, Recurso.MINERAL, "/recursos/mineral.png");
        agregarCarta(contenedor, Recurso.MADERA, "/recursos/madera.png");
        agregarCarta(contenedor, Recurso.GRANO, "/recursos/trigo.png");

        contenedor.setMinHeight(50);
        contenedor.setMaxHeight(50);
        contenedor.setMaxWidth(100);

        StackPane.setAlignment(contenedor, Pos.BOTTOM_LEFT);
        StackPane.setMargin(contenedor, new Insets(0, 0, 0, 30));

        return contenedor;
    }

    private void agregarCarta(HBox contenedor, Recurso recurso, String rutaImagen) {
        VistaCartaRecurso carta = new VistaCartaRecurso(rutaImagen, "0");
        contenedor.getChildren().add(carta);

        mapaVistas.put(recurso, carta);
    }

    private void aplicarFondo(HBox contenedor) {
        try {
            Image img = new Image(getClass().getResource("/recursos/fondo_cremita.jpg").toExternalForm());
            BackgroundImage bg = new BackgroundImage(
                    img,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(
                            BackgroundSize.AUTO, BackgroundSize.AUTO,
                            false, false, true, true
                    )
            );
            contenedor.setBackground(new Background(bg));
        } catch (Exception e) {
            System.out.println("No se pudo cargar el fondo de recursos");
        }
    }

    public void actualizar(Jugador jugador) {
        if (jugador == null) return;

        for (Recurso r : Recurso.values()) {
            VistaCartaRecurso carta = mapaVistas.get(r);
            if (carta != null) {
                carta.actualizarValor(jugador.cantidadDe(r));
            }
        }
    }
}