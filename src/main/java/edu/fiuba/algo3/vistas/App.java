package edu.fiuba.algo3.vistas; // Ojo: chequeá si tu carpeta se llama 'vista' o 'vistas'

import edu.fiuba.algo3.modelo.Tablero;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App - Punto de entrada del Catan
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Tablero modelo = new Tablero();

        VistaTablero vistaCentral = new VistaTablero(modelo);

        StackPane raiz = new StackPane(vistaCentral);

        Scene scene = new Scene(raiz, 800, 600);

        stage.setTitle("AlgoCatan - TP2");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}