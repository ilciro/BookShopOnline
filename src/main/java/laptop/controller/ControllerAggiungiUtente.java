package laptop.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;

import laptop.database.UsersDao;
import laptop.model.User;

public class ControllerAggiungiUtente {
	
	
	public ControllerAggiungiUtente()
	{
		java.util.logging.Logger.getLogger("Test Costruttore").log(Level.INFO,"Costruttore ControllerAggiungiUtente");

	}

	public boolean checkData(String nome, String cognome, String email, String pass, String dataN) throws ParseException, SQLException {
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
       
      return UsersDao.createUser(User.getInstance());
		
	}

}

