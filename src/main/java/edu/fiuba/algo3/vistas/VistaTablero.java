package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Hexagono;
import edu.fiuba.algo3.modelo.Tablero;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import java.util.List;

public class VistaTablero extends Pane {
    private final Tablero tablero;
    private final double radioHexagono = 50; // Tamaño de los hexágonos

    // Constantes para calcular posiciones (geometría hexagonal)
    private final double ANCHO = Math.sqrt(3) * radioHexagono;
    private final double ALTO = 2 * radioHexagono;

    // Offsets para coordenadas axiales (q, r) para los 19 hexágonos en espiral
    // Esto mapea el índice de la lista del modelo (0-18) a una posición (q,r)
    private final int[][] coordenadasAxiales = {
            {0, 0},   // Centro (Hex 0)
            {1, -1}, {1, 0}, {0, 1}, {-1, 1}, {-1, 0}, {0, -1}, // Primer Anillo (1-6)
            {2, -2}, {2, -1}, {2, 0}, {1, 1}, {0, 2}, {-1, 2}, {-2, 2}, {-2, 1}, {-2, 0}, {-1, -1}, {0, -2}, {1, -2} // Segundo Anillo (7-18)
    };

    public VistaTablero(Tablero tablero) {
        this.tablero = tablero;
        dibujar();
    }

    private void dibujar() {
        List<Hexagono> hexagonos = tablero.obtenerHexagonos();
        Group grupoHexagonos = new Group();

        // Centro de la pantalla (ajustar según tamaño de ventana)
        double centroX = 400;
        double centroY = 350;

        for (int i = 0; i < hexagonos.size(); i++) {
            if (i >= coordenadasAxiales.length) break; // Seguridad

            int q = coordenadasAxiales[i][0];
            int r = coordenadasAxiales[i][1];

            // Conversión de coordenadas Axiales a Cartesianas (Pixels)
            double x = centroX + radioHexagono * (Math.sqrt(3) * q + Math.sqrt(3)/2 * r);
            double y = centroY + radioHexagono * (3.0/2 * r);

            VistaHexagono vistaHex = new VistaHexagono(hexagonos.get(i), radioHexagono);

            // Ubicar el hexágono
            vistaHex.setLayoutX(x);
            vistaHex.setLayoutY(y);

            grupoHexagonos.getChildren().add(vistaHex);
        }

        this.getChildren().add(grupoHexagonos);
    }
}