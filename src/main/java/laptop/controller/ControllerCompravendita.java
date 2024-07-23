package laptop.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import laptop.database.csv.CsvGiornaleDao;
import laptop.database.csv.CsvGiornaleDao;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.database.csv.CsvLibroDao;
import laptop.database.csv.CsvRivistaDao;
import laptop.exception.IdException;
import laptop.model.User;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;



public class ControllerCompravendita {
	//insrt comment
	private final LibroDao lD;
	private final User u = User.getInstance();
	private final GiornaleDao gD;
	private final Giornale g;
	private final Libro l;
	private final Rivista r;
	private final RivistaDao rD;
	private boolean status = false;
	private static final String LIBRO = "libro";
	private static final String RIVISTA = "rivista";
	private static final String GIORNALE = "giornale";
	private final ControllerSystemState vis=ControllerSystemState.getInstance();
	private final CsvGiornaleDao csvG ;
	private final CsvLibroDao csvL;
	private final CsvRivistaDao csvR;
	private final static String REPORTGIORNALI="report/reportGiornali.csv";
	private final static String REPORTLIBRI="report/reportLibri.csv";
	private final static String REPORTRIVISTE="report/reportRiviste.csv";






	public ControllerCompravendita() throws IOException {
		lD = new LibroDao();
		gD=new GiornaleDao();
		g=new Giornale();
		r=new Rivista();
		l=new Libro();
		rD=new RivistaDao();
		csvG=new CsvGiornaleDao();
		csvL=new CsvLibroDao();
		csvR=new CsvRivistaDao();
	}

	private void checkID(int id) throws IdException {
		if(id<=0)
		{
			throw new IdException("id not correct");
		}
	}

	public ObservableList<Raccolta> getLista(String type) throws IOException, CsvValidationException, IdException {


		ObservableList<Raccolta> catalogo = FXCollections.observableArrayList();

		if(vis.getTypeOfDb().equalsIgnoreCase("db")) {
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
					break;


			}
		} if(vis.getTypeOfDb().equalsIgnoreCase("file"))
		{
			switch (type) {
				case LIBRO:
					csvL.generaReport();
					catalogo.addAll(csvL.retrieveAllData(new File(REPORTLIBRI)));
					break;
				case GIORNALE:
					csvG.generaReport();
					catalogo.addAll(csvG.retrieveAllData(new File(REPORTGIORNALI)));
					break;
				case RIVISTA:
					csvR.generaReport();
					catalogo.addAll(csvR.retrieveAllData(new File(REPORTRIVISTE)));
					break;
				default:
					break;


			}
		}

	return catalogo;
	}

	public boolean disponibilita(String type, String i) throws SQLException, IdException {
		switch (type)
		{
			case LIBRO:

				l.setId(Integer.parseInt(i));
				checkID(Integer.parseInt(i));
				if(lD.getData(l).getDisponibilita()>0)
					status=true;

				break;
			case GIORNALE:
				g.setId(Integer.parseInt(i));
				checkID(Integer.parseInt(i));
				if(gD.getData(g).getDisponibilita()>0)
					status=true;
				break;
			case RIVISTA:
				r.setId(Integer.parseInt(i));
				checkID(Integer.parseInt(i));
				if(rD.getData(r).getDisp()>0)
					status=true;
				break;
			default: checkID(Integer.parseInt(i));

		}
		return status;
	}

	
	/*
	 * Metodo udato per tornare tipo utente in base a se � loggato o no

	 */
	public String retTipoUser()
	{
		return u.getIdRuolo();
	}
	//usato nel caso se non � loggato-> "utente"
	public void setTipoUser(String ruolo)
	{
		u.setIdRuolo(ruolo);	
	}






	
}
