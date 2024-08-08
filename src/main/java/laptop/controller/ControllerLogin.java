package laptop.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.UsersDao;
import laptop.database.csvUsers.CsvUtente;
import laptop.model.User;

public class ControllerLogin {
	
	private static final User user = User.getInstance();
	protected boolean esito;
	private static final ControllerSystemState vis=ControllerSystemState.getInstance();
	private static final CsvUtente csvU=new CsvUtente();

	
	public boolean controlla(String m, String p) throws SQLException, CsvValidationException, IOException {
		
		
		
			user.setEmail(m);
			user.setPassword(p);

			if(vis.getTypeOfDb().equals("file")) {
				if(csvU.getUserListNum(new File("report/reportUtente.csv"),user.getEmail(),user.getPassword())==0)

				{
					ControllerSystemState.getInstance().setIsLogged(false);
					esito=false;
				}
				else esito=true;


            }
			else {


				if (UsersDao.checkUser(user) == 1) {
					// utente trovato
					// vai col login
					// vai con la specializzazione prendendo i dati dal dao

					// qui prendo il ruolo in base ala mail dell'utente
					String r = UsersDao.getRuolo(user);
					// predno e li assegno all'oggetto user
					UsersDao.pickData(user);
					java.util.logging.Logger.getLogger("Test log").log(Level.INFO, "loggato come {0}", r);

					ControllerSystemState.getInstance().setIsLogged(true);
					esito = true;
				} else {
					esito = false;
				}
			}




				return esito;

			}

	
	public String getRuoloTempUSer(String email) throws SQLException, CsvValidationException, IOException {

		String ruolo="";
		user.setEmail(email);
		if(vis.getTypeOfDb().equalsIgnoreCase("file")) {
			ruolo = csvU.getUserList(new File("report/reportUtente.csv"), user.getId(), user.getEmail(), user.getPassword()).getIdRuolo();
		}
		else ruolo= UsersDao.getRuolo(user);
		return ruolo;
		
	}
		
}
