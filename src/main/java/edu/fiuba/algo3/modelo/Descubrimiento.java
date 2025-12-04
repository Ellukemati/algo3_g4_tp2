package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Descubrimiento extends CartaDesarolloGeneral {
    @Override
    public void usar(Jugador jugador, Tablero tablero, List<Jugador> jugadores) {
        List<Recurso> recursos = new ArrayList<>(Arrays.asList(Recurso.MADERA, Recurso.LANA));
        for (int i = 0; i < recursos.size(); i++) {
            jugador.agregarRecurso(recursos.get(i), 1);
        }
    }
}
