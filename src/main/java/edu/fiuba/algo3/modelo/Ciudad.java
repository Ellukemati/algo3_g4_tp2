package edu.fiuba.algo3.modelo;

import java.util.List;
import java.util.ArrayList;

public class Ciudad extends Construccion {

    public Ciudad(Vertice vertice) {
        super(vertice);
    }

    @Override
    public List<Recurso> cosechar() {
        List<Recurso> recursosBase = vertice.cosecharRecursos();
        List<Recurso> recursosDuplicados = new ArrayList<>();

        for (Recurso recurso : recursosBase) {
            recursosDuplicados.add(recurso);
            recursosDuplicados.add(recurso);
        }
        return recursosDuplicados;
    }
}
