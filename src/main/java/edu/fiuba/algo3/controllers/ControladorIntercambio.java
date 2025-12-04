package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.HashMap;
import java.util.Map;
import edu.fiuba.algo3.modelo.Observador;
public class ControladorIntercambio implements Observador {
    @FXML
    private VBox panelPrincipal;
    @FXML
    private Button btnDestinatario;
    @FXML
    private Button btnConfirmar;
    @FXML
    private Label lblBalance;

    @FXML
    private Spinner<Integer> spinMaderaDar, spinMaderaRecibir;
    @FXML
    private Spinner<Integer> spinLadrilloDar, spinLadrilloRecibir;
    @FXML
    private Spinner<Integer> spinLanaDar, spinLanaRecibir;
    @FXML
    private Spinner<Integer> spinGranoDar, spinGranoRecibir;
    @FXML
    private Spinner<Integer> spinMineralDar, spinMineralRecibir;

    private Jugador jugadorActual;
    private boolean modoBanca = true;
    private boolean isUpdating = false;
    private Map<Recurso, ParSpinners> mapaSpinners;

    private static class ParSpinners {
        Spinner<Integer> dar;
        Spinner<Integer> recibir;

        ParSpinners(Spinner<Integer> d, Spinner<Integer> r) {
            dar = d;
            recibir = r;
        }
    }

    @FXML
    public void initialize() {
        inicializarMapaSpinners();
        actualizarBotonDestinatario();
        validarEstado();
    }

    private void inicializarMapaSpinners() {
        mapaSpinners = new HashMap<>();
        mapaSpinners.put(Recurso.MADERA, new ParSpinners(spinMaderaDar, spinMaderaRecibir));
        mapaSpinners.put(Recurso.LADRILLO, new ParSpinners(spinLadrilloDar, spinLadrilloRecibir));
        mapaSpinners.put(Recurso.LANA, new ParSpinners(spinLanaDar, spinLanaRecibir));
        mapaSpinners.put(Recurso.GRANO, new ParSpinners(spinGranoDar, spinGranoRecibir));
        mapaSpinners.put(Recurso.MINERAL, new ParSpinners(spinMineralDar, spinMineralRecibir));
    }

    public void setJugador(Jugador jugador) {
        this.jugadorActual = jugador;
        this.jugadorActual.agregarObservador(this);
        refrescarVista();
    }

    public void limpiarFormulario() {
        refrescarVista();
    }

    private void refrescarVista() {
        if (jugadorActual == null) return;
        actualizarLogicaSpinners();
        validarEstado();
    }
    @Override
    public void actualizar() {
        // duando el jugador cambia (ej: gastó madera), recargamos los spinners
        refrescarVista();
    }

    @FXML
    public void alternarDestinatario() {
        this.modoBanca = !this.modoBanca;
        actualizarBotonDestinatario();
        limpiarFormulario();
    }

    private void actualizarBotonDestinatario() {
        btnDestinatario.getStyleClass().removeAll("btn-toggle-banca", "btn-toggle-jugador");
        if (modoBanca) {
            btnDestinatario.setText("BANCA");
            btnDestinatario.getStyleClass().add("btn-toggle-banca");
            btnConfirmar.setText("CONFIRMAR CANJE");
        } else {
            btnDestinatario.setText("JUGADORES");
            btnDestinatario.getStyleClass().add("btn-toggle-jugador");
            btnConfirmar.setText("ENVIAR OFERTA");
        }
    }

    private void actualizarLogicaSpinners() {
        for (Map.Entry<Recurso, ParSpinners> entry : mapaSpinners.entrySet()) {
            configurarSpinnerRecurso(entry.getValue().dar, entry.getValue().recibir, entry.getKey());
        }
    }

    private void configurarSpinnerRecurso(Spinner<Integer> dar, Spinner<Integer> recibir, Recurso recurso) {
        int stockDisponible = jugadorActual.cantidadDe(recurso);
        int paso = 1;
        int maximoPermitido = stockDisponible;

        if (modoBanca) {
            paso = jugadorActual.obtenerTasaDe(recurso);
            if (paso > 0) {
                maximoPermitido = (stockDisponible / paso) * paso;
            }
        }

        dar.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maximoPermitido, 0, paso));
        dar.setDisable(maximoPermitido == 0);

        // Configurar RECIBIR
        recibir.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 0, 1));
        recibir.setDisable(false);

        configurarExclusionMutua(dar, recibir);

        dar.valueProperty().addListener((o, v, n) -> validarEstado());
        recibir.valueProperty().addListener((o, v, n) -> validarEstado());
    }

    private void configurarExclusionMutua(Spinner<Integer> dar, Spinner<Integer> recibir) {
        dar.valueProperty().addListener((o, v, n) -> manejarCambioExclusivo(recibir, n));
        recibir.valueProperty().addListener((o, v, n) -> manejarCambioExclusivo(dar, n));
    }

    private void manejarCambioExclusivo(Spinner<Integer> spinnerPasivo, Integer nuevoValor) {
        if (isUpdating) return;

        if (nuevoValor > 0) {
            isUpdating = true;
            spinnerPasivo.getValueFactory().setValue(0);
            spinnerPasivo.setDisable(true);
            isUpdating = false;
        } else {
            int max = ((SpinnerValueFactory.IntegerSpinnerValueFactory) spinnerPasivo.getValueFactory()).getMax();
            if (max > 0) spinnerPasivo.setDisable(false);
        }
    }

    private void validarEstado() {
        if (jugadorActual == null) return;

        Map<Recurso, Integer> oferta = armarMapa(true);
        Map<Recurso, Integer> demanda = armarMapa(false);

        boolean ofertaVacia = oferta.isEmpty();
        boolean demandaVacia = demanda.isEmpty();

        if (ofertaVacia && demandaVacia) {
            actualizarFeedback(0, 0, false, "Selecciona recursos a DAR y RECIBIR");
            btnConfirmar.setDisable(true);
            return;
        }
        if (ofertaVacia) {
            actualizarFeedback(0, 0, false, "Selecciona recursos a DAR");
            btnConfirmar.setDisable(true);
            return;
        }
        if (demandaVacia) {
            actualizarFeedback(0, 0, false, "Selecciona recursos a RECIBIR");
            btnConfirmar.setDisable(true);
            return;
        }

        if (modoBanca) {
            int creditos = 0;
            for (Map.Entry<Recurso, Integer> e : oferta.entrySet()) {
                int tasa = jugadorActual.obtenerTasaDe(e.getKey());
                creditos += (e.getValue() / tasa);
            }
            int costo = demanda.values().stream().mapToInt(Integer::intValue).sum();
            boolean balanceado = (creditos == costo);

            actualizarFeedback(creditos, costo, balanceado, "");
            btnConfirmar.setDisable(!balanceado);
        } else {
            actualizarFeedback(0, 0, true, "");
            btnConfirmar.setDisable(false);
        }
    }

    private void actualizarFeedback(int creditos, int costo, boolean valido, String mensajeExtra) {
        if (lblBalance == null) return;

        lblBalance.getStyleClass().removeAll("feedback-neutro", "feedback-exito", "feedback-error");

        if (!mensajeExtra.isEmpty()) {
            lblBalance.setText(mensajeExtra);
            lblBalance.getStyleClass().add("feedback-neutro");
            return;
        }

        if (modoBanca) {
            lblBalance.setText("Créditos: " + creditos + " / Costo: " + costo);
            lblBalance.getStyleClass().add(valido ? "feedback-exito" : "feedback-error");
        } else {
            lblBalance.setText("");
        }
    }

    // CONFIRMACIÓN
    @FXML
    public void confirmar() {
        if (jugadorActual == null) return;

        Map<Recurso, Integer> oferta = armarMapa(true);
        Map<Recurso, Integer> demanda = armarMapa(false);

        if (oferta.isEmpty() || demanda.isEmpty()) return;

        if (modoBanca) {
            jugadorActual.comerciarConBanca(oferta, demanda);
            System.out.println("¡Comercio con Banca ejecutado!");
        } else {
            System.out.println("Intercambio con jugador enviado (Simulado)");
        }
        cerrarPanel();
    }

    @FXML
    public void cancelar() {
        cerrarPanel();
    }

    private void cerrarPanel() {
        panelPrincipal.setVisible(false);
        limpiarFormulario();
    }

    private Map<Recurso, Integer> armarMapa(boolean esDar) {
        Map<Recurso, Integer> mapa = new HashMap<>();

        for (Map.Entry<Recurso, ParSpinners> entry : mapaSpinners.entrySet()) {
            Spinner<Integer> spinner = esDar ? entry.getValue().dar : entry.getValue().recibir;
            if (spinner.getValue() != null && spinner.getValue() > 0) {
                mapa.put(entry.getKey(), spinner.getValue());
            }
        }
        return mapa;
    }
}
