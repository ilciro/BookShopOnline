package laptop.controller;

import laptop.model.User;

public class ControllerReportRaccolta {
	private static final User u=User.getInstance();
	
	public String getTipo()
	{
		return u.getIdRuolo();
	}

}
