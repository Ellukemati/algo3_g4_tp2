package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Arista;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tablero;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ControladorArista implements EventHandler<MouseEvent> {
    private final Tablero tablero;
    private final Arista arista;
    private final Jugador jugador;

    public ControladorArista(Tablero tablero, Arista arista, Jugador jugador) {
        this.tablero = tablero;
        this.arista = arista;
        this.jugador = jugador;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        System.out.println("Jugador intenta construir carretera en Arista " + arista.getId());

        boolean exito = jugador.construirCarretera(tablero, arista.getId());

        if (exito) {
            System.out.println("¡Carretera construida!");

        } else {
            System.out.println("No se pudo construir carretera (Reglas invalidas o sin recursos)");
        }

        mouseEvent.consume();
    }
}