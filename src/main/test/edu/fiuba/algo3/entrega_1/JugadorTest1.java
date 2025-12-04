package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.Tablero;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.fiuba.algo3.modelo.*;

public class JugadorTest1 {

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
        jugador.construirPoblado(tablero,20);
        int recursosFinales = jugador.cantidadTotalDeRecursos();

        // ASSERT
        assertTrue(recursosFinales > recursosIniciales);
    }

    @Test
    public void test05JugadorPuedeMejorarPobladoACiudadYDuplicaRecursos() {
        // ARRANGE
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador();
        int idVertice = 10;

        jugador.agregarRecurso(Recurso.MADERA, 1);
        jugador.agregarRecurso(Recurso.LADRILLO, 1);
        jugador.agregarRecurso(Recurso.LANA, 1);
        jugador.agregarRecurso(Recurso.GRANO, 1);

        boolean pobladoConstruido = jugador.construirPoblado(tablero, idVertice);
        assertTrue(pobladoConstruido, "El poblado debería construirse");

        jugador.agregarRecurso(Recurso.GRANO, 2);
        jugador.agregarRecurso(Recurso.MINERAL, 3);

        // ACT
        boolean ciudadConstruida = jugador.construirCiudad(tablero, idVertice);

        // ASSERT
        assertTrue(ciudadConstruida, "Debería permitir construir Ciudad sobre un Poblado propio");
    }

    @Test
    public void test06NoSePuedeConstruirCiudadEnVerticeVacio() {
        // ARRANGE
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador();
        int idVerticeVacio = 5;

        // ACT
        // Intentamos construir ciudad directamente sin haber puesto poblado antes
        boolean ciudadConstruida = jugador.construirCiudad(tablero, idVerticeVacio);

        // ASSERT
        assertFalse(ciudadConstruida, "No se debería poder construir una Ciudad en un lugar vacío");
    }
}
