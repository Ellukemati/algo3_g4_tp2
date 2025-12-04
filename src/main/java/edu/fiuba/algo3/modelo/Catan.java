package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;

public class Catan implements Observable {
    private final Tablero tablero;
    private final List<Jugador> jugadores;
    private final Dado dado;
    private final List<Observador> observadores;

    private int indiceJugadorActual;
    private boolean juegoIniciado;
    private int contadorTurnos;
    private boolean faseInicial;

    public Catan() {
        this.contadorTurnos = 0;
        this.tablero = new Tablero();
        this.jugadores = new ArrayList<>();
        this.dado = new Dado();
        this.observadores = new ArrayList<>();
        this.indiceJugadorActual = 0;
        this.juegoIniciado = false;
        this.faseInicial = true;
    }

    public void agregarJugador(Jugador jugador) {
        if (!juegoIniciado) {
            jugadores.add(jugador);
        }
    }

    public List<Jugador> obtenerJugadores() {
        return jugadores;
    }

    public Jugador obtenerJugadorActual() {
        if (jugadores.isEmpty()) return null;
        return jugadores.get(indiceJugadorActual);
    }

    public void siguienteTurno() {
        contadorTurnos++;

        if (contadorTurnos >= jugadores.size() * 2) {
            faseInicial = false;
        }

        obtenerJugadorActual().finalizarTurno();

        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();

        notificarObservadores();
    }

    public int lanzarDado() {
        if (faseInicial) {
            return 0;
        }

        int resultado = dado.tirar();
        jugarTurno(resultado);
        return resultado;
    }

    private void jugarTurno(int numeroDado) {
        Jugador jugadorActual = obtenerJugadorActual();

        if (numeroDado == 7) {
            int posicionLadron = 10;
            Hexagono hexagonoRobar = jugadorActual.moverLadron(tablero, posicionLadron);

            for (Jugador j : jugadores) {
                if (j != jugadorActual) {
                    Recurso recursoRobado = j.serRobadoPorLadron(hexagonoRobar);
                    if (recursoRobado != null) {
                        jugadorActual.agregarRecurso(recursoRobado, 1);
                    }
                }
            }
        } else {
            for (Jugador j : jugadores) {
                j.recibirLanzamientoDeDados(numeroDado);
            }
        }
        notificarObservadores();
    }

    public Tablero obtenerTablero() {
        return this.tablero;
    }

    @Override
    public void agregarObservador(Observador observador) {
        this.observadores.add(observador);
    }

    @Override
    public void notificarObservadores() {
        for (Observador observador : observadores) {
            observador.actualizar();
        }
    }

    public int obtenerTurno() {
        return contadorTurnos;
    }

    public boolean esFaseInicial() {
        return faseInicial;
    }
}