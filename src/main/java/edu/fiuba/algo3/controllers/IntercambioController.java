package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox; // O la raíz que uses

public class IntercambioController {

    // Spinners inyectados
    @FXML private Spinner<Integer> spinMaderaDar;
    @FXML private Spinner<Integer> spinMaderaRecibir;
    @FXML private Spinner<Integer> spinLadrilloDar;
    @FXML private Spinner<Integer> spinLadrilloRecibir;
    @FXML private Spinner<Integer> spinLanaDar;
    @FXML private Spinner<Integer> spinLanaRecibir;
    @FXML private Spinner<Integer> spinGranoDar;
    @FXML private Spinner<Integer> spinGranoRecibir;
    @FXML private Spinner<Integer> spinMineralDar;
    @FXML private Spinner<Integer> spinMineralRecibir;

    private Jugador jugadorActual; // Referencia al modelo

    @FXML
    public void initialize() {
        // Configuramos la lógica para cada par de recursos
        configurarPar(spinMaderaDar, spinMaderaRecibir);
        configurarPar(spinLadrilloDar, spinLadrilloRecibir);
        configurarPar(spinLanaDar, spinLanaRecibir);
        configurarPar(spinGranoDar, spinGranoRecibir);
        configurarPar(spinMineralDar, spinMineralRecibir);
    }

    public void setJugador(Jugador jugador) {
        this.jugadorActual = jugador;
    }

    private void configurarPar(Spinner<Integer> dar, Spinner<Integer> recibir) {
        // Rangos 0-19
        dar.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 19, 0));
        recibir.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 19, 0));

        // Exclusión Mutua: Si subes "Dar", "Recibir" se va a 0 y se bloquea
        dar.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal > 0) {
                recibir.getValueFactory().setValue(0);
                recibir.setDisable(true);
            } else {
                recibir.setDisable(false);
            }
        });

        recibir.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal > 0) {
                dar.getValueFactory().setValue(0);
                dar.setDisable(true);
            } else {
                dar.setDisable(false);
            }
        });
    }

    @FXML
    public void confirmar() {
        // Aquí recolectarías los valores de los spinners en Mapas
        // y llamarías a jugadorActual.comerciarConBanca(...) o intercambiar(...)
        System.out.println("Intercambio enviado al modelo!");
    }

    @FXML
    public void cancelar() {
        // Lógica para ocultar el panel (se puede hacer accediendo al nodo padre)
        spinMaderaDar.getScene().getWindow().hide(); // Ejemplo si fuera ventana, o ocultar panel
    }
}