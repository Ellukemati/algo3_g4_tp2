package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.CartaDesarollo;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class BotonCartaDesarolloVista implements CartaDesarolloRenderizador{

    private BackgroundImage fondo;
    private Button botonGenerado;

    public Button getBoton() {
        return botonGenerado;
    }

    private void configurarBoton(Button boton) {
        //boton.setPrefSize(60, 60);
        boton.setMinSize(30, 30);
        boton.setMaxSize(50, 50);
        //boton.setBackground(new Background(fondo));
        HBox.setMargin(boton, new Insets(3));
    }

    private void armarFondo(Button boton, String ruta) {
        Image img = new Image(getClass().getResource(ruta).toExternalForm());

        BackgroundImage backgroundImage = new BackgroundImage(
                img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(
                        BackgroundSize.AUTO,
                        BackgroundSize.AUTO,
                        false, false, true, true
                )
        );
        boton.setBackground(new Background(backgroundImage));

    }

    public BotonCartaDesarolloVista() {
        Image img = new Image(getClass().getResource("/recursos/fondo_violeta.jpg").toExternalForm());

        this.fondo = new BackgroundImage(
                img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(
                        BackgroundSize.AUTO,
                        BackgroundSize.AUTO,
                        false, false, true, true
                )
        );
    }

    @Override
    public Button caballeroRenderizar(CartaDesarollo cartaDesarollo) {
        Button botonCarta = new Button();
        configurarBoton(botonCarta);
        armarFondo(botonCarta, "/recursos/caballero.jpg");
        this.botonGenerado = botonCarta;
        return botonCarta;
    }

    @Override
    public Button contruccionDeCarreterasRenderizar(CartaDesarollo cartaDesarollo) {
        Button botonCarta = new Button();
        configurarBoton(botonCarta);
        armarFondo(botonCarta, "/recursos/construccion.jpg");
        this.botonGenerado = botonCarta;
        return botonCarta;
    }

    @Override
    public Button descubrimientoRenderizar(CartaDesarollo cartaDesarollo) {
        Button botonCarta = new Button();
        configurarBoton(botonCarta);
        armarFondo(botonCarta, "/recursos/descubrimiento.jpg");
        this.botonGenerado = botonCarta;
        return botonCarta;
    }

    @Override
    public Button monopolioRenderizar(CartaDesarollo cartaDesarollo) {
        Button botonCarta = new Button();
        configurarBoton(botonCarta);
        armarFondo(botonCarta, "/recursos/monopolio.jpg");
        this.botonGenerado = botonCarta;
        return botonCarta;
    }

    @Override
    public Button puntoDeVictoriaRenderizar(CartaDesarollo cartaDesarollo) {
        Button botonCarta = new Button();
        configurarBoton(botonCarta);
        armarFondo(botonCarta, "/recursos/pv.jpg");
        this.botonGenerado = botonCarta;
        return botonCarta;
    }
}
