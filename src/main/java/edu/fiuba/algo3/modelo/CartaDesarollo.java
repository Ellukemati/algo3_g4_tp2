package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.vistas.CartaDesarolloRenderizador;

import java.util.List;

public interface CartaDesarollo {

    void usar(Jugador jugador, Tablero tablero, List<Jugador> jugadores, ParametrosCarta parametrosCarta);

    void mostrar(CartaDesarolloRenderizador renderizador);
}
