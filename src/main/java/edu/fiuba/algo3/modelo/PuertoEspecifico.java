package edu.fiuba.algo3.modelo;

public class PuertoEspecifico extends Puerto {
    private final Recurso recurso;

    public PuertoEspecifico(Recurso recurso) {
        this.recurso = recurso;
    }

    @Override
    public void aplicarBeneficio(Jugador jugador) {
        jugador.activarDescuentoPara(this.recurso);
    }
}
