package laptop.controller;

import java.util.logging.Level;

import laptop.exception.LogoutException;
import laptop.model.User;

public class ControllerHomePageAfterLogin {
	
	private static final User u = User.getInstance();
	private static final ControllerSystemState vis = ControllerSystemState.getInstance() ;

	// qui ci va la funzione di logout
	
	public static boolean logout() 
	{	
		boolean state=false;
		String n = u.getNome();
		java.util.logging.Logger.getLogger("Test logout").log(Level.INFO, "stai sloggando come {0}" ,n);
		
		if (n==null)
		{
			try {
			throw new LogoutException("Errore Logout");
			}catch(LogoutException e)
			{
				
				java.util.logging.Logger.getLogger("Test logout").log(Level.INFO, "errore nel logout",e);
				
			}
		}
		else {
		u.setId(-1);
		u.setNome(null);
		u.setCognome(null);
		u.setDataDiNascita(null);
		u.setDescrizione(null);
		u.setEmail(null);
		u.setPassword(null);
		
		
		java.util.logging.Logger.getLogger("Test Eccezione").log(Level.INFO, "stai sloggando {0}",u.getEmail());
			vis.setIsLogged(false);
			state=true;
		}
		return state;

	}
	private ControllerHomePageAfterLogin()
	{
		
	}
	//insert a blanck comment
}
