package laptop.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.regex.Pattern;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.UsersDao;
import laptop.database.csvusers.CsvUtente;
import laptop.exception.IdException;
import laptop.model.User;


public class ControllerRegistraUtente {
	private Boolean state=false;
	private final User u=User.getInstance();
	private static final ControllerSystemState vis=ControllerSystemState.getInstance();
	private static final CsvUtente csv=new CsvUtente();
	public Boolean registra(String n, String c, String email, String pwd,String desc, LocalDate localDate,String ruolo) throws SQLException, CsvValidationException, IOException, IdException {
		
		
		u.setEmail(email);
		u.setPassword(pwd);
		u.setDataDiNascita(localDate);
		u.setIdRuolo(ruolo);
		u.setDescrizione(desc);


		
		if(checkData ( n,c,email,pwd) )
		{
			if(vis.getTypeOfDb().equalsIgnoreCase("file")) {



					if(csv.userList(u).isEmpty()) {
						u.setNome(n);
						u.setCognome(c);
						u.setEmail(email);
						u.setPassword(pwd);
						u.setDataDiNascita(localDate);
						state = csv.inserisciUtente(u);
					}
				}


				else {
					if (UsersDao.checkUser(u) == 0) {
						// nuovo utente creo l'account
						u.setNome(n);
						u.setCognome(c);

						state = UsersDao.createUser(u);
					} else {
						// utente gia registrato o errore
						state = false;
					}
				}
		}

		else {
			state=false;
		}
		return state;
	}
	
	
	//le chiamo protected perchele uso nel controller stesso e basta 
	private boolean checkData (String n, String c, String email, String pwd)
	// controll  all data
	{
		if(checkEmail(email) && checkPassword(pwd) && checkNomeCognome(n,c))
		{
			state=true;
		}
		return state;
	}

	private boolean checkEmail(String email)
	{
		 Pattern pattern;

		String emailRegex;
		emailRegex= "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; 
                  
		pattern = Pattern.compile(emailRegex); 
		if (email == null) 
			return false; 
		return pattern.matcher(email).matches();
	}

	private boolean checkPassword(String pwd )
	{
		if(pwd.length()>=8 ) {
			state= true;
		}
		return state;
	}

	private boolean checkNomeCognome(String n, String c)
	{
		if (n != null && c != null)
		{
			state= true;
		}
		return state;
	}
	
	public ControllerRegistraUtente()
	{
		java.util.logging.Logger.getLogger("Test Costruttore").log(Level.INFO, "Costruttore ControllerRegistaUtente");

	}
	
	// TO DO: checkData o lo facciamo diretti in mysql
}
