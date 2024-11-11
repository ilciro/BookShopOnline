package laptop.model;

import java.time.LocalDate;


// this class is not singleton


public class TempUser {
	enum RuoliT {
		ADMIN,
		UTENTE,
		SCRITTORE,
		EDITORE,
		NONVALIDO;
 }
	@Override
	public String toString() {
		return "User [nome=" + nomeT + ", Cognome=" + cognomeT + ", email=" + emailT + ", idRuolo=" + ruoloT + "]";
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
	private String ruoloT;





public String getIdRuoloT()  {

		return ruoloT;
	}



	public void setIdRuoloT(String ruolo) {

		 switch (ruolo){
			case "ADMIN","A"->
				ruoloT = RuoliT.ADMIN.toString();
			case "EDITORE","E"->
				ruoloT = RuoliT.EDITORE.toString();
			case "SCRITTORE","W","S"->
				ruoloT = RuoliT.SCRITTORE.toString();
			case "UTENTE","U"->
					ruoloT = RuoliT.UTENTE.toString();
             default->
				ruoloT= RuoliT.NONVALIDO.toString();

			}


	}


	
}
