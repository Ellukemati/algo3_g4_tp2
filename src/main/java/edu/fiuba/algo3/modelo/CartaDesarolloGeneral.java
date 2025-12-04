package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.vistas.CartaDesarolloRenderizador;

public abstract class CartaDesarolloGeneral implements CartaDesarollo{

    @Override
    public boolean equals(Object o) {
        return o != null && this.getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
