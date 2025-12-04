package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JugadorTest2 {

    @Test
    public void test01JugadorIntercambiaRecursosConOtroJugadorExitosamente() {
        // ARRANGE
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();

        jugador1.agregarRecurso(Recurso.MADERA, 1);
        jugador2.agregarRecurso(Recurso.LADRILLO, 1);

        Map<Recurso, Integer> oferta = Map.of(Recurso.MADERA, 1);
        Map<Recurso, Integer> solicitud = Map.of(Recurso.LADRILLO, 1);

        // ACT
        jugador1.intercambiar(jugador2, oferta, solicitud);

        // ASSERT
        assertEquals(1, jugador1.cantidadTotalDeRecursos());
        assertEquals(1, jugador2.cantidadTotalDeRecursos());

        // Invirtieron su inventario oferta/solicitud = Intercambio exitoso
        assertTrue(jugador1.poseeRecursos(solicitud));
        assertTrue(jugador2.poseeRecursos(oferta));
    }

    @Test
    public void test02IntercambioFallaSiJugadorNoTieneRecursosSuficientes() {
        // ARRANGE
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();

        jugador2.agregarRecurso(Recurso.LADRILLO, 1);

        Map<Recurso, Integer> oferta = Map.of(Recurso.MADERA, 1);
        Map<Recurso, Integer> solicitud = Map.of(Recurso.LADRILLO, 1);

        // ACT
        jugador1.intercambiar(jugador2, oferta, solicitud);

        // ASSERT
        assertEquals(0, jugador1.cantidadTotalDeRecursos());
        assertEquals(1, jugador2.cantidadTotalDeRecursos());

        // jugador1 no posee los recursos que jugador2 solicita = Intercambio fallido
        assertFalse(jugador1.poseeRecursos(solicitud));
    }

    @Test
    public void test03CreacionCorrectaCarreteras() {
        //ARRANGE
        Jugador jugador1 = new Jugador();
        Tablero tablero = new Tablero();

        //ACT && ASSERT
        assertTrue(jugador1.construirCarretera(tablero, 0));
    }

    @Test
    public void test04NoSePermiteLaCreacionCarreterasNoAdyacentes() {
        //ARRANGE
        Jugador jugador = new Jugador();
        Tablero tablero = new Tablero();

        //ACT
        jugador.construirCarretera(tablero, 0);

        //ASSERT
        assertFalse(jugador.construirCarretera(tablero, 70));
    }

    @Test
    public void test05CreacionCarreterasSobreOtras() {
        //ARRANGE
        Jugador jugador1 = new Jugador();
        Tablero tablero = new Tablero();

        //ACT
        jugador1.construirCarretera(tablero, 0);

        // ASSERT
        assertFalse(jugador1.construirCarretera(tablero, 0));
    }

    @Test
    public void test06CreacionCarreterasAdyacentes() {
        //ARRANGE
        Jugador jugador1 = new Jugador();
        Tablero tablero = new Tablero();

        //ACT
        jugador1.construirCarretera(tablero, 0);

        // ASSERT
        assertTrue(jugador1.construirCarretera(tablero, 1));
    }

    @Test
    public void test07ComproCartaDeDesarolloConLosRecursosJustos() {
        //ARRANGE
        Jugador jugador = new Jugador();
        Banca banca = new Banca();
        int cantidadTotalDeRecursosEsperado = 0;
        jugador.agregarRecurso(Recurso.LANA, 1);
        jugador.agregarRecurso(Recurso.GRANO, 1);
        jugador.agregarRecurso(Recurso.MINERAL, 1);

        //ACT
        jugador.comprarCartaDeDesarollo(banca);

        //ASSERT
        assertEquals(cantidadTotalDeRecursosEsperado, jugador.cantidadTotalDeRecursos());
    }

    @Test
    public void test08ComproCartaDeDesarolloConRecursosInsuficientes() {
        //ARRANGE
        Jugador jugador = new Jugador();
        Banca banca = new Banca();
        int cantidadTotalDeRecursosEsperado = 3;
        jugador.agregarRecurso(Recurso.MADERA, 1);
        jugador.agregarRecurso(Recurso.GRANO, 1);
        jugador.agregarRecurso(Recurso.MINERAL, 1);

        //ACT
        jugador.comprarCartaDeDesarollo(banca);

        //ASSERT
        assertEquals(cantidadTotalDeRecursosEsperado, jugador.cantidadTotalDeRecursos());
    }

    @Test
    void test09UsoUnaCartaDeDesarolloDescubrimientoEnElMismoTurnoQueFueCompradaYNoSurgeElEfecto() {
        //ARRANGE
        Jugador jugador = new Jugador();
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador);
        CartaDesarollo carta = new Descubrimiento();
        Tablero tablero = new Tablero();
        Banca bancaMock = Mockito.mock(Banca.class);
        when(bancaMock.comprarCartaDeDesarollo(Mockito.any(Inventario.class))).thenReturn(new Descubrimiento());
        int cantidadDeRecursosEsperada = 0;
        jugador.comprarCartaDeDesarollo(bancaMock);

        //ACT
        jugador.usarCartaDeDesarrollo(carta, tablero, jugadores);

        //ASSERT
        assertEquals(cantidadDeRecursosEsperada, jugador.cantidadTotalDeRecursos());
    }

    @Test
    void test10UsoUnaCartaDeDesarolloEnUnturnoValido() {
        //ARRANGE
        Jugador jugador = new Jugador();
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador);
        CartaDesarollo carta = new Descubrimiento();
        Tablero tablero = new Tablero();
        Banca bancaMock = Mockito.mock(Banca.class);
        when(bancaMock.comprarCartaDeDesarollo(Mockito.any(Inventario.class))).thenReturn(new Descubrimiento());
        int cantidadDeRecursosEsperada = 2;
        jugador.comprarCartaDeDesarollo(bancaMock);


        //ACT
        jugador.usarCartaDeDesarrollo(carta, tablero, jugadores);
        jugador.finalizarTurno();
        jugador.usarCartaDeDesarrollo(carta, tablero, jugadores);

        //ASSERT
        assertEquals(cantidadDeRecursosEsperada, jugador.cantidadTotalDeRecursos());
    }
    
    @Test
    public void test11jugadorConstruyeCarreterasEnCadenaYCalculaRutaMasLarga() {
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador();
        jugador.agregarRecurso(Recurso.MADERA, 10);
        jugador.agregarRecurso(Recurso.LADRILLO, 10);
        jugador.agregarRecurso(Recurso.LANA, 2);
        jugador.agregarRecurso(Recurso.GRANO, 2);
        
        jugador.construirCarretera(tablero, 12);
        jugador.construirCarretera(tablero, 5);
        jugador.construirCarretera(tablero, 6);
        jugador.construirCarretera(tablero,7);
        jugador.construirCarretera(tablero, 13);
        jugador.construirCarretera(tablero, 14);
        jugador.construirCarretera(tablero, 15);

        assertEquals(4, jugador.obtenerRutaMasLarga());
    }
    
    @Test
    public void test12caminosSeFusionanCuandoUnaCarreteraUneDosComponentes() {
        //ARRANGE
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador();
        jugador.agregarRecurso(Recurso.MADERA, 10);
        jugador.agregarRecurso(Recurso.LADRILLO, 10);
        jugador.agregarRecurso(Recurso.LANA, 2);
        jugador.agregarRecurso(Recurso.GRANO, 2);

        //ACT
        jugador.construirCarretera(tablero, 5);
        jugador.construirCarretera(tablero, 2);
        jugador.construirCarretera(tablero, 3);
        jugador.construirCarretera(tablero, 6);
        jugador.construirCarretera(tablero, 4);

        //ASSERT
        assertEquals(5, jugador.obtenerRutaMasLarga());
    }
    
    @Test
    void test13granCaballeriaDaPuntosCuandoJugadorSupera() {
        // ARRANGE
        GranCaballeria granCaballeria = new GranCaballeria();
        Jugador jugador = Mockito.mock(Jugador.class);
        when(jugador.obtenerCaballerosUsados()).thenReturn(3);

        // ACT
        granCaballeria.actualizar(jugador);

        // ASSERT
        verify(jugador).sumarPuntos(2);
    }
    
    @Test
    public void test14granRutaComercialDaPuntosCuandoJugadorSupera() {
        //ARRANGE
    	GranRutaComercial granRutaComercial = new GranRutaComercial();
        Jugador jugador = Mockito.mock(Jugador.class);
        when(jugador.obtenerRutaMasLarga()).thenReturn(5);

        //ACT
        granRutaComercial.actualizar(jugador);

        //ASSERT
        verify(jugador).sumarPuntos(2);
    }
    
    @Test
    void test15granCaballeriaSeActualizaCorrectamente() {
        // ARRANGE
        GranCaballeria granCaballeria = new GranCaballeria();
        Jugador jugador = Mockito.mock(Jugador.class);
        Jugador jugador2 = Mockito.mock(Jugador.class);
        when(jugador.obtenerCaballerosUsados()).thenReturn(3);
        when(jugador2.obtenerCaballerosUsados()).thenReturn(6);

        // ACT
        granCaballeria.actualizar(jugador);
        granCaballeria.actualizar(jugador2);

        // ASSERT
        verify(jugador).restarPuntos(2);
        verify(jugador2).sumarPuntos(2);
        
    }
    
    @Test
    void test16granRutaComercialSeActualizaCorrectamente() {
        // ARRANGE
    	GranRutaComercial granRutaComercial = new GranRutaComercial();
        Jugador jugador = Mockito.mock(Jugador.class);
        Jugador jugador2 = Mockito.mock(Jugador.class);
        when(jugador.obtenerRutaMasLarga()).thenReturn(5);
        when(jugador2.obtenerRutaMasLarga()).thenReturn(6);

        // ACT
        granRutaComercial.actualizar(jugador);
        granRutaComercial.actualizar(jugador2);

        // ASSERT
        verify(jugador).restarPuntos(2);
        verify(jugador2).sumarPuntos(2);
        
    }
    
    @Test
    void test17granCaballeriaNoDaPuntosCuandoJugadorNoSupera() {
        // ARRANGE
        GranCaballeria granCaballeria = new GranCaballeria();
        Jugador jugador = Mockito.mock(Jugador.class);
        when(jugador.obtenerCaballerosUsados()).thenReturn(2);

        // ACT
        granCaballeria.actualizar(jugador);

        // ASSERT
        verify(jugador, never()).sumarPuntos(2);
    }
    
    @Test
    public void test18granRutaComercialnODaPuntosCuandoJugadorNoSupera() {
        //ARRANGE
    	GranRutaComercial granRutaComercial = new GranRutaComercial();
        Jugador jugador = Mockito.mock(Jugador.class);
        when(jugador.obtenerRutaMasLarga()).thenReturn(1);

        //ACT
        granRutaComercial.actualizar(jugador);

        //ASSERT
        verify(jugador, never()).sumarPuntos(2);
    }
    

}
