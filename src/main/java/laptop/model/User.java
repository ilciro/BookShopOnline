package laptop.model;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;


public class User {
	
 enum Ruoli {
		ADMIN,
		UTENTE,
		SCRITTORE,
		EDITORE,
		NONVALIDO;
 }
	@Override
	public String toString() {
		return "User [nome=" + nome + ", Cognome=" + cognome + ", email=" + email + " , pass=" + password +", idRuolo=" + r + ", id=" + id +"]";
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
			case "ADMIN", "A"-> r = Ruoli.ADMIN.toString().substring(0,1);
			case "EDITORE", "E"->r = Ruoli.EDITORE.toString().substring(0,1);
			case "SCRITTORE", "W", "S"->r = Ruoli.SCRITTORE.toString().substring(0,1);
			case "UTENTE","U"->r=Ruoli.UTENTE.toString().substring(0,1);
             default->r= Ruoli.NONVALIDO.toString().substring(0,1);

			}
		

	}
	//usato per "pulire" utente dopo modifica
	public boolean annullaUtente()
	{
		boolean status = false;
		setId(0);
		setNome("");
		setCognome("");
		setEmail("");
		setPassword("");
		setDescrizione("");
		setDataDiNascita(LocalDate.of(1900,1,1));
		setIdRuolo("NONVALIDO");
		Logger.getLogger("annulla utente").log(Level.INFO," user data is cleaned");
		if(nome.isEmpty())
			status=true;
		return status;
	}







}
