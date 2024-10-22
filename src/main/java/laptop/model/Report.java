package laptop.model;

public class Report {
    //class used for generate report

    private int idReport;
    private String tipologiaOggetto;
    private String titoloOggetto;
    private int nrPezzi;
    private float prezzo;
    private float totale;

    public int getIdReport() {
        return idReport;
    }

    public void setIdReport(int idReport) {
        this.idReport = idReport;
    }

    public int getNrPezzi() {
        return nrPezzi;
    }

    public void setNrPezzi(int nrPezzi) {
        this.nrPezzi = nrPezzi;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public String getTipologiaOggetto() {
        return tipologiaOggetto;
    }

    public void setTipologiaOggetto(String tipologiaOggetto) {
        this.tipologiaOggetto = tipologiaOggetto;
    }

    public String getTitoloOggetto() {
        return titoloOggetto;
    }

    public void setTitoloOggetto(String titoloOggetto) {
        this.titoloOggetto = titoloOggetto;
    }

    public float getTotale() {
        return totale;
    }

    public void setTotale(float totale) {
        this.totale = totale;
    }
}
