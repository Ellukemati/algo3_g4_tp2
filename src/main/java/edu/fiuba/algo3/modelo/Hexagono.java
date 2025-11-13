package edu.fiuba.algo3.modelo;

import java.util.Random;

public class Hexagono extends Colocable{
    private Recurso recurso;
    private int numero;

    public Hexagono(Recurso recurso, int numero) {
        this.recurso = recurso;
        this.numero = numero;
    }

    public Recurso VerificarNumero(int numero) {
        if (numero == this.numero)
            return this.recurso;
        else
            return null;
    }

}
