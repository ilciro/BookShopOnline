package web.bean;

import javafx.collections.ObservableList;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;

public class RicercaBean {

    private String titoloB;

    private ObservableList<Libro> listaLB;
    private ObservableList<Giornale> listaGB;
    private ObservableList<Rivista> listaRB;

    private Exception mexB;

    public Exception getMexB() {
        return mexB;
    }

    public void setMexB(Exception mexB) {
        this.mexB = mexB;
    }

    public ObservableList<Giornale> getListaGB() {
        return listaGB;
    }

    public void setListaGB(ObservableList<Giornale> listaGB) {
        this.listaGB = listaGB;
    }

    public ObservableList<Libro> getListaLB() {
        return listaLB;
    }

    public void setListaLB(ObservableList<Libro> listaLB) {
        this.listaLB = listaLB;
    }

    public ObservableList<Rivista> getListaRB() {
        return listaRB;
    }

    public void setListaRB(ObservableList<Rivista> listaRB) {
        this.listaRB = listaRB;
    }

    public String getTitoloB() {
        return titoloB;
    }

    public void setTitoloB(String titoloB) {
        this.titoloB = titoloB;
    }

    public String getAutoreB() {
        return autoreB;
    }

    public void setAutoreB(String autoreB) {
        this.autoreB = autoreB;
    }

    public int getIdB() {
        return idB;
    }

    public void setIdB(int idB) {
        this.idB = idB;
    }

    private String autoreB;
    private int idB;
    private String editoreB;




    public String getEditoreB() {
        return editoreB;
    }

    public void setEditoreB(String editoreB) {
        this.editoreB = editoreB;
    }
}
