package laptop.controller;

import java.sql.SQLException;
import java.util.logging.Level;

import laptop.database.UsersDao;
import laptop.model.User;


public class ControllerVisualizzaProfilo {
	private boolean status=false;

	public User getCredenziali() throws SQLException {

		return UsersDao.pickData(User.getInstance());

	}

	public ControllerVisualizzaProfilo()
	{
		java.util.logging.Logger.getLogger("Test Costruttore").log(Level.INFO, "Costruttore ControllerVisualizzaProfilo");

	}

	public boolean cancellaUtente() throws SQLException {
		
		
		if(UsersDao.deleteUser(User.getInstance()))
		{
			status=true;
		}
		
 
		return status;

	}
}







