package edu.fiuba.algo3.modelo;


public class GranRutaComercial implements CartaDeBonificacion{
	private int longCamino;
    private Bonificacion  jugador;
    
    public GranRutaComercial() {
        this.jugador = new JugadorNulo();
        this.longCamino = 4;
    }
    
    @Override
    public void actualizar(Jugador jugadorAEvaluar) {
    	int longitudNueva = jugadorAEvaluar.obtenerRutaMasLarga();
        if(longitudNueva > longCamino) {
        	jugador.restarPuntos(2);
        	jugadorAEvaluar.sumarPuntos(2);
        	longCamino = longitudNueva;
        	jugador = (Bonificacion) jugadorAEvaluar;
        }
    }
}
