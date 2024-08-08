package web.bean;

public class AcquistaBean {
    public String getTitoloB() {
        return titoloB;
    }

    public void setTitoloB(String titoloB) {
        this.titoloB = titoloB;
    }

    private String titoloB;

    public float getPrezzoB() {
        return prezzoB;
    }

    public void setPrezzoB(float prezzoB) {
        this.prezzoB = prezzoB;
    }

    private float prezzoB;

    private Exception mexB;

    public Exception getMexB() {
        return mexB;
    }

    public void setMexB(Exception mexB) {
        this.mexB = mexB;
    }
}
