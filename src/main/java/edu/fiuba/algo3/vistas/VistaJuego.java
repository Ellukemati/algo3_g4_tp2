package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.controllers.IntercambioController;
import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Observador;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.IOException;

public class VistaJuego extends StackPane implements Observador {

    private final Catan juego;
    private final VistaCartaDeRecursos vistaRecursos;
    private IntercambioController intercambioController;
    private Region panelIntercambio;

    private Label lblDado;
    private Label lblJugador;
    private Button btnPasarTurno;
    private Button btnLanzarDados;

    private int turnoActualLocal = -1;

    public VistaJuego(Catan juego) {
        this.juego = juego;
        this.vistaRecursos = new VistaCartaDeRecursos();

        VistaTablero vistaTablero = new VistaTablero(juego, null);
        HBox panelRecursos = vistaRecursos.inicializarVistaCarta();
        inicializarLabels();
        inicializarPanelIntercambio();

        this.getChildren().add(vistaTablero);
        this.getChildren().add(panelRecursos);

        agregarBotones();
        agregarLabels();

        this.getChildren().add(panelIntercambio);

        juego.agregarObservador(this);

        actualizar();
    }

    private void inicializarLabels() {
        lblDado = new Label("Dado: -");
        lblDado.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        lblDado.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-padding: 5px; -fx-background-radius: 5;");

        lblJugador = new Label();
        lblJugador.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        lblJugador.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-padding: 5px; -fx-background-radius: 5;");
    }

    private void agregarLabels() {
        this.getChildren().add(lblDado);
        StackPane.setAlignment(lblDado, Pos.TOP_CENTER);
        StackPane.setMargin(lblDado, new Insets(10));

        this.getChildren().add(lblJugador);
        StackPane.setAlignment(lblJugador, Pos.TOP_LEFT);
        StackPane.setMargin(lblJugador, new Insets(10));
    }

    private void inicializarPanelIntercambio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/intercambio.fxml"));
            this.panelIntercambio = loader.load();
            this.intercambioController = loader.getController();

            panelIntercambio.setMaxSize(650, 400);
            panelIntercambio.setVisible(false);

            intercambioController.setJugador(juego.obtenerJugadorActual());
        } catch (IOException e) {
            System.err.println("Advertencia: No se pudo cargar /vistas/intercambio.fxml");
            this.panelIntercambio = new Region(); // Placeholder vacío
        }
    }

    private void agregarBotones() {
        Button btnComerciar = new Button("Comerciar");
        btnComerciar.getStyleClass().add("boton-comerciar");
        btnComerciar.setOnAction(e -> {
            panelIntercambio.setVisible(true);
            panelIntercambio.toFront();
        });

        btnLanzarDados = new Button("Lanzar Dados");
        btnLanzarDados.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btnLanzarDados.setOnAction(e -> manejarLanzamientoDados());

        btnPasarTurno = new Button("Pasar Turno");
        btnPasarTurno.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btnPasarTurno.setOnAction(e -> manejarPaseDeTurno());

        this.getChildren().add(btnComerciar);
        StackPane.setAlignment(btnComerciar, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(btnComerciar, new Insets(10));

        this.getChildren().add(btnPasarTurno);
        StackPane.setAlignment(btnPasarTurno, Pos.TOP_RIGHT);
        StackPane.setMargin(btnPasarTurno, new Insets(10));

        this.getChildren().add(btnLanzarDados);
        StackPane.setAlignment(btnLanzarDados, Pos.TOP_RIGHT);
        StackPane.setMargin(btnLanzarDados, new Insets(50, 10, 0, 0));
    }

    private void manejarLanzamientoDados() {
        int resultado = juego.lanzarDado();

        lblDado.setText("Dado: " + resultado);

        btnLanzarDados.setDisable(true);
        btnPasarTurno.setDisable(false);
    }

    private void manejarPaseDeTurno() {
        juego.siguienteTurno();
    }

    @Override
    public void actualizar() {
        lblJugador.setText(juego.obtenerJugadorActual().obtenerNombre());
        vistaRecursos.actualizarRecursos(juego.obtenerJugadorActual());

        if (intercambioController != null) {
            intercambioController.setJugador(juego.obtenerJugadorActual());
        }

        int turnoDelModelo = juego.obtenerTurno();

        if (turnoDelModelo != turnoActualLocal) {
            turnoActualLocal = turnoDelModelo;
            configurarEstadoInicialTurno();
        }
    }

    private void configurarEstadoInicialTurno() {

        lblDado.setText("Dado: -");

        boolean esFasePreparacion = juego.obtenerTurno() < (juego.obtenerJugadores().size() * 2);

        if (esFasePreparacion) {
            btnLanzarDados.setDisable(true);
            btnLanzarDados.setVisible(false);
            btnPasarTurno.setDisable(false);
        } else {
            btnLanzarDados.setDisable(false);
            btnLanzarDados.setVisible(true);
            btnPasarTurno.setDisable(true);
        }
    }
}