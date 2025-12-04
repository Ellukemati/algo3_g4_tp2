package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Tablero;
import edu.fiuba.algo3.modelo.Vertice;
import edu.fiuba.algo3.vistas.VistaTablero;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

public class ControladorVertice implements EventHandler<MouseEvent> {
    private final Tablero tablero;
    private final Vertice vertice;
    private final VistaTablero vistaTablero;
    private final Catan juego;
    private final Runnable avanzarTurnoCallback;

    public ControladorVertice(Tablero tablero, Vertice vertice, VistaTablero vistaTablero,
                              Catan juego, Runnable avanzarTurnoCallback) {
        this.tablero = tablero;
        this.vertice = vertice;
        this.vistaTablero = vistaTablero;
        this.juego = juego;
        this.avanzarTurnoCallback = avanzarTurnoCallback;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        mouseEvent.consume();
        vistaTablero.limpiarAcciones();
        boolean faseInicial = juego.obtenerFaseInicial();
        Jugador jugadorActual = juego.obtenerJugadorActual();

        // CASO 1: Vértice libre -> Construir Poblado
        if (!vertice.verificarOcupado()) {
            if (faseInicial) {
                Button btnConstruir = new Button("Construir Poblado (Gratis)");
                btnConstruir.setOnAction(e -> {
                    if (jugadorActual.construirPoblado(tablero, vertice.getId())) {
                        vistaTablero.limpiarAcciones();

                        if (avanzarTurnoCallback != null) {
                            avanzarTurnoCallback.run();
                        }
                    }
                });
                vistaTablero.mostrarBotonAccion(btnConstruir, mouseEvent.getX(), mouseEvent.getY());
            } else if (puedePagarPoblado(jugadorActual)) {
                // ... lógica de botón construir poblado ...
                Button btnConstruir = new Button("Construir Poblado");
                btnConstruir.setOnAction(e -> {
                    if (jugadorActual.construirPoblado(tablero, vertice.getId())) {
                        vistaTablero.limpiarAcciones();
                        // El modelo ya notifica observadores
                    }
                });
                vistaTablero.mostrarBotonAccion(btnConstruir, mouseEvent.getX(), mouseEvent.getY());
            }
        }
        // CASO 2: Vértice Ocupado -> Verificar si TIENE EDIFICIO para mejorar
        else {
            // CAMBIO CLAVE: Usamos vertice.tieneEdificio() en vez de solo 'else'
            if (vertice.tieneEdificio()) {
                if (puedePagarCiudad(jugadorActual)) {
                    Button btnMejorar = new Button("Mejorar a Ciudad");
                    btnMejorar.setOnAction(e -> {
                        // ... lógica de mejora ...
                        if (jugadorActual.construirCiudad(tablero, vertice.getId())) {
                            vistaTablero.limpiarAcciones();
                        }
                    });
                    vistaTablero.mostrarBotonAccion(btnMejorar, mouseEvent.getX(), mouseEvent.getY());
                } else {
                    System.out.println("No tienes recursos para mejorar a Ciudad");
                }
            } else {
                System.out.println("Este vértice está bloqueado por un poblado cercano.");
            }
        }
    }

    private boolean puedePagarPoblado(Jugador jugador) {
        Map<Recurso, Integer> costo = new HashMap<>();
        costo.put(Recurso.MADERA, 1);
        costo.put(Recurso.LADRILLO, 1);
        costo.put(Recurso.LANA, 1);
        costo.put(Recurso.GRANO, 1);
        return jugador.poseeRecursos(costo);
    }

    private boolean puedePagarCiudad(Jugador jugador) {
        Map<Recurso, Integer> costo = new HashMap<>();
        costo.put(Recurso.GRANO, 2);
        costo.put(Recurso.MINERAL, 3);
        return jugador.poseeRecursos(costo);
    }
}