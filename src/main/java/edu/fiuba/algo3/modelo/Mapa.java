package edu.fiuba.algo3.modelo;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Mapa {
    private Map<String, int[]> rangos = new HashMap<>() {
        {
            put("A", new int[]{2, 2});
            put("V", new int[]{2, 2});
            put("E", new int[]{3, 2});
        }
    };
    private String[][] mapa;

    private void inicializacion(String ruta){
        try (CSVReader reader = new CSVReader(new FileReader(ruta))) {
            String[] fila;
            fila = reader.readNext();
            int cantidadFilas = Integer.parseInt(fila[0]);
            int cantidadColumnas = Integer.parseInt(fila[1]);
            this.mapa = new String[cantidadFilas][cantidadColumnas];
            int indicefila = 0;

            while ((fila = reader.readNext()) != null) {
                for(int i=0; i < fila.length; i++){
                    this.mapa[indicefila][i] = fila[i];
                }
                indicefila++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            System.err.println("Error en el formato del CSV: " + e.getMessage());
        }
    }

    private int[] obtenerPosicionInicial(int x , int y, String identificador) {
        int[] rango =  this.rangos.get(identificador);
        return new int[]{x - rango[0], y - rango[1]};
    }
    public Mapa(String  ruta) {
        inicializacion(ruta);
    }

    public ArrayList<int[]> obtenerPosicionesAdyacentes(int x, int y, String identificador) {
        ArrayList<int[]> posicionesAdyacentes = new ArrayList<>();
        int[] posicion = obtenerPosicionInicial(x, y, identificador);
        int[] rango =  this.rangos.get(identificador);
        for(int i = posicion[0]; i <= (x + rango[0]); i++){
            for(int j = posicion[1]; j <= (y + rango[1]); j++){
                /*System.out.println("--------------------------");
                System.out.println(identificador);
                System.out.println(mapa[i][j].toUpperCase());*/
                if (identificador.equals(this.mapa[i][j].toUpperCase()) && !(i == x && j == y )){
                    posicionesAdyacentes.add(new int[]{i,j});
                }
            }
        }
        return posicionesAdyacentes;
    }

    public boolean esPocicionValida(int x, int y, String identificador) {
        if (mapa[x][y].equalsIgnoreCase(identificador)) {
            return true;
        }
        return false;
    }

    public  int[] calcularTamañoMapa() {
        return new int[] {mapa.length, mapa[0].length};
    }

    public Queue<int[]> calcularPosicionesExagonos(){
        Queue<int[]> posicionesExagonos = new LinkedList<>();
        for (int i = 0; i< mapa.length; i++){

            for (int j = 0; j < mapa[0].length; j++){
                if (mapa[i][j].equalsIgnoreCase("E")) {
                    posicionesExagonos.add(new int[]{i, j});
                }
            }
        }
        return posicionesExagonos;
    }
}

