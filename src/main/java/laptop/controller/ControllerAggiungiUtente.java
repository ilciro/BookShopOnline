package laptop.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.UsersDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.database.csvusers.CsvUtente;
import laptop.exception.IdException;
import laptop.model.User;

public class ControllerAggiungiUtente {
	private final CsvUtente csv;
	
	
	public ControllerAggiungiUtente() {
		csv=new CsvUtente();
		java.util.logging.Logger.getLogger("Test Costruttore").log(Level.INFO,"Costruttore ControllerAggiungiUtente");

	}

	public boolean checkData(String nome, String cognome, String email, String pass, String dataN) throws ParseException, SQLException, CsvValidationException, IOException, IdException {
		boolean state=false;
		Date sqlDate ;
		java.util.Date utilDate;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

	  
		utilDate = format.parse(dataN);
	    sqlDate = new Date(utilDate.getTime());
       

		User.getInstance().setIdRuolo("U");
       
       User.getInstance().setNome(nome);
       User.getInstance().setCognome(cognome);
       User.getInstance().setEmail(email);
       User.getInstance().setPassword(pass);
       User.getInstance().setDataDiNascita(sqlDate.toLocalDate());
	   if(ControllerSystemState.getInstance().getTypeOfDb().equals("file"))
		   state=csv.inserisciUtente(User.getInstance());
       else
      	state= UsersDao.createUser(User.getInstance());
	   return state;
		
	}

}

