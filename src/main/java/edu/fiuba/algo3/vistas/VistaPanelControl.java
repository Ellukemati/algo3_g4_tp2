package edu.fiuba.algo3.vistas;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class VistaPanelControl extends HBox {

    private final Label lblDado;
    private final Button btnPasarTurno;
    private final Button btnComprarCarta;

    public VistaPanelControl() {
        super(10); // Espaciado entre elementos (spacing)
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: rgba(255,255,255,0.7); -fx-background-radius: 10; -fx-padding: 10;");
        this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        lblDado = new Label("Dado: -");
        lblDado.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        btnPasarTurno = new Button("Pasar Turno");
        btnPasarTurno.setStyle("-fx-font-size: 16px; -fx-base: #87CEEB; -fx-cursor: hand;");

        btnComprarCarta = new Button("Comprar carta");
        this.getChildren().addAll(lblDado, btnPasarTurno, btnComprarCarta);
    }

    public void actualizarDado(int valor) {
        lblDado.setText("Dado: " + valor);
    }

    public void setAccionPasarTurno(Runnable accion) {
        btnPasarTurno.setOnAction(e -> accion.run());
    }

    public void setAccionComprarCarta(Runnable accion) {
        btnComprarCarta.setOnAction(e -> accion.run());
    }
}