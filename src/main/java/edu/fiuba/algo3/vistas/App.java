package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.controllers.ControladorIntercambio;
import edu.fiuba.algo3.controllers.ControladorPanelControl;
import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private Stage stage;
    private Catan juego;
    private ControladorIntercambio controladorIntercambio;
    private ControladorPanelControl controladorHUD;

    private VistaPanelControl vistaSuperior;
    private VistaCartaDeRecursos vistaRecursos;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("AlgoCatan - TP2");
        mostrarMenuInicio();
        stage.show();
    }

    private void mostrarMenuInicio() {
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
        configurarModelo(cantidadJugadores);

        VistaTablero vistaTablero = new VistaTablero(juego, this::avanzarTurno);


        FXMLLoader loaderCanje = new FXMLLoader(getClass().getResource("/vistas/intercambio.fxml"));
        Region panelIntercambio = loaderCanje.load();
        controladorIntercambio = loaderCanje.getController();
        configurarPanelIntercambio(panelIntercambio);

        FXMLLoader loaderHUD = new FXMLLoader(getClass().getResource("/vistas/panel_control.fxml"));
        Region panelControl = loaderHUD.load();
        controladorHUD = loaderHUD.getController();
        configurarHUD(panelControl, panelIntercambio);

        vistaRecursos = new VistaCartaDeRecursos();
        HBox panelRecursos = vistaRecursos.inicializarVistaCarta();

        vistaSuperior = new VistaPanelControl();
        vistaSuperior.setAccionPasarTurno(this::avanzarTurno);

        StackPane raiz = new StackPane();

        raiz.getChildren().add(vistaTablero);

        BorderPane interfaz = new BorderPane();
        interfaz.setPickOnBounds(false);

        interfaz.setTop(vistaSuperior);
        interfaz.setBottom(panelRecursos);

        BorderPane.setMargin(vistaSuperior, new Insets(10));

        raiz.getChildren().add(interfaz);

        // Capa 3: HUD (Lateral Derecho)
        raiz.getChildren().add(panelControl);
        StackPane.setAlignment(panelControl, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(panelControl, new Insets(10, 10, 10, 10));
        panelControl.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        // Capa 4: Panel Flotante de Intercambio
        raiz.getChildren().add(panelIntercambio);

        // 4. Configurar Observadores
        configurarObservadoresJuego();
        actualizarControladoresJugadorActual();

        // 5. Mostrar Escena
        Scene scene = new Scene(raiz, 800, 600);
        cargarEstilos(scene);
        stage.setScene(scene);
    }

    // --- MÉTODOS AUXILIARES ---

    private void configurarModelo(int cantidadJugadores) {
        juego = new Catan();
        for (int i = 1; i <= cantidadJugadores; i++) {
            Jugador nuevoJugador = new Jugador("Jugador " + i);
            // Recursos iniciales para testing
            //nuevoJugador.agregarRecurso(Recurso.MADERA, 5);
            //nuevoJugador.agregarRecurso(Recurso.LADRILLO, 5);
            //nuevoJugador.agregarRecurso(Recurso.LANA, 5);
            //nuevoJugador.agregarRecurso(Recurso.GRANO, 5);
            //nuevoJugador.agregarRecurso(Recurso.MINERAL, 5);
            juego.agregarJugador(nuevoJugador);
        }
        juego.iniciarJuego();
    }

    private void configurarPanelIntercambio(Region panel) {
        panel.setMaxSize(650, 400);
        panel.setVisible(false);
    }

    private void configurarHUD(Region panelControl, Region panelIntercambio) {
        controladorHUD.setOnAbrirComercio(() -> {
            controladorIntercambio.limpiarFormulario();
            panelIntercambio.setVisible(true);
            panelIntercambio.toFront();
        });
    }

    private void avanzarTurno() {
        juego.siguienteTurno();
        int nuevoResultado = 0;
        if (juego.obtenerTurno() >= (juego.obtenerJugadores().size() * 2)) {
            nuevoResultado = juego.lanzarDado();
        }

        vistaSuperior.actualizarDado(nuevoResultado);

        actualizarControladoresJugadorActual();
    }

    private void configurarObservadoresJuego() {
        juego.agregarObservador(() -> {});
    }

    private void actualizarControladoresJugadorActual() {
        Jugador actual = juego.obtenerJugadorActual();
        controladorIntercambio.setJugador(actual);
        controladorHUD.setJugador(actual);
        vistaRecursos.actualizarRecursos(actual);
    }

    private void cargarEstilos(Scene scene) {
        try {
            scene.getStylesheets().add(getClass().getResource("/estilos/intercambio.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/estilos/hud.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("Advertencia: No se pudo cargar algún archivo CSS.");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}