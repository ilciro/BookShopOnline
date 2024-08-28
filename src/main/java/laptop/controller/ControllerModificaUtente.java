package laptop.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.UsersDao;
import laptop.database.csvusers.CsvUtente;
import laptop.exception.IdException;
import laptop.model.User;

public class ControllerModificaUtente {
	private boolean state = false;
	private User uT;
	private final ControllerSystemState vis=ControllerSystemState.getInstance();
	private final CsvUtente csvUtente;



	public ControllerModificaUtente()
	{
		java.util.logging.Logger.getLogger("Test Costruttore").log(Level.INFO,"Costruttore ControllerModificaUtente");

		uT=User.getInstance();
		csvUtente=new CsvUtente();
	}



	public User prendi() throws SQLException{

	return UsersDao.pickData(uT);

	}

	public User prendiCsv() throws CsvValidationException, IOException, IdException {
		return csvUtente.userList(new File("report/reportUtente.csv"),uT).get(0);
	}


	public boolean aggiornaTot(String n, String c, String email1, String pass, String desc, LocalDate localDate, String ruolo) throws SQLException, CsvValidationException, IOException, IdException {
		//sistemare anche qui
		if (vis.getTypeOfDb().equals("file"))
			uT = prendiCsv();
		else uT = prendi();

		String email=uT.getEmail();



		uT.setNome(n);
		uT.setCognome(c);
		uT.setEmail(email1);
		uT.setPassword(pass);
		uT.setDescrizione(desc);
		uT.setDataDiNascita(localDate);
		uT.setIdRuolo(ruolo);
		uT.setId(vis.getId());

		if(vis.getTypeOfDb().equals("file"))
		{
			csvUtente.removeUserByIdEmailPwd(uT);
			csvUtente.inserisciUtente(uT);
			state=true;
		}

		if(UsersDao.aggiornaUtente(uT, email) != null)
			{
				state=true;
			}

		return state;
	}



}
