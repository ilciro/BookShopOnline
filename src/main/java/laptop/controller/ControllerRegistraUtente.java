package laptop.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.regex.Pattern;

import laptop.database.UsersDao;
import laptop.model.User;


public class ControllerRegistraUtente {
	private Boolean state=false;
	private final User u=User.getInstance();

	public Boolean registra(String n, String c, String email, String pwd, String pwdC, LocalDate localDate) throws SQLException {
		
		
		u.setEmail(email);
		u.setPassword(pwd);
		u.setDataDiNascita(localDate);
		
		
		if(checkData ( n,c,email,pwd,pwdC) )
		{
			if (UsersDao.checkUser(u) == 0)
			{
				// nuovo utente creo l'account
				u.setNome(n);
				u.setCognome(c);
				
				state=UsersDao.createUser(u);
			}
			else 			{
				// utente gia registrato o errore 
				state = false;
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
