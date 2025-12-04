package edu.fiuba.algo3.modelo;

public class GranRutaComercial {
    private int recordLongitud;
    private Bonificacion poseedorActual;

    public GranRutaComercial() {
        this.poseedorActual = new JugadorNulo();
        this.recordLongitud = 4;
    }

    public void actualizar(Jugador jugadorCandidato) {
        int longitudCandidato = jugadorCandidato.obtenerRutaMasLarga();

        if (longitudCandidato > recordLongitud) {
            poseedorActual.asignarGranRuta(false);

            recordLongitud = longitudCandidato;

            poseedorActual = jugadorCandidato;
            poseedorActual.asignarGranRuta(true);
        }
    }
}