package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class VistaCartaDeRecursos {

    private List<VistaCartaRecurso> cartas;
    public HBox inicializarVistaCarta() {
        this.cartas = new ArrayList<>();
        List<String> imagenes = List.of(
                "/recursos/ladrillo.png",
                "/recursos/lana.png",
                "/recursos/mineral.png",
                "/recursos/madera.png",
                "/recursos/trigo.png"
        );

        HBox contenedor = new HBox();
        aplicarFondo(contenedor);

        imagenes.forEach(ruta -> {
            VistaCartaRecurso carta = new VistaCartaRecurso(ruta, "0");
            contenedor.getChildren().add(carta);
            cartas.add(carta);
        });

        contenedor.setMinHeight(50);
        contenedor.setMaxHeight(50);
        contenedor.setMaxWidth(100);

        StackPane.setAlignment(contenedor, Pos.BOTTOM_LEFT);
        StackPane.setMargin(contenedor, new Insets(0, 0, 0, 30));


        return contenedor;
    }

    private void aplicarFondo(HBox contenedor) {
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
    }

    public void actualizarRecursos(Jugador jugador) {

        cartas.get(0).actualizarValor(jugador.cantidadDe(Recurso.LADRILLO));
        cartas.get(1).actualizarValor(jugador.cantidadDe(Recurso.LANA));
        cartas.get(2).actualizarValor(jugador.cantidadDe(Recurso.MINERAL));
        cartas.get(3).actualizarValor(jugador.cantidadDe(Recurso.MADERA));
        cartas.get(4).actualizarValor(jugador.cantidadDe(Recurso.GRANO));
    }
}

