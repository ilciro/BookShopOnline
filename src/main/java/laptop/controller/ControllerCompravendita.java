package laptop.controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.GiornaleDao;
import laptop.database.RivistaDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.database.LibroDao;

import laptop.exception.IdException;
import laptop.exception.PersistenzaException;

import laptop.model.raccolta.Raccolta;



public class ControllerCompravendita {
	//insrt comment
	private final LibroDao lD;
	private final GiornaleDao gD;
	private final RivistaDao rD;
	private static final String LIBRO = "libro";
	private static final String RIVISTA = "rivista";
	private static final String GIORNALE = "giornale";
	private final ControllerSystemState vis=ControllerSystemState.getInstance();
	private final CsvOggettoDao csv ;

	private static final String REPORTGIORNALE="report/reportGiornale.csv";
	private static final String REPORTLIBRO="report/reportLibro.csv";
	private static final String REPORTRIVISTA="report/reportRivista.csv";






	public ControllerCompravendita() throws IOException {
		lD = new LibroDao();
		gD=new GiornaleDao();

		rD=new RivistaDao();
		csv=new CsvOggettoDao();


	}



	public ObservableList<Raccolta> getLista(String type) throws IOException, CsvValidationException, IdException, PersistenzaException {

	 ObservableList <Raccolta> catalogo=FXCollections.observableArrayList();
		if(vis.getTypeOfDb().equalsIgnoreCase("db")) {
			switch (type) {
				case LIBRO->catalogo.addAll(lD.getLibri());
				case GIORNALE->catalogo.addAll(gD.getGiornali());
				case RIVISTA->catalogo.addAll(rD.getRiviste());
				default->java.util.logging.Logger.getLogger("Test getId db").log(Level.INFO, "error !! list empty");

			}
		}
		if(vis.getTypeOfDb().equalsIgnoreCase("file"))
		{

			switch (type) {
				case LIBRO->catalogo.addAll(csv.retrieveRaccoltaData(new File(REPORTLIBRO)));
				case GIORNALE->	catalogo.addAll(csv.retrieveRaccoltaData(new File(REPORTGIORNALE)));
				case RIVISTA->catalogo.addAll(csv.retrieveRaccoltaData(new File(REPORTRIVISTA)));
				default->java.util.logging.Logger.getLogger("get lista").log(Level.SEVERE, " list is empty");
			}



		}
	return catalogo;
	}

	
}
