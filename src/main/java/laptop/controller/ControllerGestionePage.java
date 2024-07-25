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
import laptop.database.csv.CsvOggettoDao;
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

	public void cancella(int id) throws SQLException, CsvValidationException, IOException {
		File fd = null;
		if (ControllerSystemState.getInstance().getType().equals(LIBRO)) {
			l.setId(id);
			lD.cancella(l);
		} else if (ControllerSystemState.getInstance().getType().equals(GIORNALE)) {
			g.setId(id);
			gD.cancella(g);
		} else if (ControllerSystemState.getInstance().getType().equals(RIVISTA)) {
			r.setId(id);
			rD.cancella(r);
		}
		if(ControllerSystemState.getInstance().getTypeOfDb().equals("file"))
		{
			switch (ControllerSystemState.getInstance().getType())
			{
				case LIBRO -> fd=new File("report/reportLibri.csv");
				case GIORNALE -> fd=new File("report/reportGiornali.csv");
				case RIVISTA -> fd=new File("report/reportRiviste.csv");
				default ->	java.util.logging.Logger.getLogger("cancella oggetto").log(Level.SEVERE, " type not correct !!\n");

			}
			csvDao.eliminaOggetto(fd);
		}
	}


	public ObservableList<Raccolta> getLista(String type) throws CsvValidationException, IOException, IdException {
		ObservableList<Raccolta> catalogo = FXCollections.observableArrayList();
		if(ControllerSystemState.getInstance().getTypeOfDb().equalsIgnoreCase("file"))
		{
			switch (type) {
				case LIBRO:
					csvDao.generaReport();
					catalogo.addAll(csvDao.retrieveAllData(new File("report/reportLibri.csv")));
					break;
				case GIORNALE:
					csvDao.generaReport();
					catalogo.addAll(csvDao.retrieveAllData(new File("report/reportGiornali.csv")));
					break;
				case RIVISTA:
					csvDao.generaReport();
					catalogo.addAll(csvDao.retrieveAllData(new File("report/reportRiviste.csv")));
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
