package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.mockito.Mockito;

public class CatanTest {
	
	@Test
    public void testJugadorGanaCon10Puntos() {

        // ARRANGE
        Jugador jugador = Mockito.mock(Jugador.class);

        when(jugador.obtenerPuntage()).thenReturn(7);
        when(jugador.obtenerPuntosVictoriaOcultos()).thenReturn(3);
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador);
        Catan catan = new Catan(jugadores);

        // ACT
        boolean resultado = catan.verificarSiGanó(jugador);

        // ASSERT
        assertTrue(resultado, "El jugador debería ganar con 10 puntos");
    }
}
