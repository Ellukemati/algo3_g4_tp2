package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.Colocable;
import edu.fiuba.algo3.modelo.Mapa;
import edu.fiuba.algo3.modelo.Tablero;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TableroTest {

    @Test
    void obtenerAdyacentesAUnPobladoConElTableroVacio() {
        //Arrange
        Tablero tablero = new Tablero("mapa.csv");
        ArrayList<Colocable> esperado = new ArrayList<>();

        //Act
        ArrayList<Colocable> actual = tablero.obtenerAdyacentes(2, 8, "V");

        //Asset
        assertEquals(esperado, actual);



    }

    @Test
    void colocarColocable() {
    }
}