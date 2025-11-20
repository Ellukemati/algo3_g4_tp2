package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
    private int id;
    private List<Hexagono> hexagonosAdyacentes;
    private  List<Vertice> verticesAdyacentes;
    private boolean ocupado;

    public Vertice(int id) {
        this.id = id;
        this.hexagonosAdyacentes = new ArrayList<>();
        this.verticesAdyacentes = new ArrayList<>();
        this.ocupado = false;
    }
    public void agregarHexagono(Hexagono hexagono) {
        hexagonosAdyacentes.add(hexagono);
    }

    public void agregarVerticeAdyacente(Vertice vertice) {
        verticesAdyacentes.add(vertice);
    }

    public void ocuparVertice() {
        this.ocupado = true;
    }

    public boolean verificarOcupado() {
        return this.ocupado;
    }

    public List<Recurso> cosecharRecursos() {
        List<Recurso> recursos = new ArrayList<>();
        for (Hexagono hexagonosAdyacente : this.hexagonosAdyacentes) {
            if (hexagonosAdyacente.obtenerRecurso() != null) {
                recursos.add(hexagonosAdyacente.obtenerRecurso());
            }
        }
        return recursos;
    }
}
