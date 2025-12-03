package edu.fiuba.algo3.vistas; // Ojo: chequeá si tu carpeta se llama 'vista' o 'vistas'

import edu.fiuba.algo3.modelo.Tablero;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;


/**
 * JavaFX App - Punto de entrada del Catan
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        Tablero modelo = new Tablero();

        VistaTablero vistaCentral = new VistaTablero(modelo);

        StackPane raiz = new StackPane(vistaCentral);

        stage.setTitle("AlgoCatan - TP2");
        VistaCartaDeRecursos recursos = new VistaCartaDeRecursos();

        HBox hBox = recursos.inicializarVistaCarta();
        BorderPane root = new BorderPane();

        root.setCenter(raiz);

        root.setBottom(hBox);
        var scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}