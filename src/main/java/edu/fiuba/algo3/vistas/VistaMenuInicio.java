package edu.fiuba.algo3.vistas;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.function.Consumer;

public class VistaMenuInicio extends VBox {

    public VistaMenuInicio(Consumer<Integer> manejadorInicio) {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setStyle("-fx-background-color: #f0f8ff; -fx-padding: 20;");

        Label titulo = new Label("AlgoCatan");
        titulo.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        titulo.setStyle("-fx-text-fill: #2c3e50;");



        Label lblInstruccion = new Label("Seleccione la cantidad de jugadores:");
        lblInstruccion.setFont(Font.font("Arial", 16));

        ChoiceBox<Integer> selectorCantidad = new ChoiceBox<>();
        selectorCantidad.getItems().addAll(3, 4);
        selectorCantidad.setValue(3); // Valor por defecto
        selectorCantidad.setStyle("-fx-font-size: 14px;");

        Button btnJugar = new Button("COMENZAR AVENTURA");
        btnJugar.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-size: 18px; -fx-cursor: hand;");

        btnJugar.setOnMouseEntered(e -> btnJugar.setStyle("-fx-background-color: #d35400; -fx-text-fill: white; -fx-font-size: 18px;"));
        btnJugar.setOnMouseExited(e -> btnJugar.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-size: 18px;"));

        btnJugar.setOnAction(e -> {
            int cantidadSeleccionada = selectorCantidad.getValue();
            manejadorInicio.accept(cantidadSeleccionada);
        });

        this.getChildren().addAll(titulo, lblInstruccion, selectorCantidad, btnJugar);
    }
}