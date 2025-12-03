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

    public ControladorArista(Tablero tablero, Arista arista, VistaTablero vistaTablero, Catan juego) {
        this.tablero = tablero;
        this.arista = arista;
        this.vistaTablero = vistaTablero;
        this.juego = juego;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        mouseEvent.consume();
        vistaTablero.limpiarAcciones();

        Jugador jugadorActual = juego.obtenerJugadorActual();

        if (!arista.verificarOcupado()) {
            if (puedePagarCarretera(jugadorActual)) {
                Button btnCamino = new Button("Construir Camino");
                btnCamino.setOnAction(e -> {
                    if (jugadorActual.construirCarretera(tablero, arista.getId())) {
                        System.out.println("Carretera construida en arista " + arista.getId());
                        vistaTablero.limpiarAcciones();
                        juego.notificarObservadores();
                    }
                });
                vistaTablero.mostrarBotonAccion(btnCamino, mouseEvent.getX(), mouseEvent.getY() - 15);
            } else {
                System.out.println("Recursos insuficientes para Carretera");
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