package laptop.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;


public class ControllerRicercaPage {
	
	private final LibroDao lD;
	private final GiornaleDao gD;
	private final RivistaDao rD;
	private final Libro l;
	private final Giornale g;
	private final Rivista riv;
	public ControllerRicercaPage() throws IOException {
		lD = new LibroDao();
		gD = new GiornaleDao();
		rD =new RivistaDao();
		l=new Libro();
		g=new Giornale();
		riv=new Rivista();

		ControllerSystemState.getInstance().setIsSearch(true);
		
	}
	
	public ObservableList<Raccolta> cercaPerTipo (String s)
	{
		ObservableList<Raccolta> r = null;

		switch(returnType()) {
			case "libro" -> {
			l.setTitolo(s);
			l.setAutore(s);
			r = lD.getLibriIdTitoloAutore(l);
			}
			case "giornale"-> {
				g.setEditore(s);
				g.setTitolo(s);
				r = gD.getGiornaliIdTitoloAutore(g);
			}
			case "rivista"-> {
				riv.setTitolo(s);
				riv.setAutore(s);
				r = rD.getRivisteIdTitoloAutore(riv);
			}
			default-> Logger.getLogger("cerca per tipo").log(Level.SEVERE," type is not correct");

		}


		
		return r;
		
	}
	
	public String returnType()
	{
		return ControllerSystemState.getInstance().getType();
	}
	
	
}
