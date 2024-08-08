package laptop.model;

import java.time.LocalDate;




public class TempUser {
	enum RuoliT {
		ADMIN,
		UTENTE,
		SCRITTORE,
		EDITORE
 }
	@Override
	public String toString() {
		return "User [nome=" + nomeT + ", Cognome=" + cognomeT + ", email=" + emailT + ", idRuolo=" + r + "]";
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeT() {
		return nomeT;
	}

	public void setNomeT(String nomeT) {
		this.nomeT = nomeT;
	}

	public String getCognomeT() {
		return cognomeT;
	}

	public void setCognomeT(String cognomeT) {
		this.cognomeT = cognomeT;
	}

	public String getEmailT() {
		return emailT;
	}

	public void setEmailT(String emailT) {
		this.emailT = emailT;
	}

	public String getPasswordT() {
		return passwordT;
	}

	public void setPasswordT(String passwordT) {
		this.passwordT = passwordT;
	}

	public String getDescrizioneT() {
		return descrizioneT;
	}

	public void setDescrizioneT(String descrizioneT) {
		this.descrizioneT = descrizioneT;
	}

	public LocalDate getDataDiNascitaT() {
		return dataDiNascitaT;
	}

	public void setDataDiNascitaT(LocalDate dataDiNascitaT) {
		this.dataDiNascitaT = dataDiNascitaT;
	}



	private int id;
	private String nomeT;
	private String cognomeT;
	private String emailT;
	private String passwordT;
	private String descrizioneT;
	private LocalDate dataDiNascitaT;
	private String r;

	
	private static TempUser userInstance = null;

    private TempUser() {}

    public static TempUser getInstance() {
        if (userInstance == null) {
          
        	userInstance = new TempUser();
                }
       
        return userInstance;
    }
	

	
public String getIdRuolo()  {
		
		return r;
	}



	public void setIdRuolo(String ruolo) {

		 switch (ruolo){
			case "ADMIN","A"->
				r = RuoliT.ADMIN.toString();

			case "EDITORE","E"->
				r = RuoliT.EDITORE.toString();

			case "SCRITTORE","W","S"->
				r = RuoliT.SCRITTORE.toString();

             default->
				r= RuoliT.UTENTE.toString();

			}
		

	}


	
}
