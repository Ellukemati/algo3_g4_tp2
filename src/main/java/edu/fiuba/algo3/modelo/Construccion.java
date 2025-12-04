package edu.fiuba.algo3.modelo;

import java.util.List;

public abstract class Construccion {
    protected Vertice vertice;

    public Vertice obtenerVertice() {
        return this.vertice;
    }
    public Construccion(Vertice vertice) {
        this.vertice = vertice;
    }

    public abstract List<Recurso> cosechar(int numeroDado);

    public abstract int obtenerPuntosDeVictoria();
}
