package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
    private int id;
    private List<Hexagono> hexagonos;
    private  List<Vertice> vertices;
    private boolean ocupado;

    public Vertice(int id) {
        this.id = id;
        this.hexagonos = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.ocupado = false;
    }
    public void agregarHexagono(Hexagono hexagono) {
        hexagonos.add(hexagono);
    }

    public void agregarVerticeAdyacente(Vertice vertice) {
        vertices.add(vertice);
    }

    public void ocuparVertice() {
        this.ocupado = true;
    }

    public boolean verificarOcupado() {
        return this.ocupado;
    }

    public List<Recurso> cosecharRecursos() {
        List<Recurso> recursos = new ArrayList<>();
        for (int i = 0; i < this.hexagonos.size(); i++) {
            if (hexagonos.get(i).obtenerRecurso() != null) {
                recursos.add(this.hexagonos.get(i).obtenerRecurso());
            }
        }
        return recursos;
    }
}
