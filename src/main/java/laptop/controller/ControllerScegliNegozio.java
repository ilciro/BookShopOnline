package laptop.controller;


import javafx.collections.ObservableList;
import laptop.database.NegozioDao;
import laptop.model.Negozio;

public class ControllerScegliNegozio {
	
	private final NegozioDao nD;


	
	public ControllerScegliNegozio()
	{
		nD = new NegozioDao();
	}
	
	public ObservableList<Negozio> getNegozi()
	{
		ObservableList<Negozio>listOfNegozi;
		listOfNegozi = nD.getNegozi();
		return listOfNegozi;
	}
	

}