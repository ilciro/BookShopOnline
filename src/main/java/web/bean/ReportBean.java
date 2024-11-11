package web.bean;

import javafx.collections.ObservableList;
import laptop.model.Report;
import laptop.model.raccolta.Raccolta;

public class ReportBean {
    private int idReportB;
    private String tipoOggettoB;
    private String titoloB;
    private int nrPezziB;
    private float prezzoB;
    private float totaleB;
    private ObservableList<Raccolta> elencoReportB;

    public ObservableList<Report> getElencoReportBRepo() {
        return elencoReportBRepo;
    }

    public void setElencoReportBRepo(ObservableList<Report> elencoReportBRepo) {
        this.elencoReportBRepo = elencoReportBRepo;
    }

    private ObservableList<Report> elencoReportBRepo;

    public ObservableList<Raccolta> getElencoReportB() {
        return elencoReportB;
    }

    public void setElencoReportB(ObservableList<Raccolta> elencoReportB) {
        this.elencoReportB = elencoReportB;
    }

    public int getIdReportB() {
        return idReportB;
    }

    public void setIdReportB(int idReportB) {
        this.idReportB = idReportB;
    }

    public int getNrPezziB() {
        return nrPezziB;
    }

    public void setNrPezziB(int nrPezziB) {
        this.nrPezziB = nrPezziB;
    }

    public float getPrezzoB() {
        return prezzoB;
    }

    public void setPrezzoB(float prezzoB) {
        this.prezzoB = prezzoB;
    }

    public String getTipoOggettoB() {
        return tipoOggettoB;
    }

    public void setTipoOggettoB(String tipoOggettoB) {
        this.tipoOggettoB = tipoOggettoB;
    }

    public String getTitoloB() {
        return titoloB;
    }

    public void setTitoloB(String titoloB) {
        this.titoloB = titoloB;
    }

    public float getTotaleB() {
        return totaleB;
    }

    public void setTotaleB(float totaleB) {
        this.totaleB = totaleB;
    }
}
