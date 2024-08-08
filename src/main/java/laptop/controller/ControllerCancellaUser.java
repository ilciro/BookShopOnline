package laptop.controller;

import java.sql.SQLException;
import java.util.logging.Level;

import laptop.database.UsersDao;
import laptop.model.User;

public class ControllerCancellaUser {
	
	public boolean cancellaUser() throws SQLException
	{
		User.getInstance().setId(ControllerSystemState.getInstance().getId());
		return UsersDao.deleteUser(User.getInstance());
	}
	public ControllerCancellaUser()
	{
		java.util.logging.Logger.getLogger("Test Costruttore").log(Level.INFO,"Costruttore ControllerCancellaUser");

	}

}
