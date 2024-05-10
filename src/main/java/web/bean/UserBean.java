package web.bean;


import java.time.LocalDate;
import java.util.regex.Pattern;

public class UserBean {



    public String getMexB() {
        return mexB;
    }

    public void setMexB(String mexB) {
        this.mexB = mexB;
    }

    public String getStringB() {
        return stringB;
    }

    public void setStringB(String stringB) {
        this.stringB = stringB;
    }


    enum Ruoli {
        ADMIN,
        UTENTE,
        SCRITTORE,
        EDITORE
    }

    private int idB;
    private String ruoloB;
    private String emailB;
    private String passB;
    private String mexB;

    private String nomeB;
    private String cognomeB;

    private LocalDate dataDiNascitaB;

    private  String stringB;


    public String getNomeB() {
        return nomeB;
    }

    public void setNomeB(String nomeB) {
        this.nomeB = nomeB;
    }

    public String getCognomeB() {
        return cognomeB;
    }

    public void setCognomeB(String cognomeB) {
        this.cognomeB = cognomeB;
    }

    public LocalDate getDataDiNascitaB() {
        return dataDiNascitaB;
    }

    public void setDataDiNascitaB(LocalDate dataDiNascitaB) {
        this.dataDiNascitaB = dataDiNascitaB;
    }

    public String getDescrizioneB() {
        return descrizioneB;
    }

    public void setDescrizioneB(String descrizioneB) {
        this.descrizioneB = descrizioneB;
    }

    private String descrizioneB;

    private static UserBean instance=null;

    public static UserBean getInstance()
    {
        if(instance==null)
            instance=new UserBean();
        return instance;
    }

    private UserBean() {}

    public int getIdB() {
        return idB;
    }

    public void setIdB(int idB) {
        this.idB = idB;
    }

    public String getRuoloB() {
        return ruoloB;
    }

    public void setRuoloB(String ruoloB) {
       this.ruoloB= getRuolo(ruoloB);

    }

    public String getEmailB() {
        return emailB;
    }

    public void setEmailB(String emailB) {

        Pattern pattern;

        String emailRegex;
        emailRegex= "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        pattern = Pattern.compile(emailRegex);
        if (emailB == null || emailB.isEmpty() || !pattern.matcher(emailB).matches())
            this.emailB=null;

        this.emailB = emailB;
    }

    public String getPassB() {
        return passB;
    }

    public void setPassB(String passB) {
        if(passB.length() <= 8)
            this.passB=null;
        this.passB = passB;
    }

    private String getRuolo(String r) {
        String rB;
        switch (r) {
            case "ADMIN", "A" -> rB=Ruoli.ADMIN.toString().substring(0,1);
            case "EDITORE", "E" -> rB=Ruoli.EDITORE.toString().substring(0,1);
            case "SCRITTORE", "W" -> rB=Ruoli.SCRITTORE.toString().substring(0,1);
            default -> rB=Ruoli.UTENTE.toString().substring(0,1);

        }


        return rB;
    }
}
