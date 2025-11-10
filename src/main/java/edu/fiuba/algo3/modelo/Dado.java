package edu.fiuba.algo3.modelo;

import java.util.Random;

public class Dado {
    private Random random;

    public Dado() {
        this.random = new Random();
    }

    public int tirar() {
        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        return dado1 + dado2;
    }
}
