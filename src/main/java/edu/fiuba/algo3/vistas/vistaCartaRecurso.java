package edu.fiuba.algo3.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
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

public class vistaCartaRecurso extends StackPane {
    private Label label;

    public vistaCartaRecurso(String dirrecionImagen, String cantidad) {
        this.label = new Label(cantidad);
        Rectangle rect = new Rectangle(80, 130);
        //rect.heightProperty().bind(hBox.heightProperty());
        // stilo
        label.setFont(Font.font("Orbitron", FontWeight.BOLD, 20));
        label.setTranslateX(-5);
        StackPane stackPane = new StackPane(rect, label);
        Image img = new Image(getClass().getResource(dirrecionImagen).toExternalForm());
        rect.setFill(new ImagePattern(img));

        StackPane.setAlignment(label, Pos.TOP_RIGHT);
        //hBox.getChildren().add(stackPane);
        HBox.setMargin(stackPane, new Insets(5, 5, 5, 5));


        //stackPane.setMaxWidth(Region.USE_PREF_SIZE);
        //stackPane.setMaxHeight(Region.USE_PREF_SIZE);
        HBox.setHgrow(stackPane, Priority.NEVER);
    }

    public void setTexto(String texto) {
        this.label.setText(texto);
    }
}
