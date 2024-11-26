package web.bean;

import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class TempUserBean {



    public ObservableList<TempUserBean> getLista() {
        return lista;
    }

    public void setLista(ObservableList<TempUserBean> lista) {
        this.lista = lista;
    }


    enum Ruoli {
        ADMIN,
        UTENTE,
        SCRITTORE,
        EDITORE
    }

    public String getCognomeBNOS() {
        return cognomeBNOS;
    }

    public void setCognomeBNOS(String cognomeBNOS) {
        this.cognomeBNOS = cognomeBNOS;
    }

    public LocalDate getDataDiNascitaBNOS() {
        return dataDiNascitaBNOS;
    }

    public void setDataDiNascitaBNOS(LocalDate dataDiNascitaBNOS) {
        this.dataDiNascitaBNOS = dataDiNascitaBNOS;
    }

    public String getEmailBNOS() {
        return emailBNOS;
    }

    public void setEmailBNOS(String emailBNOS) {
        Pattern pattern;

        String emailRegex;
        emailRegex= "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        pattern = Pattern.compile(emailRegex);
        if (emailBNOS == null || emailBNOS.isEmpty() || !pattern.matcher(emailBNOS).matches())
            this.emailBNOS=null;

        this.emailBNOS = emailBNOS;
    }

    public int getIdBNOS() {
        return idBNOS;
    }

    public void setIdBNOS(int idBNOS) {
        this.idBNOS = idBNOS;
    }

    public String getMexBNOS() {
        return mexBNOS;
    }

    public void setMexBNOS(String mexBNOS) {
        this.mexBNOS = mexBNOS;
    }

    public String getNomeBNOS() {
        return nomeBNOS;
    }

    public void setNomeBNOS(String nomeBNOS) {
        this.nomeBNOS = nomeBNOS;
    }

    public String getPassBNOS() {
        return passBNOS;
    }

    public void setPassBNOS(String passBNOS) {
        if(passBNOS.length() <= 8)
            this.passBNOS=null;
        this.passBNOS = passBNOS;
    }

    public String getRuoloB() {

        switch (ruoloBNOS) {
            case "ADMIN", "A" -> ruoloBNOS= UserBean.Ruoli.ADMIN.toString().substring(0,1);
            case "EDITORE", "E" -> ruoloBNOS= UserBean.Ruoli.EDITORE.toString().substring(0,1);
            case "SCRITTORE", "W" -> ruoloBNOS= UserBean.Ruoli.SCRITTORE.toString().substring(0,1);
            default -> ruoloBNOS= UserBean.Ruoli.UTENTE.toString().substring(0,1);

        }


        return ruoloBNOS;

    }

    public void setRuoloBNOS(String ruoloBNOS) {
        this.ruoloBNOS = ruoloBNOS;
    }

    public String getStringBNOS() {
        return stringBNOS;
    }

    public void setStringBNOS(String stringBNOS) {
        this.stringBNOS = stringBNOS;
    }

    private int idBNOS;
    private String ruoloBNOS;
    private String emailBNOS;
    private String passBNOS;
    private String mexBNOS;

    private String nomeBNOS;
    private String cognomeBNOS;

    private LocalDate dataDiNascitaBNOS;

    private  String stringBNOS;

    private ObservableList<TempUserBean> lista;





    public String getDescrizioneBNOS() {
        return descrizioneBNOS;
    }

    public void setDescrizioneBNOS(String descrizioneB) {
        this.descrizioneBNOS = descrizioneB;
    }

    private String descrizioneBNOS;













}
