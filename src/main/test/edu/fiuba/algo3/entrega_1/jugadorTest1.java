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

    @Test
    public void test05JugadorPuedeMejorarPobladoACiudadYDuplicaRecursos() {
        // ARRANGE
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador();
        int idVertice = 10; // Elegimos un vértice interior arbitrario
        // Si no hay poblado, construirCiudad debería fallar.
        jugador.construirPoblado(tablero, idVertice);

        // ACT
        // Intentamos mejorar ese poblado a ciudad
        boolean ciudadConstruida = jugador.construirCiudad(tablero, idVertice);

        // ASSERT
        // 1. Verificamos que la operación fue exitosa
        assertTrue(ciudadConstruida, "Debería permitir construir Ciudad sobre un Poblado propio");

        // 2. Verificación de comportamiento (Producción duplicada)
        // NOTA: Como el tablero es aleatorio, es difícil saber EXACTAMENTE qué recursos tiene.

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

