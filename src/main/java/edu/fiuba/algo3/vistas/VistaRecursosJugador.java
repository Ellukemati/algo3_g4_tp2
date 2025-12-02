package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.controllers.CatanController;
import edu.fiuba.algo3.modelo.Recurso;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class VistaRecursosJugador extends VBox {
    private final CatanController controlador;
    private final Label nombreJugadorLabel;
    private final GridPane recursosGrid;

    public VistaRecursosJugador(CatanController controlador) {
        this.controlador = controlador;

        // Configurar estilo - más compacto para esquina inferior
        this.setPadding(new Insets(15));
        this.setSpacing(8);
        this.setStyle(
                "-fx-background-color: rgba(240, 240, 240, 0.9); " +
                        "-fx-border-color: #333; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 5; " +
                        "-fx-background-radius: 5;"
        );
        this.setMaxWidth(300); // Limitar ancho máximo

        // Título
        nombreJugadorLabel = new Label("Recursos del Jugador Actual");
        nombreJugadorLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #2c3e50;");

        // Grid para recursos - más compacto
        recursosGrid = new GridPane();
        recursosGrid.setHgap(8);
        recursosGrid.setVgap(5);
        recursosGrid.setPadding(new Insets(5, 0, 0, 0));

        this.getChildren().addAll(nombreJugadorLabel, recursosGrid);

        // Actualizar vista inicial
        actualizarVista();
    }

    public void actualizarVista() {
        // Actualizar nombre del jugador
        nombreJugadorLabel.setText("Recursos: " + controlador.getNombreJugadorActual());

        // Limpiar grid
        recursosGrid.getChildren().clear();

        // Obtener recursos actuales
        Map<Recurso, Integer> recursos = controlador.obtenerRecursosJugadorActual();

        int row = 0;
        for (Recurso recurso : Recurso.values()) {
            // Crear contenedor horizontal para cada recurso
            HBoxRecurso hboxRecurso = new HBoxRecurso(recurso, recursos.get(recurso));

            // Agregar al grid
            recursosGrid.add(hboxRecurso, 0, row);

            row++;
        }
    }

    // Clase interna para mejor organización de cada recurso
    private static class HBoxRecurso extends javafx.scene.layout.HBox {
        public HBoxRecurso(Recurso recurso, int cantidad) {
            this.setSpacing(8);
            this.setAlignment(Pos.CENTER_LEFT);

            // Ícono
            Rectangle icono = new Rectangle(16, 16);
            icono.setFill(MapaDeColores.obtenerColor(recurso));
            icono.setArcWidth(3);
            icono.setArcHeight(3);

            // Nombre del recurso
            Label nombreLabel = new Label(recurso.name() + ":");
            nombreLabel.setStyle("-fx-font-size: 12px; -fx-min-width: 80;");

            // Cantidad
            Label cantidadLabel = new Label(String.valueOf(cantidad));
            cantidadLabel.setStyle(
                    "-fx-font-weight: bold; " +
                            "-fx-font-size: 14px; " +
                            "-fx-background-color: white; " +
                            "-fx-padding: 2 8; " +
                            "-fx-border-color: #ccc; " +
                            "-fx-border-width: 1; " +
                            "-fx-border-radius: 3; " +
                            "-fx-background-radius: 3;"
            );
            cantidadLabel.setMinWidth(30);
            cantidadLabel.setAlignment(Pos.CENTER);

            this.getChildren().addAll(icono, nombreLabel, cantidadLabel);
        }
    }
}
