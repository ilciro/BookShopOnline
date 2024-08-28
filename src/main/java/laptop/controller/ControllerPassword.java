package laptop.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.UsersDao;
import laptop.database.csvusers.CsvUtente;
import laptop.exception.IdException;
import laptop.model.User;

public class ControllerPassword {
	private final User u = User.getInstance();
	private boolean status=false;
	private static final ControllerSystemState vis= ControllerSystemState.getInstance();
	private static final CsvUtente csv=new CsvUtente();
	public ControllerPassword()
	{
		java.util.logging.Logger.getLogger("Test Costruttore").log(Level.INFO,"Costruttore ControllerPassword");

	}

	public boolean aggiornaPass(String email,String vecchiaP,String nuovaP) throws SQLException, CsvValidationException, IOException, IdException {
		u.setEmail(email);
		u.setPassword(vecchiaP);
		if(u.getPassword().equals(vecchiaP) && (nuovaP.length()>=8 || !email.isEmpty()) )
		{

				if(vis.getTypeOfDb().equalsIgnoreCase("file"))
				{
					//cancello il vecchio e metto il nuovo
					User u2=User.getInstance();
					u2.setId(u.getId());
					u2.setIdRuolo(u.getIdRuolo());
					u2.setNome(u.getNome());
					u2.setCognome(u.getCognome());
					u2.setEmail(u.getEmail());
					u2.setPassword(u.getPassword());
					u2.setDescrizione(u.getDescrizione());
					u2.setDataDiNascita(u.getDataDiNascita());

					csv.removeUserByIdEmailPwd(u);
					csv.inserisciUtente(u2);


				}

				if(UsersDao.checkUser(u) == 1)
				{
					status=UsersDao.checkResetpass(u, nuovaP,email);
				}
				
				
			
			
		}
		return status;
	}
}
