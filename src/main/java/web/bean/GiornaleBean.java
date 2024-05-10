package web.bean;

import javafx.collections.ObservableList;
import laptop.model.raccolta.Raccolta;

import java.sql.Date;
import java.util.logging.Level;

public class GiornaleBean {


    private ObservableList<Raccolta> listaGiornaliB;
    private int idB;
    private String titoloB;

    public ObservableList<Raccolta> getListaGiornaliB() {
        return listaGiornaliB;
    }

    private String tipologiaB;
    private String linguaB;
    private String editoreB;

    private Date dataB;
    private int copieRimanentiB;

    public String getTipologiaB() {
        return tipologiaB;
    }

    public void setTipologiaB(String tipologiaB) {
        this.tipologiaB = tipologiaB;
    }

    public String getLinguaB() {
        return linguaB;
    }

    public void setLinguaB(String linguaB) {
        this.linguaB = linguaB;
    }

    public String getEditoreB() {
        return editoreB;
    }

    public void setEditoreB(String editoreB) {
        this.editoreB = editoreB;
    }

    public Date getDataB() {
        return dataB;
    }

    public void setDataB(Date dataB) {
        this.dataB = dataB;
    }

    public int getCopieRimanentiB() {
        return copieRimanentiB;
    }

    public void setCopieRimanentiB(int copieRimanentiB) {
        this.copieRimanentiB = copieRimanentiB;
    }



    public float getPrezzoB() {
        return prezzoB;
    }

    public void setPrezzoB(float prezzoB) {
        this.prezzoB = prezzoB;
    }

    private int disponibilitaB;
    private float prezzoB;

    public void setListaGiornaliB(ObservableList<Raccolta> listaGiornaliB) {
        this.listaGiornaliB = listaGiornaliB;
    }

    public int getIdB() {
        return idB;
    }

    public void setIdB(int idB)  {
        if(idB<1 )
        {
            this.idB=0;
            java.util.logging.Logger.getLogger("Test set id").log(Level.INFO,"id <1!!");
        }

        this.idB = idB;
    }

    public String getTitoloB() {
        return titoloB;
    }

    public void setTitoloB(String titoloB) {
        this.titoloB = titoloB;
    }


    private Exception mexB;

    public Exception getMexB() {
        return mexB;
    }

    public void setMexB(Exception mexB) {
        this.mexB = mexB;
    }

    public int getDisponibilitaB() {
        return disponibilitaB;
    }

    public void setDisponibilitaB(int disponibilitaB) {
        this.disponibilitaB = disponibilitaB;
    }
}
