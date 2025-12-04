package edu.fiuba.algo3.modelo;

public class GranCaballeria {
    private int recordEjercito;
    private Bonificacion poseedorActual;

    public GranCaballeria() {
        this.poseedorActual = new JugadorNulo();
        this.recordEjercito = 2;
    }

    public void actualizar(Jugador jugadorCandidato) {
        int ejercitoCandidato = jugadorCandidato.obtenerCaballerosUsados();

        if (ejercitoCandidato > recordEjercito) {
            poseedorActual.asignarGranCaballeria(false);

            recordEjercito = ejercitoCandidato;
            poseedorActual = jugadorCandidato;

            poseedorActual.asignarGranCaballeria(true);
        }
    }
}