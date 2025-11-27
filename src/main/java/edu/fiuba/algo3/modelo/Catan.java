package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;

public class Catan {
    private final Tablero tablero;
    private final List<Jugador>  jugadores;
    private final Dado dado;

    public Catan() {
        this.tablero = new Tablero();
        this.jugadores = new ArrayList<>();
        this.dado = new Dado();
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public List<Jugador> obtenerJugadores() {
        return jugadores;
    }

    public int lanzarDado() {
        return dado.tirar();
    }

    public void jugarTurno(Jugador jugadorActual) {
        int numeroDado = dado.tirar();
        if (numeroDado == 7) {
            //luego cambiar posicion ladron por el input que decida el jugador
            int posicionLadron = 10;
            Hexagono hexagonoRobar = jugadorActual.moverLadron(tablero, posicionLadron);
            for (Jugador jugador : jugadores) {
                if (jugador != jugadorActual) {
                    // considerar crear un recurso nulo asi no hacemos verificaciones
                    Recurso recursoRobado = jugador.serRobadoPorLadron(hexagonoRobar);
                    if (recursoRobado != null) {
                        jugadorActual.agregarRecurso(recursoRobado, 1);
                    }
                }
            }
        } else {
            for (Jugador jugador : jugadores) {
                jugador.recibirLanzamientoDeDados(numeroDado);
            }
        }
    }
}
