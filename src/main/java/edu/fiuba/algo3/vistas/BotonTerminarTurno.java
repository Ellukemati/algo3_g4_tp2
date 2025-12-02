package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.controllers.CatanController;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class BotonTerminarTurno extends HBox {
    private final CatanController controlador;
    private final VistaRecursosJugador vistaRecursos;

    public BotonTerminarTurno(CatanController controlador, VistaRecursosJugador vistaRecursos) {
        this.controlador = controlador;
        this.vistaRecursos = vistaRecursos;

        Button btnCambiarTurno = new Button("Cambiar Turno");
        btnCambiarTurno.setOnAction(e -> {
            controlador.cambiarTurno();
            vistaRecursos.actualizarVista();
            // También podrías actualizar otras vistas aquí
        });

        Button btnLanzarDado = new Button("Lanzar Dado");
        btnLanzarDado.setOnAction(e -> {
            // Lógica para lanzar dado
            vistaRecursos.actualizarVista();
        });

        this.getChildren().addAll(btnLanzarDado, btnCambiarTurno);
        this.setSpacing(10);
    }
}
