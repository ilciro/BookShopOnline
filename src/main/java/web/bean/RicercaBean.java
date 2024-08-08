package web.bean;

import javafx.collections.ObservableList;
import laptop.model.raccolta.Raccolta;

public class RicercaBean {
    private ObservableList<Raccolta> listaB;
    private String titoloB;

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


    public ObservableList<Raccolta> getListaB() {
        return listaB;
    }

    public void setListaB(ObservableList<Raccolta> listaB) {
        this.listaB = listaB;
    }

    public String getEditoreB() {
        return editoreB;
    }

    public void setEditoreB(String editoreB) {
        this.editoreB = editoreB;
    }
}
