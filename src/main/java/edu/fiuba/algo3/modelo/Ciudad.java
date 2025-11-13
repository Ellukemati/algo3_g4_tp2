package edu.fiuba.algo3.modelo;

public class Ciudad extends Colocable{
    private String identificador = "V";
    private String color;

    public Ciudad(String color) {
        this.color = color;
    }

    public void colocarEnTablero(Tablero tablero, int x, int y) {
        this.x = x;
        this.y = y;
        tablero.mejorarPoblado(this, x, y);

    }



    public boolean esMejorable(Colocable colocable) {
        Poblado poblado = new Poblado(color);
        return poblado.equals(colocable);
    }

}
