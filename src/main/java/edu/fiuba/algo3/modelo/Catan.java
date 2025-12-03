package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;

public class Catan implements Observable {
    private final Tablero tablero;
    private final List<Jugador> jugadores;
    private final Dado dado;
    private final List<Observador> observadores;

    // Estado del juego
    private int indiceJugadorActual;
    private boolean juegoIniciado;
    private int contadorTurnos;

    public Catan() {
        this.contadorTurnos = 0;
        this.tablero = new Tablero();
        this.jugadores = new ArrayList<>();
        this.dado = new Dado();
        this.observadores = new ArrayList<>();
        this.indiceJugadorActual = 0;
        this.juegoIniciado = false;
    }

    // --- Gestión de Jugadores ---

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

    // --- Lógica de Turnos ---

    public void siguienteTurno() {
        contadorTurnos++;
        // Finaliza el turno del jugador anterior
        obtenerJugadorActual().finalizarTurno();

        // Avanza al siguiente (circular)
        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();

        // Notificamos a la vista que el turno cambió (para actualizar labels, inventarios, etc.)
        notificarObservadores();
    }

    public int lanzarDado() {
        int resultado = dado.tirar();
        jugarTurno(resultado); // Tu lógica existente de mover ladrón o cobrar recursos
        return resultado;
    }

    // Adaptamos tu método jugarTurno para recibir el int del dado
    private void jugarTurno(int numeroDado) {
        Jugador jugadorActual = obtenerJugadorActual();

        if (numeroDado == 7) {
            // Lógica simplificada del ladrón (puedes expandirla luego)
            int posicionLadron = 10; // TODO: Esto debería venir de la vista/input
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
            // Repartir recursos a todos
            for (Jugador j : jugadores) {
                j.recibirLanzamientoDeDados(numeroDado);
            }
        }
        notificarObservadores();
    }

    // --- Getters para la Vista ---

    public Tablero obtenerTablero() {
        return this.tablero;
    }

    // --- Implementación de Observable ---

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
}