package laptop.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;



public class ControllerGestionePage {
	private final RivistaDao rD;
	private final LibroDao lD;
	private final GiornaleDao gD;
	private final Libro l;
	private final Giornale g;
	private final Rivista r;
	private static final String LIBRO="libro";
	private static final String GIORNALE="giornale";
	private static final String RIVISTA="rivista";
	private final CsvOggettoDao csvDao;
	private final ControllerSystemState vis=ControllerSystemState.getInstance();

	public boolean cancella(int id) throws SQLException, CsvValidationException, IOException {

		boolean status=false;

		if(ControllerSystemState.getInstance().getTypeOfDb().equals("file"))
		{

			switch (vis.getType())
			{
				case LIBRO ->
				{
					l.setId(id);
					csvDao.removeLibroById(l);
					status=true;
				}
				case GIORNALE ->
				{
					g.setId(id);
					csvDao.removeGiornaleById(g);
					status=true;
				}
				case RIVISTA ->
				{
					r.setId(id);
					csvDao.removeRivistaById(r);
					status=true;
				}
				default ->	java.util.logging.Logger.getLogger("cancella oggetto").log(Level.SEVERE, " type not correct !!\n");

			}

		}
		else {
			switch (vis.getType())
			{
				case LIBRO ->
				{
					l.setId(vis.getId());
					lD.cancella(l);
					status=true;
				}
				case GIORNALE ->
				{
					g.setId(vis.getId());
					gD.cancella(g);
					status=true;
				}
				case RIVISTA ->
				{
					r.setId(vis.getId());
					rD.cancella(r);
					status=true;
				}
				default ->	java.util.logging.Logger.getLogger("cancella oggetto").log(Level.SEVERE, " type not correct !!\n");

			}
			}

		return status;

	}


	public ObservableList<Raccolta> getLista(String type) throws CsvValidationException, IOException, IdException {
		ObservableList<Raccolta> catalogo = FXCollections.observableArrayList();
		if(ControllerSystemState.getInstance().getTypeOfDb().equalsIgnoreCase("file"))
		{

			switch (type) {
				case LIBRO->catalogo.addAll(csvDao.retrieveRaccoltaData(new File("report/reportLibro.csv")));

				case GIORNALE->
					catalogo.addAll(csvDao.retrieveRaccoltaData(new File("report/reportGiornale.csv")));

				case RIVISTA->
					catalogo.addAll(csvDao.retrieveRaccoltaData(new File("report/reportRivista.csv")));

				default-> throw new IdException(" id/type not valid");

			}
		}
		else {
			switch (type) {
				case LIBRO->
					catalogo.addAll(lD.getLibri());

				case GIORNALE->
					catalogo.addAll(gD.getGiornali());

				case RIVISTA->
					catalogo.addAll(rD.getRiviste());

				default-> throw new IdException( " db : id / type not found");


			}
		}
		return catalogo;
	}


	
	public ControllerGestionePage() throws IOException {
		rD=new RivistaDao();
		gD=new GiornaleDao();
		lD=new LibroDao();
		l=new Libro();
		g=new Giornale();
		r=new Rivista();
		csvDao=new CsvOggettoDao();

	}
	public String settaHeader(String type) {
		String s;
		switch (type) {
			case LIBRO:
				s = "Benvenuto nella schermata dei libri";
				break;
			case RIVISTA:
				s = "Benvenuto nella schermata dele riviste";
				break;
			case GIORNALE:
				s = "Benvenuto nella schermata dei giornali";
				break;
			default:
				return "";
		}
		return s;
	}



}
