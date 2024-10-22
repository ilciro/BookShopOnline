package laptop.controller;

import java.io.IOException;
import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;


public class ControllerVisualizza {

	private final LibroDao lD;
	private final Libro l;
	private final Giornale g;
	private final Rivista r;
	private final RivistaDao rD;
	private final GiornaleDao gD;


	private final ControllerSystemState vis = ControllerSystemState.getInstance();
	private final CsvOggettoDao csv = new CsvOggettoDao();

	public ControllerVisualizza() throws IOException {
		l = new Libro();
		g = new Giornale();
		r = new Rivista();
		lD = new LibroDao();
		gD = new GiornaleDao();
		rD = new RivistaDao();
	}



	public int getID()
	{
		java.util.logging.Logger.getLogger("Test getId").log(Level.INFO, "id {0}",vis.getId());

		return vis.getId();
	}

	public ObservableList<Libro> getListLibro() throws CsvValidationException, IOException, IdException {
		ObservableList<Libro> list;
		l.setId(getID());
		if(vis.getTypeOfDb().equalsIgnoreCase("db"))
		{
			list=lD.getLibroByIdTitoloAutoreLibro(l);
		}
		else{
			list=csv.getLibroByIdTitoloAutore(l);
		}
		return list;
	}

	public ObservableList<Giornale> getListGiornale() throws CsvValidationException, IOException, IdException {
		ObservableList<Giornale> list;
		g.setId(getID());
		if(vis.getTypeOfDb().equalsIgnoreCase("db"))
		{

			list=gD.getGiornaleIdTitoloAutore(g);

		}
		else{
			list=csv.getGiornaleByIdTitoloEditore(g);
		}
		return list;
	}

	public ObservableList<Rivista> getListRivista() throws CsvValidationException, IOException, IdException {
		ObservableList<Rivista> list;
		r.setId(getID());
		if(vis.getTypeOfDb().equalsIgnoreCase("db"))
		{

			list=rD.getRivistaIdTitoloAutore(r);

		}
		else{
			list=csv.getRivistaByIdTitoloEditore(r);
		}
		return list;

	}

}

