package web.bean;

import javafx.collections.ObservableList;
import laptop.model.Pagamento;

public class PagamentoBean {
    private int idB;
    private String metodoB;
    private float ammontareB;
    private int esitoB;

    private String nomeUtenteB;
    private String tipoB;
    private String emailB;

    public String getEmailB() {
        return emailB;
    }

    public void setEmailB(String emailB) {
        this.emailB = emailB;
    }

    private ObservableList<Pagamento> listaPagamentiB;

    public int getIdB() {
        return idB;
    }

    public void setIdB(int idB) {
        this.idB = idB;
    }

    public String getMetodoB() {
        return metodoB;
    }

    public void setMetodoB(String metodoB) {
        this.metodoB = metodoB;
    }

    public float getAmmontareB() {
        return ammontareB;
    }

    public void setAmmontareB(float ammontareB) {
        this.ammontareB = ammontareB;
    }

    public int getEsitoB() {
        return esitoB;
    }

    public void setEsitoB(int esitoB) {
        this.esitoB = esitoB;
    }

    public String getNomeUtenteB() {
        return nomeUtenteB;
    }

    public void setNomeUtenteB(String nomeUtenteB) {
        this.nomeUtenteB = nomeUtenteB;
    }

    public String getTipoB() {
        return tipoB;
    }

    public void setTipoB(String tipoB) {
        this.tipoB = tipoB;
    }
    private int idOggettoB;

    public int getIdOggettoB() {
        return idOggettoB;
    }

    public void setIdOggettoB(int idOggettoB) {
        this.idOggettoB = idOggettoB;
    }

    public ObservableList<Pagamento> getListaPagamentiB() {
        return listaPagamentiB;
    }

    public void setListaPagamentiB(ObservableList<Pagamento> listaPagamentiB) {
        this.listaPagamentiB = listaPagamentiB;
    }

    @Override
    public String toString() {
        return "Pagamento [id=" + idB + ", metodo=" + metodoB + " , nomeUtente=" + nomeUtenteB
                + ", ammontare=" + ammontareB + ", tipo=" + tipoB+ ", idOggetto="+idOggettoB+"]";
    }
}
