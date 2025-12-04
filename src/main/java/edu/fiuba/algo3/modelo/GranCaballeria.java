package edu.fiuba.algo3.modelo;

public class GranCaballeria implements CartaDeBonificacion{
    private int cantidadCartasUsadas;
    private Bonificacion  jugador;

    public GranCaballeria() {
        this.jugador = new JugadorNulo();
        this.cantidadCartasUsadas = 2;
    }

    @Override
    public void actualizar(Jugador jugadorAEvaluar) {
        int cantidadNueva = jugadorAEvaluar.obtenerCaballerosUsados();
        if(cantidadNueva > cantidadCartasUsadas) {
            jugador.restarPuntos(2);
            jugadorAEvaluar.sumarPuntos(2);
            cantidadCartasUsadas = cantidadNueva;
            jugador = (Bonificacion) jugadorAEvaluar;
        }
    }
}