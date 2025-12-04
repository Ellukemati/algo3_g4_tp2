package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.vistas.CartaDesarolloRenderizador;

import java.util.List;

public class Monopolio extends CartaDesarolloGeneral {
    @Override
    public void usar(Jugador jugador, Tablero tablero, List<Jugador> jugadores, ParametrosCarta parametrosCarta) {
        // deve ser recibido por imput
        Recurso recursoSeleccionado = parametrosCarta.getRecursoAPedir();
        int cantidadTotalDeRecurso = 0;
        for (Jugador jugador1 : jugadores) {
            if (!jugador1.equals(jugador)) {
                cantidadTotalDeRecurso += jugador1.quitarCantidadTotalDeRecursos(recursoSeleccionado);
            }
        }
        jugador.agregarRecurso(recursoSeleccionado, cantidadTotalDeRecurso);
    }

    @Override
    public void mostrar(CartaDesarolloRenderizador renderizador) {
        renderizador.monopolioRenderizar(this);
    }
}
