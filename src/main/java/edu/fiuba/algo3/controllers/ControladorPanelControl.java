package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Observador;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class ControladorPanelControl implements Observador {

    @FXML private Label lblNombre;
    @FXML private Label lblPuntos;

    private Jugador jugador;
    private Runnable accionAbrirComercio; // Una "función" que guardamos para ejecutar después

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
        this.jugador.agregarObservador(this); // Escuchamos cambios
        actualizarDatos();
    }

    public void setOnAbrirComercio(Runnable accion) {
        this.accionAbrirComercio = accion;
    }

    @FXML
    public void abrirComercio() {
        if (accionAbrirComercio != null) {
            accionAbrirComercio.run(); // ¡Ejecutamos la acción! (Mostrar panel intercambio)
        }
    }

    @Override
    public void actualizar() {
        actualizarDatos();
        if (jugador.obtenerPuntajeTotal() >= 10) {
            mostrarVictoria();
        }
    }

    private void actualizarDatos() {
        if (jugador != null) {
            lblNombre.setText("Jugador: " + jugador.obtenerNombre());
            lblPuntos.setText("Puntos de Victoria: " + jugador.obtenerPuntajeTotal());
        }
    }

    private void mostrarVictoria() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Juego Terminado!");
        alert.setHeaderText(null);
        alert.setContentText("¡Felicidades " + jugador.obtenerNombre() + "! Has ganado.");
        alert.showAndWait();
        System.exit(0);
    }
}