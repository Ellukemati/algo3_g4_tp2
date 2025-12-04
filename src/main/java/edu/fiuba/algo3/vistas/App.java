package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.controllers.IntercambioController;
import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;

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

    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("AlgoCatan - TP2");

        // Iniciamos mostrando el menú
        mostrarMenuInicio();

        stage.show();
    }

    private void mostrarMenuInicio() {
        // Pasamos una expresión lambda que dice qué hacer cuando se elija la cantidad
        VistaMenuInicio vistaMenu = new VistaMenuInicio(cantidad -> {
            try {
                iniciarJuego(cantidad);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Scene scene = new Scene(vistaMenu, 800, 600);
        stage.setScene(scene);
    }

    private void iniciarJuego(int cantidadJugadores) throws IOException {
        // 1. Instanciamos la Facade
        Catan juego = new Catan();

        for (int i = 1; i <= cantidadJugadores; i++) {
            Jugador nuevoJugador = new Jugador();

            // --- RECURSOS DE PRUEBA (SOLO PARA DEBUG) ---
            // Le damos suficiente para 2 caminos, 1 poblado y 1 ciudad
            nuevoJugador.agregarRecurso(Recurso.MADERA, 5);
            nuevoJugador.agregarRecurso(Recurso.LADRILLO, 5);
            nuevoJugador.agregarRecurso(Recurso.LANA, 5);
            nuevoJugador.agregarRecurso(Recurso.GRANO, 5);
            nuevoJugador.agregarRecurso(Recurso.MINERAL, 5);
            // ---------------------------------------------

            juego.agregarJugador(nuevoJugador);
        }

        // 3. Creamos la vista del tablero
        VistaTablero vistaTablero = new VistaTablero(juego);
        StackPane raiz = new StackPane(vistaTablero);

        // 4. Carga del Panel de Intercambio
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/intercambio.fxml"));
        Region panelIntercambio = loader.load();

        IntercambioController intercambioController = loader.getController();
        configurarPanelIntercambio(panelIntercambio, intercambioController, juego);

        // Observer: Actualizar controlador cuando cambia el turno
        juego.agregarObservador(() -> {
            intercambioController.setJugador(juego.obtenerJugadorActual());
        });

        raiz.getChildren().add(panelIntercambio);

        // Botones de UI
        Button btnAbrirComercio = crearBotonAbrirComercio(panelIntercambio);
        raiz.getChildren().add(btnAbrirComercio);
        StackPane.setAlignment(btnAbrirComercio, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(btnAbrirComercio, new Insets(10));

        Button btnPasarTurno = new Button("Pasar Turno");
        btnPasarTurno.setOnAction(e -> juego.siguienteTurno());
        raiz.getChildren().add(btnPasarTurno);
        StackPane.setAlignment(btnPasarTurno, Pos.TOP_RIGHT);
        StackPane.setMargin(btnPasarTurno, new Insets(10));

        // Cambio de Escena
        Scene scene = new Scene(raiz, 800, 600);
        try {
            scene.getStylesheets().add(getClass().getResource("/estilos/intercambio.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("No se encontró CSS, continuando sin estilos.");
        }

        stage.setScene(scene);
        // Opcional: stage.setFullScreen(true);
    }

    private void configurarPanelIntercambio(Region panel, IntercambioController controlador, Catan juego) {
        controlador.setJugador(juego.obtenerJugadorActual());
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