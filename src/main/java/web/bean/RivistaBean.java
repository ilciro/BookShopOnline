package web.bean;

import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.raccolta.CategorieRivista;
import laptop.model.raccolta.Raccolta;

import java.sql.Date;
import java.util.logging.Level;

public class RivistaBean {
    private ObservableList<Raccolta> listaRivisteB;
    private int idB;
    private String titoloB;
    private Exception mexB;

    private String tipologiaB;
    private String autoreB;
    private String linguaB;
    private String editoreB;
    private String descrizioneB;
    private Date dataB;
    private float prezzoB;
    private int copieRimB;
    private int dispB;

    public String getTipologiaB() {
        return tipologiaB;
    }

    public void setTipologiaB(String tipologiaB) {
        switch (tipologiaB) {
            case "SETTIMANALE"->this.tipologiaB = CategorieRivista.SETTIMANALE.toString();
            case "BISETTIMANALE" ->this.tipologiaB = CategorieRivista.BISETTIMANALE.toString();
            case "MENSILE"->  this.tipologiaB = CategorieRivista.MENSILE.toString();
            case "BIMESTRALE"-> this.tipologiaB = CategorieRivista.BIMESTRALE.toString();
            case "TRIMESTRALE"-> this.tipologiaB = CategorieRivista.TRIMESTRALE.toString();
            case "ANNUALE"-> this.tipologiaB = CategorieRivista.ANNUALE.toString();
            case "ESTIVO"-> this.tipologiaB = CategorieRivista.ESTIVO.toString();
            case "INVERNALE"-> this.tipologiaB = CategorieRivista.INVERNALE.toString();
            case "SPORTIVO"->    this.tipologiaB = CategorieRivista.SPORTIVO.toString();
            case "CINEMATOGRAFICA"->    this.tipologiaB = CategorieRivista.CINEMATOGRAFICA.toString();
            case "GOSSIP"->    this.tipologiaB = CategorieRivista.GOSSIP.toString();
            case "TELEVISIVO"->    this.tipologiaB = CategorieRivista.TELEVISIVO.toString();
            case "MILITARE"->    this.tipologiaB = CategorieRivista.MILITARE.toString();
            case "INFORMATICA"->    this.tipologiaB = CategorieRivista.INFORMATICA.toString();
            default -> this.tipologiaB=null;



        }

    }

    public String getAutoreB() {
        return autoreB;
    }

    public void setAutoreB(String autoreB) {
        this.autoreB = autoreB;
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

    public String getDescrizioneB() {
        return descrizioneB;
    }

    public void setDescrizioneB(String descrizioneB) {
        this.descrizioneB = descrizioneB;
    }

    public Date getDataB() {
        return dataB;
    }

    public void setDataB(Date dataB) {
        this.dataB = dataB;
    }

    public float getPrezzoB() {
        return prezzoB;
    }

    public void setPrezzoB(float prezzoB) {
        this.prezzoB = prezzoB;
    }

    public int getCopieRimB() {
        return copieRimB;
    }

    public void setCopieRimB(int copieRimB) {
        this.copieRimB = copieRimB;
    }

    public ObservableList<Raccolta> getListaRivisteB() {
        return listaRivisteB;
    }

    public void setListaRivisteB(ObservableList<Raccolta> listaRivisteB) {
        this.listaRivisteB = listaRivisteB;
    }

    public int getIdB() {
        return idB;
    }

    public void setIdB(int idB) {
        try {
            if (idB < 1) {
                throw new IdException("id incorrect");

            }
        }catch (IdException e)
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

    public Exception getMexB() {
        return mexB;
    }

    public void setMexB(Exception mexB) {
        this.mexB = mexB;
    }

    public int getDispB() {
        return dispB;
    }

    public void setDispB(int dispB) {
        this.dispB = dispB;
    }
}
