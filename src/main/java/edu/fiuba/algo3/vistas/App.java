package edu.fiuba.algo3.vistas; // Ojo: chequeá si tu carpeta se llama 'vista' o 'vistas'

import edu.fiuba.algo3.controllers.IntercambioController;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Tablero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Tablero modelo = new Tablero();
        Jugador jugador = new Jugador();
        VistaTablero vistaCentral = new VistaTablero(modelo, jugador);
        StackPane raiz = new StackPane(vistaCentral);
        Jugador jugadorActivo = crearJugadorPrueba(modelo);

        VistaTablero vistaTablero = new VistaTablero(modelo,jugador);

        // Carga del Panel de Intercambio
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/intercambio.fxml"));
        Region panelIntercambio = loader.load();
        configurarPanelIntercambio(panelIntercambio, loader.getController(), jugadorActivo);

        raiz.getChildren().addAll(vistaTablero, panelIntercambio);

        Button btnAbrirComercio = crearBotonAbrirComercio(panelIntercambio);
        raiz.getChildren().add(btnAbrirComercio);
        StackPane.setAlignment(btnAbrirComercio, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(btnAbrirComercio, new Insets(10));

        Scene scene = new Scene(raiz, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/estilos/intercambio.css").toExternalForm());

        stage.setTitle("AlgoCatan - TP2");
        stage.setScene(scene);
        stage.show();
    }

    // JUGADOR SIMULADO PARA PRUEBAS, HAY QUE IMPLEMENTAR LOS TURNOS Y EL JUGADOR ACTIVO
    private Jugador crearJugadorPrueba(Tablero modelo) {
        Jugador j = new Jugador();
        j.agregarRecurso(Recurso.MADERA, 10);
        j.agregarRecurso(Recurso.LADRILLO, 5);
        j.agregarRecurso(Recurso.LANA, 3);
        j.agregarRecurso(Recurso.GRANO, 2);
        j.construirPoblado(modelo, 1);
        return j;
    }

    private void configurarPanelIntercambio(Region panel, IntercambioController controlador, Jugador jugador) {
        controlador.setJugador(jugador);
        panel.setMaxSize(650, 400);
        panel.setVisible(false);
    }

    private Button crearBotonAbrirComercio(Region panelObjetivo) {
        Button btn = new Button("Comerciar");
        btn.getStyleClass().add("boton-comerciar");
        btn.setOnAction(e -> {
            panelObjetivo.setVisible(true);
            panelObjetivo.toFront();
        });
        return btn;
    }

    public static void main(String[] args) {
        launch();
    }
}