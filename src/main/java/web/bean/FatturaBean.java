package web.bean;

import java.util.Objects;

public class FatturaBean {
    private String nomeB;
    private String cognomeB;
    private String indirizzoB;
    private String comunicazioniB;
    private float ammontareB;

    public float getAmmontareB() {
        return ammontareB;
    }

    public void setAmmontareB(float ammontareB) {
        this.ammontareB = ammontareB;
    }

    private int idFatturaB;

    public int getIdFatturaB() {
        return idFatturaB;
    }

    public void setIdFatturaB(int idFatturaB) {
        this.idFatturaB = idFatturaB;
    }

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

    @Override
    public String toString() {
        return "Fattura [nome=" + nomeB + ", cognome=" + cognomeB + ", via=" + indirizzoB + ", com=" + comunicazioniB + ", numero=" + idFatturaB
                + ", ammontare=" +  + ammontareB + "]";
    }

}
