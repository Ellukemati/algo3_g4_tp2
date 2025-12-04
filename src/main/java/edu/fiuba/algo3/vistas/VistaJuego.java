package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.controllers.IntercambioController;
import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Hexagono;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Observador;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VistaJuego extends StackPane implements Observador {

    private final Catan juego;
    private final VistaCartaDeRecursos vistaRecursos;
    private IntercambioController intercambioController;
    private Region panelIntercambio;
    private VistaTablero vistaTablero; // Atributo

    private Label lblDado;
    private Label lblJugador;
    private Button btnPasarTurno;
    private Button btnLanzarDados;

    private int turnoActualLocal = -1;

    public VistaJuego(Catan juego) {
        this.juego = juego;
        this.vistaRecursos = new VistaCartaDeRecursos();

        // IMPORTANTE: Asignar al atributo, no declarar variable local
        this.vistaTablero = new VistaTablero(juego, null);

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
            this.panelIntercambio = new Region();
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

        if (resultado == 7) {
            System.out.println("DEBUG: Salió 7. Iniciando movimiento ladrón.");
            btnPasarTurno.setDisable(true);
            iniciarMovimientoLadron();
        } else {
            btnPasarTurno.setDisable(false);
        }
    }

    private void iniciarMovimientoLadron() {
        lblDado.setText("¡LADRÓN! (7) - Selecciona dónde mover");

        vistaTablero.activarSeleccionLadron(hexagonoDestino -> {
            gestionarRobo(hexagonoDestino);
            vistaTablero.desactivarSeleccionLadron();
            vistaTablero.actualizarTablero();
            btnPasarTurno.setDisable(false);
            lblDado.setText("Ladrón movido. Fin del robo.");
        });
    }

    private void gestionarRobo(Hexagono hexagono) {
        Jugador jugadorActual = juego.obtenerJugadorActual();

        List<Jugador> victimas = juego.obtenerJugadores().stream()
                .filter(j -> !j.equals(jugadorActual))
                .filter(j -> tieneConstruccionEnHexagono(j, hexagono))
                .filter(j -> j.cantidadTotalDeRecursos() > 0)
                .collect(Collectors.toList());

        Jugador victimaSeleccionada = null;

        if (!victimas.isEmpty()) {
            if (victimas.size() == 1) {
                victimaSeleccionada = victimas.get(0);
            } else {
                ChoiceDialog<Jugador> dialog = new ChoiceDialog<>(victimas.get(0), victimas);
                dialog.setTitle("Robar Recurso");
                dialog.setHeaderText("Elige a quién robar");
                dialog.setContentText("Víctima:");
                Optional<Jugador> result = dialog.showAndWait();
                if (result.isPresent()) victimaSeleccionada = result.get();
            }
        }

        juego.moverLadron(hexagono.obtenerId(), victimaSeleccionada);
        vistaRecursos.actualizarRecursos(jugadorActual);
    }

    private boolean tieneConstruccionEnHexagono(Jugador j, Hexagono h) {
        return j.serRobadoPorLadron(h) != null;
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