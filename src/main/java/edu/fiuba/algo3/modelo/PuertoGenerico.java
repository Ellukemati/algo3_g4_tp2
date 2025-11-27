package edu.fiuba.algo3.modelo;

public class PuertoGenerico extends Puerto {
    @Override
    public void aplicarBeneficio(Jugador jugador) {
        jugador.activarDescuentoGenerico();
    }
}
