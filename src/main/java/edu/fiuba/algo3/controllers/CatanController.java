package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import java.util.HashMap;
import java.util.Map;

public class CatanController {
    private final Catan modelo;
    private Jugador jugadorActual;

    public CatanController(Catan modelo) {
        this.modelo = modelo;
        actualizarJugadorActual();
    }

    public void actualizarJugadorActual() {
        int indiceJugador = modelo.obtenerPosicionJugadorActual();
        this.jugadorActual = modelo.obtenerJugadores().get(indiceJugador);
    }

    public Map<Recurso, Integer> obtenerRecursosJugadorActual() {
        Map<Recurso, Integer> recursos = new HashMap<>();
        if (jugadorActual != null) {
            for (Recurso recurso : Recurso.values()) {
                int cantidad = jugadorActual.CantidadRecurso(recurso);
                recursos.put(recurso, cantidad);
            }
        }
        return recursos;
    }

    public String getNombreJugadorActual() {
        return jugadorActual != null ? jugadorActual.obtenerNombre() : "Sin jugador";
    }

    public void cambiarTurno() {
        modelo.cambiarTurno();
        actualizarJugadorActual();
    }
}
