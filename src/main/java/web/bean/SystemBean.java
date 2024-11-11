package web.bean;

public class SystemBean {
    public String getTypeB() {
        return typeB;
    }

    public void setTypeB(String type) {
        this.typeB = type;
    }

    public void setTypeAsBook()
    {
        setTypeB("libro");
    }
    public void setTypeAsMagazine()
    {
        setTypeB("rivista");
    }
    public void setTypeAsDaily()
    {
        setTypeB("giornale");
    }




    private static SystemBean instance=null;

    public static SystemBean getInstance() {
        if(instance==null)
            instance=new SystemBean();
        return instance;
    }
    private SystemBean(){}

    private String typeB;

    private String titoloB;
    private String metodoPB;



    public String getMetodoPB() {
        return metodoPB;
    }

    public void setMetodoPB(String metodoPB) {
        this.metodoPB = metodoPB;
    }

    public String getTitoloB() {
        return titoloB;
    }

    public void setTitoloB(String titoloB) {
        this.titoloB = titoloB;
    }

    public int getIdB() {
        return idB;
    }

    public void setIdB(int idB) {
        this.idB = idB;
    }

    private int idB;

    public int getQuantitaB() {
        return quantitaB;
    }

    public void setQuantitaB(int quantitaB) {
        this.quantitaB = quantitaB;
    }

    private int quantitaB;

    private float spesaTB;

    public float getSpesaTB() {
        return spesaTB;
    }

    public void setSpesaTB(float spesaTB) {
        this.spesaTB = spesaTB;
    }

    private boolean negozioSelezionatoB;

    public boolean isNegozioSelezionatoB() {
        return negozioSelezionatoB;
    }

    public void setNegozioSelezionatoB(boolean negozioSelezionatoB) {
        this.negozioSelezionatoB = negozioSelezionatoB;
    }

    private String categoriaB;

    public String getCategoriaB() {
        return categoriaB;
    }

    public void setCategoriaB(String categoriaB) {
        this.categoriaB = categoriaB;
    }


    private  boolean loggedB;

    public String getTypeOfModif() {
        return typeOfModif;
    }

    public void setTypeOfModif(String typeOfModif) {
        this.typeOfModif = typeOfModif;
    }

    private String typeOfModif;


    public boolean isLoggedB() {
        return loggedB;
    }

    public void setLoggedB(boolean loggedB) {
        this.loggedB = loggedB;
    }
}
