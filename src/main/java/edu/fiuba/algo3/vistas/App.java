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