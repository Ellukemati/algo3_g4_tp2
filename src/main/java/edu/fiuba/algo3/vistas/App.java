package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("AlgoCatan - TP2");
        mostrarMenuInicio();
        stage.show();
    }

    private void mostrarMenuInicio() {
        VistaMenuInicio vistaMenu = new VistaMenuInicio(cantidad -> {
            iniciarJuego(cantidad);
        });
        Scene scene = new Scene(vistaMenu, 800, 600);
        stage.setScene(scene);
    }

    private void iniciarJuego(int cantidadJugadores) {
        // 1. Crear Modelo
        Catan juego = crearModeloJuego(cantidadJugadores);

        // 2. Crear Vista Principal (toda la lógica UI está encapsulada aquí)
        VistaJuego vistaJuego = new VistaJuego(juego);

        // 3. Configurar Escena
        Scene scene = new Scene(vistaJuego, 800, 600);
        cargarEstilos(scene);

        stage.setScene(scene);
        // stage.setFullScreen(true);
    }

    private Catan crearModeloJuego(int cantidadJugadores) {
        Catan juego = new Catan();
        for (int i = 1; i <= cantidadJugadores; i++) {
            Jugador nuevoJugador = new Jugador("Jugador " + i);
            // Recursos iniciales de prueba
            nuevoJugador.agregarRecurso(Recurso.MADERA, 5);
            nuevoJugador.agregarRecurso(Recurso.LADRILLO, 5);
            nuevoJugador.agregarRecurso(Recurso.LANA, 5);
            nuevoJugador.agregarRecurso(Recurso.GRANO, 5);
            nuevoJugador.agregarRecurso(Recurso.MINERAL, 5);
            juego.agregarJugador(nuevoJugador);
        }
        return juego;
    }

        // 3. Creamos la vista del tablero
        VistaTablero vistaTablero = new VistaTablero(juego);
        StackPane raiz = new StackPane(vistaTablero);

        // 4. Carga del Panel de Intercambio
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/intercambio.fxml"));
        Region panelIntercambio = loader.load();

        IntercambioController intercambioController = loader.getController();
        configurarPanelIntercambio(panelIntercambio, intercambioController, juego);

        // 5. carga vista de los recursos
        VistaCartaDeRecursos recursos = new VistaCartaDeRecursos();
        HBox hBox = recursos.inicializarVistaCarta();
        raiz.getChildren().add(hBox);

        // Observer: Actualizar controlador cuando cambia el turno
        juego.agregarObservador(() -> {
            intercambioController.setJugador(juego.obtenerJugadorActual());
        });

        raiz.getChildren().add(panelIntercambio);

        // Labels
        Label resultadoDado = new Label();
        resultadoDado.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        int resultadoInicial = juego.lanzarDado();
        resultadoDado.setText("Dado: " + resultadoInicial);
        raiz.getChildren().add(resultadoDado);
        StackPane.setAlignment(resultadoDado, Pos.TOP_CENTER);
        StackPane.setMargin(resultadoDado, new Insets(10));

        Label nombreJugador = new Label();
        nombreJugador.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        nombreJugador.setText(juego.obtenerJugadorActual().obtenerNombre());
        raiz.getChildren().add(nombreJugador);
        StackPane.setAlignment(nombreJugador, Pos.TOP_LEFT);
        StackPane.setMargin(nombreJugador, new Insets(10));

        // Botones de UI
        Button btnAbrirComercio = crearBotonAbrirComercio(panelIntercambio);
        raiz.getChildren().add(btnAbrirComercio);
        StackPane.setAlignment(btnAbrirComercio, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(btnAbrirComercio, new Insets(10));

        Button btnPasarTurno = new Button("Pasar Turno");
        btnPasarTurno.setOnAction(e -> {
            juego.siguienteTurno();
            int nuevoResultado = 0;

            if (juego.obtenerTurno() >= (juego.obtenerJugadores().size() * 2)) {
                nuevoResultado = juego.lanzarDado();
            }
            resultadoDado.setText("Dado: " + nuevoResultado);
            nombreJugador.setText(juego.obtenerJugadorActual().obtenerNombre());
            recursos.actualizarRecursos(juego.obtenerJugadorActual());
            recursos.actulizarCartaDesarollo(juego.obtenerJugadorActual());
        });
        raiz.getChildren().add(btnPasarTurno);
        StackPane.setAlignment(btnPasarTurno, Pos.TOP_RIGHT);
        StackPane.setMargin(btnPasarTurno, new Insets(10));

        // boton para comprar cartas
        Button btnComprarCarta = new Button("Comprar carta");
        //btnComprarCarta.setTranslateX(-200);
        btnComprarCarta.setTranslateY(225);
        btnComprarCarta.setOnAction(e -> {
            juego.jugadorActualComprarCartaDeDesarrollo();
            recursos.actulizarCartaDesarollo(juego.obtenerJugadorActual());
            recursos.actualizarRecursos(juego.obtenerJugadorActual());
        });
        raiz.getChildren().add(btnComprarCarta);
        StackPane.setAlignment(btnComprarCarta, Pos.CENTER_LEFT);

        // Cambio de Escena
        Scene scene = new Scene(raiz, 800, 600);
    private void cargarEstilos(Scene scene) {
        try {
            scene.getStylesheets().add(getClass().getResource("/estilos/intercambio.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("Advertencia: No se encontró CSS (/estilos/intercambio.css). Continuando sin estilos.");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}