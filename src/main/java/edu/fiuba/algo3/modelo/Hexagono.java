package edu.fiuba.algo3.modelo;

import java.util.Random;

public class Hexagono {
    private final Recurso recurso;
    private final int numero;
    private boolean ladron;

    public Hexagono(Recurso recurso, int numero) {
        this.recurso = recurso;
        this.numero = numero;
        this.ladron = false;
    }

    public Recurso VerificarNumero(int numero) {
        if (numero == this.numero && !this.ladron)
            return this.recurso;
        else
            return null;
    }

    public Recurso obtenerRecurso() {
        if (ladron) {
            return null;
        }
        return this.recurso;
    }

    public void ladronOcupar() {
        this.ladron = true;
    }

    public void ladronDesocupar() {
        this.ladron = false;
    }

    public int getNumero() {
        return this.numero;
    }

}
