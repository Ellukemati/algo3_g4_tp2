package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;

public class Arista {
    private int id;
    private  List<Arista> aristasAdyacentes;
    private boolean ocupado;

    public Arista(int id) {
        this.id = id;
        this.aristasAdyacentes = new ArrayList<>();
        this.ocupado = false;
    }

    public void agregarAristaAdyacente(Arista arista) {
        aristasAdyacentes.add(arista);
    }

}
