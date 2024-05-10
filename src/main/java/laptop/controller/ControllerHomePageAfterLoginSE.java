package laptop.controller;

import java.util.logging.Level;

import laptop.exception.LogoutException;
import laptop.model.User;

public class ControllerHomePageAfterLoginSE {
	private static final User u = User.getInstance();
	private static final ControllerSystemState vis = ControllerSystemState.getInstance() ;

	// qui ci va la funzione di logout

	public static boolean logout() throws   SecurityException, IllegalArgumentException, LogoutException
	{	
			
			String n = u.getNome();
			java.util.logging.Logger.getLogger("Test Eccezione").log(Level.INFO, "stai sloggando come {0}",n);

			u.setId(-1);
			u.setNome(null);
			u.setCognome(null);
			u.setDataDiNascita(null);
			u.setDescrizione(null);
			u.setEmail(null);
			u.setPassword(null);
			

			if(u.getId()!=-1)
			{
				throw new LogoutException("Errore Logout");

				
			}
			else
			{	
				java.util.logging.Logger.getLogger("Test Eccezione").log(Level.INFO, "logout utente {0}",u.getEmail());

				vis.setIsLogged(false);
				return true;
			}

		

	}
	private ControllerHomePageAfterLoginSE()
	{
		
	}

}
