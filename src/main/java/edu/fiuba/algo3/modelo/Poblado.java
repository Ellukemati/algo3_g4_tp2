package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.Objects;

public class Poblado extends Colocable {
    private String identificador = "V";
    private String color;

    private boolean esValido(ArrayList<Colocable> adyacentes) {
        if (adyacentes.isEmpty()) {
            return true;
        }
        throw new PosicionInvalidaException("posicion no valido");
        //return adyacentes.isEmpty();
    }

    public Poblado(String color) {
        this.color = color;
    }

    public void colocarEnTablero(Tablero tablero, int x, int y) {
        this.x = x;
        this.y = y;
        ArrayList<Colocable> adyacentes = tablero.obtenerAdyacentes(x, y, "V");
        if (esValido(adyacentes)) {
            tablero.colocarColocable(this, x, y, identificador);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Poblado poblado = (Poblado) o;
        return Objects.equals(identificador, poblado.identificador) && Objects.equals(color, poblado.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador, color);
    }
}
