package edu.fiuba.algo3.vistas;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.image.Image;

import java.util.List;

public class VistaCartaDeRecursos {

    public HBox inicializarVistaCarta() {

        List<String> imagenes = List.of(
                "/ladrillo.png",
                "/lana.png",
                "/mineral.png",
                "/madera.png",
                "/trigo.png"
        );

        HBox contenedor = new HBox();
        aplicarFondo(contenedor);

        imagenes.forEach(ruta -> {
            VistaCartaRecurso carta = new VistaCartaRecurso(ruta, "0");
            contenedor.getChildren().add(carta);
        });

        contenedor.setMinHeight(50);
        contenedor.setMaxWidth(140);

        BorderPane.setMargin(contenedor, new Insets(0, 0, 0, 30));

        return contenedor;
    }

    private void aplicarFondo(HBox contenedor) {
        Image img = new Image(getClass().getResource("/fondo_cremita.jpg").toExternalForm());

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
}

