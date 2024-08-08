package laptop.controller;

import java.util.logging.Level;




public class ControllerRicercaPerTipo {



	
	public ControllerRicercaPerTipo() 
	{
		java.util.logging.Logger.getLogger("Test Costruttore").log(Level.INFO,"Costruttore ControllerRicercaPerTipo");

	}
	

	public boolean setRicerca(String type)
	{


        return type.equals("libro") || type.equals("giornale") || type.equals("rivista");
	}
}
