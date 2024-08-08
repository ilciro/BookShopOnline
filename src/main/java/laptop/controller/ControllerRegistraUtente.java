package laptop.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.regex.Pattern;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.UsersDao;
import laptop.database.csvUsers.CsvUtente;
import laptop.model.User;


public class ControllerRegistraUtente {
	private Boolean state=false;
	private final User u=User.getInstance();
	private static final ControllerSystemState vis=ControllerSystemState.getInstance();
	private static final CsvUtente csv=new CsvUtente();
	public Boolean registra(String n, String c, String email, String pwd, String pwdC, LocalDate localDate) throws SQLException, CsvValidationException, IOException {
		
		
		u.setEmail(email);
		u.setPassword(pwd);
		u.setDataDiNascita(localDate);


		
		if(checkData ( n,c,email,pwd,pwdC) )
		{
			if(vis.getTypeOfDb().equalsIgnoreCase("file")) {



					if(csv.getUserListNum(new File("report/reportUtente.csv"),  email, pwd)==0) {
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
	private boolean checkData (String n, String c, String email, String pwd, String pwdC)
	// controll  all data
	{
		if(checkEmail(email) && checkPassword(pwd,pwdC) && checkNomeCognome(n,c))
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

	private boolean checkPassword(String pwd, String pwdC )
	{
		if(pwd.length()>=8 && pwdC.length()>=8 && pwd.equals(pwdC)) {
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
