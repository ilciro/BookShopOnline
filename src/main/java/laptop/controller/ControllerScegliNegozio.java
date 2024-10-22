package laptop.controller;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import laptop.database.NegozioDao;
import laptop.model.Negozio;

public class ControllerScegliNegozio {
	
	private final NegozioDao nD;
	public Negozio getN() {
		return n;
	}

	public void setN(Negozio n) {
		this.n = n;
	}

	private Negozio n;


	
	public ControllerScegliNegozio()
	{
		nD = new NegozioDao();
		n = new Negozio(); 
	}
	
	public ObservableList<Negozio> getNegozi()
	{
		ObservableList<Negozio>listOfNegozi;
		listOfNegozi = nD.getNegozi();
		return listOfNegozi;
	}
	

}