package web.bean;

public class NegozioBean {
    private String nomeB;
    private boolean openB;

    public boolean isOpenB() {
        return openB;
    }

    public void setOpenB(boolean openB) {
        this.openB = openB;
    }

    public boolean isValidB() {
        return validB;
    }

    public void setValidB(boolean validB) {
        this.validB = validB;
    }

    private boolean validB;

    private String mexB;

    public String getNomeB() {
        return nomeB;
    }

    public void setNomeB(String nomeB) {
        this.nomeB = nomeB;
    }



    public String getMexB() {
        return mexB;
    }

    public void setMexB(String mexB) {
        this.mexB = mexB;
    }
}
