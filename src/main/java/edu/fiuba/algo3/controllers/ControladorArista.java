package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Arista;
import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Tablero;
import edu.fiuba.algo3.vistas.VistaTablero;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class ControladorArista implements EventHandler<MouseEvent> {
    private final Tablero tablero;
    private final Arista arista;
    private final VistaTablero vistaTablero;
    private final Catan juego;
    private final Runnable avanzarTurnoCallback;

    public ControladorArista(Tablero tablero, Arista arista, VistaTablero vistaTablero, Catan juego, Runnable avanzarTurnoCallback) {
        this.tablero = tablero;
        this.arista = arista;
        this.vistaTablero = vistaTablero;
        this.juego = juego;
        this.avanzarTurnoCallback = avanzarTurnoCallback;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        mouseEvent.consume();
        vistaTablero.limpiarAcciones();

        Jugador jugadorActual = juego.obtenerJugadorActual();

        if (!arista.verificarOcupado()) {
            if (juego.esFaseInicial()) {
                if (jugadorActual.obtenerCantidadCarreteras() < jugadorActual.obtenerCantidadConstrucciones()) {
                    Button btnCamino = new Button("Construir Camino (Gratis)");
                    btnCamino.setOnAction(e -> {
                        if (juego.jugadorColocarCarretera(jugadorActual, arista.getId())) {
                            vistaTablero.limpiarAcciones();
                            if (avanzarTurnoCallback != null) {
                                avanzarTurnoCallback.run();
                            }
                        }
                    });
                    vistaTablero.mostrarBotonAccion(btnCamino, mouseEvent.getX(), mouseEvent.getY() - 15);
                }
            }
            else if (puedePagarCarretera(jugadorActual)) {
                Button btnCamino = new Button("Construir Camino");
                btnCamino.setOnAction(e -> {
                    if (juego.jugadorColocarCarretera(jugadorActual, arista.getId())) {
                        vistaTablero.limpiarAcciones();
                    }
                });
                vistaTablero.mostrarBotonAccion(btnCamino, mouseEvent.getX(), mouseEvent.getY() - 15);
            }
        }
    }

    private boolean puedePagarCarretera(Jugador jugador) {
        Map<Recurso, Integer> costo = new HashMap<>();
        costo.put(Recurso.MADERA, 1);
        costo.put(Recurso.LADRILLO, 1);
        return jugador.poseeRecursos(costo);
    }
}