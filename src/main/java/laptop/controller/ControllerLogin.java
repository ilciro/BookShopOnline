package laptop.controller;

import java.sql.SQLException;
import java.util.logging.Level;

import laptop.database.UsersDao;
import laptop.model.User;

public class ControllerLogin {
	
	private final User user = User.getInstance();
	protected boolean esito;

	
	public boolean controlla(String m, String p) throws SQLException
	{
		
		
		
			user.setEmail(m);
			user.setPassword(p);
			
			 if (UsersDao.checkUser(user) == 1)
			{
				// utente trovato
				// vai col login
				// vai con la specializzazione prendendo i dati dal dao
				
				// qui prendo il ruolo in base ala mail dell'utente
				String r =UsersDao.getRuolo(user);
				// predno e li assegno all'oggetto user
				UsersDao.pickData(user);
				java.util.logging.Logger.getLogger("Test log").log(Level.INFO, "loggato come {0}", r);

				ControllerSystemState.getInstance().setIsLogged(true);
				 esito = true;
			}
			 else {
				 esito=false;
			 }
			
			return esito;

		
	}
	
	public String getRuoloTempUSer(String email) throws SQLException
	{

		user.setEmail(email);
		 return UsersDao.getRuolo(user);
		
	}
		
}
