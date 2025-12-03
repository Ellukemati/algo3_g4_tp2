package edu.fiuba.algo3.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class VistaCartaRecurso extends StackPane {

    private final Rectangle rectangulo;
    private final Label label;

    public VistaCartaRecurso(String rutaImagen, String valorInicial) {

        this.rectangulo = new Rectangle(27, 47);
        this.label = new Label(valorInicial);

        configurarLabel();
        configurarImagen(rutaImagen);

        this.getChildren().addAll(rectangulo, label);
        StackPane.setAlignment(label, Pos.TOP_RIGHT);
        HBox.setMargin(this, new Insets(3));
    }

    private void configurarLabel() {
        label.setFont(Font.font("Orbitron", FontWeight.BOLD, 15));
        label.setTranslateX(-5);
    }

    private void configurarImagen(String rutaImagen) {
        Image img = new Image(getClass().getResource(rutaImagen).toExternalForm());
        rectangulo.setFill(new ImagePattern(img));
    }

    public void actualizarValor(int nuevoValor) {
        label.setText(String.valueOf(nuevoValor));
    }
}