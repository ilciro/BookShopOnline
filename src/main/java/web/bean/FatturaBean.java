package web.bean;

import java.util.Objects;

public class FatturaBean {
    private String nomeB;
    private String cognomeB;
    private String indirizzoB;
    private String comunicazioniB;

    public String getNomeB() {
        return nomeB;
    }

    public void setNomeB(String nomeB) {
        if(Objects.equals(nomeB, ""))
            this.nomeB="";
        this.nomeB = nomeB;
    }

    public String getCognomeB() {

        return cognomeB;
    }

    public void setCognomeB(String cognomeB) {
        if(Objects.equals(cognomeB, ""))
            this.cognomeB="";
        this.cognomeB = cognomeB;
    }

    public String getIndirizzoB() {

        return indirizzoB;
    }

    public void setIndirizzoB(String indirizzoB) {
        if(indirizzoB.isEmpty())
            this.indirizzoB="";
        this.indirizzoB = indirizzoB;
    }

    public String getComunicazioniB() {
        return comunicazioniB;
    }

    public void setComunicazioniB(String comunicazioniB) {
        this.comunicazioniB = comunicazioniB;
    }
}
