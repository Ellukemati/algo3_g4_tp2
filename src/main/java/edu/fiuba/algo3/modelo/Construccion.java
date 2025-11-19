package edu.fiuba.algo3.modelo;

import java.util.List;

public abstract class Construccion {
    protected Vertice vertice;

    public Construccion(Vertice vertice) {
        this.vertice = vertice;
    }

    public abstract List<Recurso> cosechar();
}
