package laptop.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;

import laptop.database.UsersDao;
import laptop.model.User;

public class ControllerModificaUtente {
	private boolean state = false;
	private User uT;



	public ControllerModificaUtente()
	{
		java.util.logging.Logger.getLogger("Test Costruttore").log(Level.INFO,"Costruttore ControllerModificaUtente");

		uT=User.getInstance();
	}



	public User prendi() throws SQLException {

		return UsersDao.pickData(User.getInstance());


	}


	public boolean aggiornaTot(String n, String c, String email1, String pass, String desc, LocalDate localDate, String ruolo) throws SQLException {
		uT=prendi();

		uT.setNome(n);
		uT.setCognome(c);
		uT.setEmail(email1);
		uT.setPassword(pass);
		uT.setDescrizione(desc);
		uT.setDataDiNascita(localDate);
		uT.setIdRuolo(ruolo);
		if(UsersDao.aggiornaUtente(uT,email1) != null)
		{
			state=true;
		}
		return state;
	}

}
