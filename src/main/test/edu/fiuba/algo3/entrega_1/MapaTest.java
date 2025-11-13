package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.Mapa;
import org.junit.jupiter.api.Test;

import java.beans.Transient;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MapaTest {

    @Test
    void obtenerPosicionesAdyacentes() {
        //Arrange
        Mapa mapa = new Mapa("mapa.csv");
        ArrayList<int[]> esperado =  new ArrayList<>(List.of(new int[] {4, 6}, new int[] {4, 10}));
        //ArrayList<int[]> resultado2 = mapa.obtenerPosicionesAdyacentes(5, 8, "E");

        //Act
        ArrayList<int[]> actual = mapa.obtenerPosicionesAdyacentes(2, 8, "V");

        //Assert
        for(int i = 0; i < esperado.size(); i++) {
            assertArrayEquals(esperado.get(i), actual.get(i));
        }
        //assertEquals(esperado, actual);

    }

    @Test
    void esPocicionValida() {
        //Arrange
        Mapa mapa = new Mapa("mapa.csv");

        //Assert
        assertTrue(mapa.esPocicionValida(2, 8, "V"));
    }

    @Test
    void calculoDelTamañoDelMapa() {
        //Arrange
        Mapa mapa = new Mapa("mapa.csv");
        int[] esperado = new int[] {27, 25};

        //Act
        int[] actual = mapa.calcularTamañoMapa();

        //Assert
        assertArrayEquals(esperado, actual);
    }

    @Test
    void CalculoDePosicionesCorrectasDeExagonos() {
        Mapa mapa = new Mapa("mapa.csv");
        List<int[]> as = Arrays.asList(new int[] {5, 8}, new int[] {5, 12}, new int[] {5, 16}, new int[] {9, 6},
                new int[] {9, 10}, new int[] {9, 14}, new int[] {9, 18}, new int[] {13, 4}, new int[] {13, 8},
                new int[] {13, 12}, new int[] {13, 16}, new int[] {13, 20}, new int[] {17, 6}, new int[] {17, 10},
                new int[] {17, 14}, new int[] {17, 18}, new int[] {21, 8}, new int[] {21, 12}, new int[] {21, 16});
        Queue<int[]> esperado = new LinkedList<>(as);

        Queue<int[]> actual = mapa.calcularPosicionesExagonos();

        for (int i = 0; i < actual.size(); i++) {
            assertArrayEquals(esperado.poll(), actual.poll());
        }

    }
}