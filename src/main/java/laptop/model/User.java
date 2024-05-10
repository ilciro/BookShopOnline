package laptop.model;

import java.time.LocalDate;



public class User {
	
 enum Ruoli {
		ADMIN,
		UTENTE,
		SCRITTORE,
		EDITORE;
 }
	@Override
	public String toString() {
		return "User [nome=" + nome + ", Cognome=" + cognome + ", email=" + email + ", idRuolo=" + r + "]";
	}
	
		


	private int id;
	private String nome;
	private String cognome;
	private String email;
	private String password;
	private String descrizione;
	private LocalDate dataDiNascita;
	private String r;
	
	private static User userInstance = null;

    private User() {}

    public static User getInstance() {
        if (userInstance == null) {
          
        	userInstance = new User();
                }
       
        return userInstance;
    }
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public LocalDate getDataDiNascita() {
		return dataDiNascita;
	}
	public void setDataDiNascita(LocalDate dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	
	public String getIdRuolo()  {
		
		return r;
	}



	public void setIdRuolo(String ruolo) {

		 switch (ruolo){
			case "ADMIN", "A"-> r = Ruoli.ADMIN.toString();
			case "EDITORE", "E"->r = Ruoli.EDITORE.toString();
			case "SCRITTORE", "W"->r = Ruoli.SCRITTORE.toString();
             default->r= Ruoli.UTENTE.toString();

			}
		

	}







}
