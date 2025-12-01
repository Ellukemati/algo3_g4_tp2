package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Recurso;
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;

public class MapaDeColores {
    private static final Map<Recurso, Color> colores;

    static {
        colores = new HashMap<>();
        colores.put(Recurso.MADERA, Color.SADDLEBROWN);
        colores.put(Recurso.LADRILLO, Color.FIREBRICK);
        colores.put(Recurso.LANA, Color.LIGHTGREEN);
        colores.put(Recurso.GRANO, Color.GOLDENROD);
        colores.put(Recurso.MINERAL, Color.DARKGRAY);
    }

    public static Color obtenerColor(Recurso recurso) {
        // Si es null desierto, devolvemos un color arena
        return colores.getOrDefault(recurso, Color.SANDYBROWN);
    }
}