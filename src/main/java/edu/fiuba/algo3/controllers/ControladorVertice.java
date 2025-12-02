package edu.fiuba.algo3.controllers; //

import edu.fiuba.algo3.modelo.Tablero;
import edu.fiuba.algo3.modelo.Vertice;
import edu.fiuba.algo3.vistas.VistaTablero;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ControladorVertice implements EventHandler<MouseEvent> {
    private final Tablero tablero;
    private final Vertice vertice;
    private final VistaTablero vistaTablero;

    public ControladorVertice(Tablero tablero, Vertice vertice, VistaTablero vistaTablero) {
        this.tablero = tablero;
        this.vertice = vertice;
        this.vistaTablero = vistaTablero;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        // ACCIÓN DE DEBUG:
        // En lugar de construir, le pedimos al tablero que muestre los vecinos
        vistaTablero.iluminarVecinos(vertice);

        mouseEvent.consume();
    }
}