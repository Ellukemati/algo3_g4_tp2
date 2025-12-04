package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.CartaDesarollo;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class VistaCartaDeRecursos {

    private List<VistaCartaRecurso> cartas;
    private List<Button> botones;
    private HBox contenedor;

    public VistaCartaDeRecursos() {
        this.botones = new ArrayList<>();
    }

    public HBox inicializarVistaCarta() {
        this.cartas = new ArrayList<>();
        List<String> imagenes = List.of(
                "/recursos/ladrillo.png",
                "/recursos/lana.png",
                "/recursos/mineral.png",
                "/recursos/madera.png",
                "/recursos/trigo.png"
        );

        this.contenedor = new HBox();
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

    public void actulizarCartaDesarollo(Jugador jugador) {
        BotonCartaDesarolloVista renderizador = new BotonCartaDesarolloVista();
        List<CartaDesarollo> cartasDesarollo = jugador.obtenerCartasDeDesarollo();
        for (Button boton : botones) {
            contenedor.getChildren().remove(boton);
        }
        for (CartaDesarollo carta : cartasDesarollo) {

            carta.mostrar(renderizador);
            Button boton = renderizador.getBoton();
            // aca iria la implemenentacion de cada boton
//            boton.setOnAction(e -> {
//                System.out.println("implemente el evenHandler");
//            });
            contenedor.getChildren().add(boton);
            botones.add(boton);
        }

    }
}

