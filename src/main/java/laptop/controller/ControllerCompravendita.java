package laptop.controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.GiornaleDao;
import laptop.database.RivistaDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.database.LibroDao;

import laptop.exception.IdException;

import laptop.model.raccolta.Raccolta;



public class ControllerCompravendita {
	//insrt comment
	private final LibroDao lD;
	private final GiornaleDao gD;
	private final RivistaDao rD;

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



	public ObservableList<Raccolta> getLista(String type) throws IOException, CsvValidationException, IdException {

	 ObservableList <Raccolta> catalogo=FXCollections.observableArrayList();

	 switch (type)
	 {
		 case "libro" -> {
			 if(vis.getTypeOfDb().equalsIgnoreCase("db"))
				 catalogo.addAll(lD.getLibri());
			 else catalogo.addAll(csv.retrieveRaccoltaData(new File(REPORTLIBRO)));

		 }
		 case "giornale"->{
			 if(vis.getTypeOfDb().equalsIgnoreCase("db"))
				 catalogo.addAll(gD.getGiornali());
			 else catalogo.addAll(csv.retrieveRaccoltaData(new File(REPORTGIORNALE)));


		 }
		 case "rivista"->{
			 if(vis.getTypeOfDb().equalsIgnoreCase("db"))
				 catalogo.addAll(rD.getRiviste());
			 else catalogo.addAll(csv.retrieveRaccoltaData(new File(REPORTRIVISTA)));

		 }
		 default-> Logger.getLogger("get lista").log(Level.SEVERE, " list is empty");

	 }

	return catalogo;
	}

	
}
