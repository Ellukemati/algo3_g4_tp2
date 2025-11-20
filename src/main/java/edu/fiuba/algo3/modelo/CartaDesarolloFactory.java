package edu.fiuba.algo3.modelo;

public class CartaDesarolloFactory {
    public static CartaDesarollo crearCartaDesarollo(Carta carta) {
        switch (carta) {
            case CABALLERO:
                return new Caballero();
            case CONSTRUCCIONDECARRETERA:
                return new ConstruccionDeCarreteras();
            case PUNTODEVICTORIA:
                return new PuntoDeVictoria();
            case DESCUBRIMIENTO:
                return new Descubrimiento();
            case MONOPOLIO:
                return new Monopolio();
            default: throw new IllegalArgumentException("Tipo desconocido");
        }

    }
}
