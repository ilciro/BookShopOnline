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
import laptop.database.csvOggetto.CsvOggettoDao;
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

	public void cancella(int id) throws SQLException, CsvValidationException, IOException {


		if(ControllerSystemState.getInstance().getTypeOfDb().equals("file"))
		{

			switch (vis.getType())
			{
				case LIBRO ->
				{
					l.setId(vis.getId());
					csvDao.removeLibroById(l);
				}
				case GIORNALE ->
				{
					g.setId(vis.getId());
					csvDao.removeGiornaleById(g);
				}
				case RIVISTA ->
				{
					r.setId(vis.getId());
					csvDao.removeRivistaById(r);
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
				}
				case GIORNALE ->
				{
					g.setId(vis.getId());
					gD.cancella(g);
				}
				case RIVISTA ->
				{
					r.setId(vis.getId());
					rD.cancella(r);
				}
				default ->	java.util.logging.Logger.getLogger("cancella oggetto").log(Level.SEVERE, " type not correct !!\n");

			}
			}


	}


	public ObservableList<Raccolta> getLista(String type) throws CsvValidationException, IOException, IdException {
		ObservableList<Raccolta> catalogo = FXCollections.observableArrayList();
		if(ControllerSystemState.getInstance().getTypeOfDb().equalsIgnoreCase("file"))
		{

			switch (type) {
				case LIBRO:
					catalogo.addAll(csvDao.retrieveRaccoltaData(new File("report/reportLibro.csv")));
					break;
				case GIORNALE:
					catalogo.addAll(csvDao.retrieveRaccoltaData(new File("report/reportGiornale.csv")));
					break;
				case RIVISTA:
					catalogo.addAll(csvDao.retrieveRaccoltaData(new File("report/reportRivista.csv")));
					break;
				default:
					return catalogo;



			}



		}
		else {
			switch (type) {
				case LIBRO:
					catalogo.addAll(lD.getLibri());
					break;
				case GIORNALE:
					catalogo.addAll(gD.getGiornali());
					break;
				case RIVISTA:
					catalogo.addAll(rD.getRiviste());
					break;
				default:
					return catalogo;

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
