package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.controllers.CatanController;
import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Jugador;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // 1. Crear modelo
        Catan juego = new Catan();

        // Agregar jugadores de ejemplo
        // Necesitarás agregar constructor con nombre a Jugador
        juego.agregarJugador(new Jugador("Jugador 1"));
        juego.agregarJugador(new Jugador("Jugador 2"));

        // 2. Crear controlador
        CatanController controlador = new CatanController(juego);

        // 3. Crear vistas
        VistaTablero vistaCentral = new VistaTablero(juego.obtenerTablero());
        VistaRecursosJugador vistaRecursos = new VistaRecursosJugador(controlador);
        BotonTerminarTurno botonTerminarTurno = new BotonTerminarTurno(controlador, vistaRecursos);

        // 4. Configurar layout principal
        BorderPane raiz = new BorderPane();

        // Centro: Tablero
        raiz.setCenter(vistaCentral);

        // Esquina inferior izquierda: Recursos
        StackPane recursosContainer = new StackPane(vistaRecursos);
        recursosContainer.setAlignment(Pos.BOTTOM_LEFT);
        raiz.setBottom(recursosContainer);
        BorderPane.setAlignment(recursosContainer, Pos.BOTTOM_LEFT);

        // Lado derecho: Botón de terminar turno
        HBox panelDerecho = new HBox(botonTerminarTurno);
        panelDerecho.setAlignment(Pos.CENTER_RIGHT);
        panelDerecho.setStyle("-fx-padding: 20;");
        raiz.setRight(panelDerecho);

        // 5. Escena y stage
        Scene scene = new Scene(raiz, 1200, 800); // Aumentar tamaño para mejor visualización

        stage.setTitle("AlgoCatan - TP2");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}