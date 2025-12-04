package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.CartaDesarollo;
import javafx.scene.control.Button;

import javax.smartcardio.Card;

public interface CartaDesarolloRenderizador {

    Button caballeroRenderizar(CartaDesarollo cartaDesarollo);
    Button contruccionDeCarreterasRenderizar(CartaDesarollo cartaDesarollo);
    Button descubrimientoRenderizar(CartaDesarollo cartaDesarollo);
    Button monopolioRenderizar(CartaDesarollo cartaDesarollo);
    Button puntoDeVictoriaRenderizar(CartaDesarollo cartaDesarollo);
}
