package web.bean;

import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.raccolta.CategorieLibro;
import laptop.model.raccolta.Raccolta;

import java.sql.Date;

public class LibroBean {



    private ObservableList<Raccolta> elencoLibriB;

    private Exception mexB;

    private int idB;
    private String titoloB;
    private String codIsbnB;
    private int numeroPagineB;
    private String autoreB;
    private String editoreB;
    private String linguaB;
    private String categoriaB;

    private Date dateB;

    private String recensioneB;
    private String descB;
    private int disponibilitaB;
    private float prezzoB;
    private int nrCopieB;

    public String getRecensioneB() {
        return recensioneB;
    }

    public void setRecensioneB(String recensioneB) {
        this.recensioneB = recensioneB;
    }

    public String getDescB() {
        return descB;
    }

    public void setDescB(String descB) {
        this.descB = descB;
    }

    public int getDisponibilitaB() {
        return disponibilitaB;
    }

    public void setDisponibilitaB(int disponibilitaB) {
        this.disponibilitaB = disponibilitaB;
    }

    public float getPrezzoB() {
        return prezzoB;
    }

    public void setPrezzoB(float prezzoB) {
        this.prezzoB = prezzoB;
    }

    public int getNrCopieB() {
        return nrCopieB;
    }

    public void setNrCopieB(int nrCopieB) {
        this.nrCopieB = nrCopieB;
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

    public void setIdB(int idB)  {
        if(idB<1 )
        {
            this.idB=0;
            setMexB( new IdException("id incorrect"));

        }

        this.idB = idB;
    }



    public ObservableList<Raccolta> getElencoLibriB() {
        return elencoLibriB;
    }

    public void setElencoLibriB(ObservableList<Raccolta> elencoLibriB) {
        this.elencoLibriB = elencoLibriB;
    }

    public Exception getMexB() {
        return mexB;
    }

    public void setMexB(Exception mexB) {
        this.mexB = mexB;
    }

    //user for calculate length


    public String getCategoriaB() {
        return categoriaB;
    }

    public void setCategoriaB(String categoriaB) {
        switch (categoriaB){
            case "ADOLESCENTI_RAGAZZI"->
                this.categoriaB = CategorieLibro.ADOLESCENTI_RAGAZZI.toString();

            case "ARTE"->
                this.categoriaB = CategorieLibro.ARTE.toString();

            case "CINEMA_FOTOGRAFIA" ->
                this.categoriaB = CategorieLibro.CINEMA_FOTOGRAFIA.toString();

            case "BIOGRAFIE" ->
                this.categoriaB = CategorieLibro.BIOGRAFIE.toString();

            case "DIARI_MEMORIE" ->
                this.categoriaB = CategorieLibro.DIARI_MEMORIE.toString();

            case "CALENDARI_AGENDE" ->
                this.categoriaB = CategorieLibro.CALENDARI_AGENDE.toString();

            case "DIRITTO" ->
                this.categoriaB = CategorieLibro.DIRITTO.toString();

            case "DIZINARI_OPERE" ->
                this.categoriaB = CategorieLibro.DIZINARI_OPERE.toString();

            case "ECONOMIA" ->
                this.categoriaB = CategorieLibro.ECONOMIA.toString();

            case "FAMIGLIA" ->
                this.categoriaB = CategorieLibro.FAMIGLIA.toString();

            case "FANTASCIENZA_FANTASY" ->
                this.categoriaB = CategorieLibro.FANTASCIENZA_FANTASY.toString();

            case "FUMETTI_MANGA" ->
                this.categoriaB = CategorieLibro.FUMETTI_MANGA.toString();

            case "GIALLI_THRILLER" ->
                this.categoriaB = CategorieLibro.GIALLI_THRILLER.toString();

            case "COMPUTER_GIOCHI" ->
                this.categoriaB = CategorieLibro.COMPUTER_GIOCHI.toString();

            case "HUMOR" ->
                this.categoriaB = CategorieLibro.HUMOR.toString();

            case "INFORMATICA" ->
                this.categoriaB = CategorieLibro.INFORMATICA.toString();

            case "WEB_DIGITAL_MEDIA" ->
                this.categoriaB = CategorieLibro.WEB_DIGITAL_MEDIA.toString();

            case "LETTERATURA_NARRATIVA" ->
                this.categoriaB = CategorieLibro.LETTERATURA_NARRATIVA.toString();

            case "LIBRI_BAMBINI" ->
                this.categoriaB = CategorieLibro.LIBRI_BAMBINI.toString();

            case "LIBRI_SCOLASTICI" ->
                this.categoriaB = CategorieLibro.LIBRI_SCOLASTICI.toString();

            case "LIBRI_UNIVERSITARI" ->
                this.categoriaB = CategorieLibro.LIBRI_UNIVERSITARI.toString();

            case "RICETTARI_GENERALI" ->
                this.categoriaB = CategorieLibro.RICETTARI_GENERALI.toString();

            case "LINGUISTICA_SCRITTURA" ->
                this.categoriaB = CategorieLibro.LINGUISTICA_SCRITTURA.toString();

            case "POLITICA" ->
                this.categoriaB = CategorieLibro.POLITICA.toString();

            case "RELIGIONE" ->
                this.categoriaB = CategorieLibro.RELIGIONE.toString();

            case "ROMANZI_ROSA" ->
                this.categoriaB = CategorieLibro.ROMANZI_ROSA.toString();

            case "SCIENZE" ->
                this.categoriaB = CategorieLibro.SCIENZE.toString();

            case "TECNOLOGIA_MEDICINA" ->
                this.categoriaB = CategorieLibro.TECNOLOGIA_MEDICINA.toString();

            case "ALTRO"->
                this.categoriaB = CategorieLibro.ALTRO.toString();


            default ->
                this.categoriaB = null;

        }
    }


    public String getCodIsbnB() {
        return codIsbnB;
    }

    public void setCodIsbnB(String codIsbnB) {
        this.codIsbnB = codIsbnB;
    }

    public int getNumeroPagineB() {
        return numeroPagineB;
    }

    public void setNumeroPagineB(int numeroPagineB) {
        this.numeroPagineB = numeroPagineB;
    }

    public String getAutoreB() {
        return autoreB;
    }

    public void setAutoreB(String autoreB) {
        this.autoreB = autoreB;
    }

    public String getEditoreB() {
        return editoreB;
    }

    public void setEditoreB(String editoreB) {
        this.editoreB = editoreB;
    }

    public String getLinguaB() {
        return linguaB;
    }

    public void setLinguaB(String linguaB) {
        this.linguaB = linguaB;
    }

    public Date getDateB() {
        return dateB;
    }

    public void setDateB(Date dateB) {
        this.dateB = dateB;
    }
}
