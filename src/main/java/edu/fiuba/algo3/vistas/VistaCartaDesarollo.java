package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Descubrimiento;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class VistaCartaDesarollo {

    public HBox mostraCartaDesarollo(HBox contenedor) {
        BotonCartaDesarolloVista carta = new BotonCartaDesarolloVista();
        contenedor.getChildren().add(carta.caballeroRenderizar(new Descubrimiento()));
        contenedor.getChildren().add(carta.caballeroRenderizar(new Descubrimiento()));
        return contenedor;
    }
}
