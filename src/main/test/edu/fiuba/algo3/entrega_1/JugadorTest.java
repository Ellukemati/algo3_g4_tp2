package edu.fiuba.algo3.entrega_1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;

public class JugadorTest {

    @Test
    public void test01JugadorCon9CartasDescartaLaMitadAlResultarUn7EnLosDados() {
        // ARRANGE
        int cantidadDeRecursosFinalesEsperados = 5;
        Jugador jugador = new Jugador();
        for (int i = 0; i < 9; i++) {
            jugador.agregarRecurso(Recurso.MADERA);
        }

        // ACT
        jugador.recibirLanzamientoDeDados(7);

        // ASSERT
        assertEquals(cantidadDeRecursosFinalesEsperados, jugador.cantidadTotalDeRecursos());
    }

    @Test
    public void test02JugadorCon7CartasAlRecibirUn7NoDescarta() {
        // ARRANGE
        int cantidadDeRecursosFinalesEsperados = 7;
        Jugador jugador = new Jugador();
        for (int i = 0; i < 7; i++) {
            jugador.agregarRecurso(Recurso.LANA);
        }

        // ACT
        jugador.recibirLanzamientoDeDados(7);

        // ASSERT
        assertEquals(cantidadDeRecursosFinalesEsperados, jugador.cantidadTotalDeRecursos());
    }

    @Test
    public void test03JugadorAlRecibirUnNumeroQueNoEs7NoDescarta() {
        // ARRANGE
        int cantidadDeRecursosFinalesEsperados = 9;
        Jugador jugador = new Jugador();
        for (int i = 0; i < 9; i++) {
            jugador.agregarRecurso(Recurso.MADERA);
        }

        // ACT
        jugador.recibirLanzamientoDeDados(2);

        // ASSERT
        assertEquals(cantidadDeRecursosFinalesEsperados, jugador.cantidadTotalDeRecursos());
    }
}
