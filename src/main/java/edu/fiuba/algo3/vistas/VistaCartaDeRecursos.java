package edu.fiuba.algo3.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.beans.binding.Bindings;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;


public class VistaCartaDeRecursos {
    private int madera;
    private int lana;
    private int trigo;



    private void crearCarta(String dirrecionImagen, Label label, HBox hBox) {
        Rectangle rect = new Rectangle(80, 130);
        //rect.heightProperty().bind(hBox.heightProperty());
        // stilo
        label.setFont(Font.font("Orbitron", FontWeight.BOLD, 20));
        label.setTranslateX(-5);
        StackPane stackPane = new StackPane(rect, label);
        Image img = new Image(getClass().getResource(dirrecionImagen).toExternalForm());
        rect.setFill(new ImagePattern(img));

        StackPane.setAlignment(label, Pos.TOP_RIGHT);
        hBox.getChildren().add(stackPane);
        HBox.setMargin(stackPane, new Insets(5, 5, 5, 5));


        //stackPane.setMaxWidth(Region.USE_PREF_SIZE);
        //stackPane.setMaxHeight(Region.USE_PREF_SIZE);
        HBox.setHgrow(stackPane, Priority.NEVER);

    }

    public HBox inicializarVistaCarta() {
        List<Rectangle> rectangles = List.of(new Rectangle(120,100), new Rectangle(120,100),
                new Rectangle(120,100), new Rectangle(120,100), new Rectangle(120,100));
        List<Label> labels = List.of(new Label("0"), new Label("0"), new Label("0"), new Label("0"),
                new Label("0"));

        List<String> dirreccionCarta = List.of("/ladrillo.png", "/lana.png", "/mineral.png", "/madera.png", "/trigo.png");
        HBox hb1 = new HBox();

        Image img = new Image(getClass().getResource("/fondo_cremita.jpg").toExternalForm());

        BackgroundImage bg = new BackgroundImage(
                img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );

        hb1.setBackground(new Background(bg));

        for (int i = 0; i < labels.size(); i++) {
            crearCarta(dirreccionCarta.get(i), labels.get(i), hb1);
        }

        //hb1.setSpacing(10);
        //crearCarta(dirreccionCarta.get(0), labels.get(0), hb1);

        /*
        Rectangle rect = new Rectangle(120, 100);
        rect.heightProperty().bind(hb1.heightProperty());
        rect.setFill(Color.BLUE);
        Label label = new Label("0");
        // stilo
        label.setFont(Font.font("Orbitron", FontWeight.BOLD, 20));
        label.setTranslateX(-5);

        StackPane stackPane = new StackPane(rect, label);
        Image img = new Image(getClass().getResource("/ladrillo.png").toExternalForm());
        rect.setFill(new ImagePattern(img));

        StackPane.setAlignment(label, Pos.TOP_RIGHT);
        hb1.getChildren().add(stackPane);


        Rectangle rect2 = new Rectangle(120, 100);
        rect2.heightProperty().bind(hb1.heightProperty());
        rect2.setFill(Color.LIGHTGRAY);
        Label label2 = new Label("0");
        StackPane stackPane2 = new StackPane(rect2, label2);

        StackPane.setAlignment(label2, Pos.TOP_RIGHT);
        hb1.getChildren().add(stackPane2);
        */

        /*hb1.getChildren().addAll(new Button("ejemplo"));*/
        hb1.setPrefHeight(150);
        return hb1;
    }
}
