package edu.fiuba.algo3.vistas;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ContruccionesVista extends HBox {
    private Button poblado;
    private Button muro;
    private Button ciudad;

    public ContruccionesVista(){
        Image img = new Image(getClass().getResource("/fondo_celeste.jpg").toExternalForm());

        BackgroundImage bg = new BackgroundImage(
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

        poblado = new Button("Poblado");
        poblado.setPrefSize(110, 110);
        poblado.setMinSize(80, 80);
        poblado.setMaxSize(135, 135);

        poblado.setBackground(new Background(bg));

        muro = new Button("Muro");
        muro.setPrefSize(110, 110);
        muro.setMinSize(80, 80);
        muro.setMaxSize(135, 135);
        muro.setBackground(new Background(bg));

        ciudad = new Button("Ciudad");
        ciudad.setPrefSize(110, 110);
        ciudad.setMinSize(80, 80);
        ciudad.setMaxSize(135, 135);
        ciudad.setBackground(new Background(bg));


        this.setSpacing(10);
        this.getChildren().add(poblado);
        this.getChildren().add(muro);
        this.getChildren().add(ciudad);
    }
}
