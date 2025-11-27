package edu.fiuba.algo3.modelo;

import java.util.List;

public class Poblado extends Construccion {

    public Poblado(Vertice vertice) {
        super(vertice);
    }

    @Override
    public List<Recurso> cosechar(int numeroDado) {
        return vertice.cosecharRecursos(numeroDado);
    }
}
