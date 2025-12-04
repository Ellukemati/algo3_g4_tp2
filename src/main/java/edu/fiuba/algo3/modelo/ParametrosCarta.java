package edu.fiuba.algo3.modelo;

public class ParametrosCarta {
    private int idArista1;
    private int idArista2;
    private int posicionLadron;
    private Recurso recurso1;
    private Recurso recurso2;
    private Recurso recursoAPedir;

    public void setRecursoAPedir(Recurso recursoAPedir) {
        this.recursoAPedir = recursoAPedir;
    }

    public void setIdArista1(int idArista1) {
        this.idArista1 = idArista1;
    }

    public void setIdArista2(int idArista2) {
        this.idArista2 = idArista2;
    }

    public void setPosicionLadron(int posicionLadron) {
        this.posicionLadron = posicionLadron;
    }

    public void setRecurso1(Recurso recurso1) {
        this.recurso1 = recurso1;
    }

    public void setRecurso2(Recurso recurso2) {
        this.recurso2 = recurso2;
    }

    public Recurso getRecursoAPedir() {
        return recursoAPedir;
    }

    public int getIdArista1() {
        return idArista1;
    }

    public int getIdArista2() {
        return idArista2;
    }

    public int getPosicionLadron() {
        return posicionLadron;
    }

    public Recurso getRecurso1() {
        return recurso1;
    }

    public Recurso getRecurso2() {
        return recurso2;
    }
}
