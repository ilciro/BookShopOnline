package web.bean;

import javafx.collections.ObservableList;
import laptop.model.raccolta.Raccolta;


import java.util.List;

public class ModificaOggettoBean {
    private ObservableList<Raccolta> miaListaB;

    private List<String> listaCategorieB;

    public List<String> getListaCategorieB() {
        return listaCategorieB;
    }

    public void setListaCategorieB(List<String> listaCategorieB) {
        this.listaCategorieB = listaCategorieB;
    }

    private int idB;

    public ObservableList<Raccolta> getMiaListaB() {
        return miaListaB;
    }

    public void setMiaListaB(ObservableList<Raccolta> miaListaB) {
        this.miaListaB = miaListaB;
    }

    public int getIdB() {
        return idB;
    }

    public void setIdB(int idB) {
        if(idB<1 || idB> miaListaB.size())
            this.idB=0;
        this.idB = idB;
    }
}
