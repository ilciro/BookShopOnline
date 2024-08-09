package laptop.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.UsersDao;
import laptop.database.csvusers.CsvUtente;
import laptop.model.User;

public class ControllerCancellaUser {
	private final ControllerSystemState vis=ControllerSystemState.getInstance();
	private final CsvUtente csvUtente;

	public boolean cancellaUser() throws SQLException, CsvValidationException, IOException {
		boolean state;

		User.getInstance().setId(vis.getId());

		if(vis.getTypeOfDb().equals("file")) {
			csvUtente.removeUserByIdEmailPwd(User.getInstance());
			state=true;
		}
			else
				state=UsersDao.deleteUser(User.getInstance());
			return state;
	}
	public ControllerCancellaUser()
	{
		java.util.logging.Logger.getLogger("Test Costruttore").log(Level.INFO,"Costruttore ControllerCancellaUser");
		csvUtente=new CsvUtente();
	}

}
