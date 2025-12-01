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
        // Esto dispara tu InicializadorTablero y crea hexágonos, vértices, etc.
        Tablero modelo = new Tablero();

        // 2. Creamos la Vista Principal
        // (Le pasamos el modelo para que sepa qué dibujar)
        VistaTablero vistaCentral = new VistaTablero(modelo);

        // Usamos un StackPane o BorderPane como raíz visual
        StackPane raiz = new StackPane(vistaCentral);

        // 3. Configuramos la Escena (El lienzo)
        // Tamaño sugerido: 800x600 o maximizado
        Scene scene = new Scene(raiz, 800, 640);

        stage.setTitle("AlgoCatan - TP2");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}