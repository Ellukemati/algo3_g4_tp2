package edu.fiuba.algo3.entrega_1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.fiuba.algo3.modelo.*;
import java.util.List;

public class jugadorTest1 {

    @Test
    public void test01JugadorCon9CartasDescartaLaMitadAlResultarUn7EnLosDados() {
        // ARRANGE
        int cantidadDeRecursosFinalesEsperados = 5;
        Jugador jugador = new Jugador();
        jugador.agregarRecurso(Recurso.MADERA, 9);

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
        jugador.agregarRecurso(Recurso.LANA, 7);

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
        jugador.agregarRecurso(Recurso.MADERA, 9);

        // ACT
        jugador.recibirLanzamientoDeDados(2);

        // ASSERT
        assertEquals(cantidadDeRecursosFinalesEsperados, jugador.cantidadTotalDeRecursos());
    }

    @Test
    public void test04JugadorRecibeRecursosInicialesAlColocarSegundoPoblado() {
        // ARRANGE
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador();

        jugador.construirPoblado(tablero,0);
        int recursosIniciales = jugador.cantidadTotalDeRecursos();

        // ACT
        boolean segundoPobladoColocado = jugador.construirPoblado(tablero,20);
        int recursosFinales = jugador.cantidadTotalDeRecursos();

        // ASSERT
        assertTrue(recursosFinales > recursosIniciales);
    }


}
