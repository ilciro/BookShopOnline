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
	private final CsvOggettoDao csv ;

	private static final String REPORTGIORNALE="report/reportGiornale.csv";
	private static final String REPORTLIBRO="report/reportLibro.csv";
	private static final String REPORTRIVISTA="report/reportRivista.csv";






	public ControllerCompravendita() throws IOException {
		lD = new LibroDao();
		gD=new GiornaleDao();
		g=new Giornale();
		r=new Rivista();
		l=new Libro();
		rD=new RivistaDao();
		csv=new CsvOggettoDao();

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
				case LIBRO->catalogo.addAll(lD.getLibri());
				case GIORNALE->catalogo.addAll(gD.getGiornali());
				case RIVISTA->catalogo.addAll(rD.getRiviste());
				default->java.util.logging.Logger.getLogger("Test getId db").log(Level.INFO, "error !! list empty");

			}
		}
		if(vis.getTypeOfDb().equalsIgnoreCase("file"))
		{
			switch (type) {
				case LIBRO->catalogo.addAll(csv.retrieveAllData(new File(REPORTLIBRO)));
				case GIORNALE->	catalogo.addAll(csv.retrieveAllData(new File(REPORTGIORNALE)));
				case RIVISTA->catalogo.addAll(csv.retrieveAllData(new File(REPORTRIVISTA)));
				default->java.util.logging.Logger.getLogger("get lista").log(Level.SEVERE, " list is empty");



			}
		}

	return catalogo;
	}

	public boolean disponibilita(String type, String i) throws SQLException, IdException, CsvValidationException, IOException {
		switch (type) {
			case LIBRO -> {


			l.setId(Integer.parseInt(i));
			checkID(Integer.parseInt(i));
			if(vis.getTypeOfDb().equalsIgnoreCase("file"))
			{
				Libro l1=csv.retrieveAllLibroData(new File(REPORTLIBRO),l.getId(),"");
				vis.setId(l1.getId());
				vis.setIdOggetto(vis.getId());
				if(l1.getDisponibilita()>0)
					status=true;
			}
			else {


				if (lD.getData(l).getDisponibilita() > 0)
					status = true;
			}
		}
			case GIORNALE-> {
				g.setId(Integer.parseInt(i));
				checkID(Integer.parseInt(i));
				if(vis.getTypeOfDb().equalsIgnoreCase("file"))
				{
					Giornale g1=csv.retrieveAllGiornaleData(new File(REPORTGIORNALE),g.getId(),"");
					vis.setId(g1.getId());
					vis.setIdOggetto(vis.getId());
					if(g1.getDisponibilita()>0)
						status=true;
				}
				else {
					if (gD.getData(g).getDisponibilita() > 0)
						status = true;
				}
			}
			case RIVISTA-> {
				r.setId(Integer.parseInt(i));
				checkID(Integer.parseInt(i));
				if(vis.getTypeOfDb().equalsIgnoreCase("file"))
				{
					Rivista  r1=csv.retrieveAllRivistaData(new File(REPORTRIVISTA),r.getId(),"","");
					vis.setId(r1.getId());
					vis.setIdOggetto(vis.getId());
					if(r1.getDisp()>0)
						status=true;
				}
				else {
					if(rD.getData(r).getDisp()>0)
						return true;
				}
			}
			default-> checkID(Integer.parseInt(i));

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
