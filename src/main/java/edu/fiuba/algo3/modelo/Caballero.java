package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.vistas.CartaDesarolloRenderizador;

import java.util.List;

public class Caballero extends CartaDesarolloGeneral {
    @Override
    public void usar(Jugador jugador, Tablero tablero, List<Jugador> jugadores, ParametrosCarta parametrosCarta) {
        int posicionLadron = parametrosCarta.getPosicionLadron();
        Hexagono hexagonoRobar = jugador.moverLadron(tablero, posicionLadron);
        Recurso recursoRobado;
        int indice = 0;
        int cantidad = jugador.cantidadTotalDeRecursos();
        while (indice < jugadores.size() && cantidad == jugador.cantidadTotalDeRecursos()) {
            recursoRobado = jugadores.get(indice).serRobadoPorLadron(hexagonoRobar);
            if(!jugador.equals(jugadores.get(indice))) {
                if (recursoRobado != null) {
                    jugador.agregarRecurso(recursoRobado, 1);
                }
            }
            indice++;
        }
    }

    @Override
    public void mostrar(CartaDesarolloRenderizador renderizador) {
        renderizador.caballeroRenderizar(this);
    }
}
