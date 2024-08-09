package laptop.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.database.csvOggetto.CsvOggettoDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;

public class ControllerModifPage {
	private final LibroDao ld;
	private Libro l;
	private final Giornale g;
	private final GiornaleDao gD;
	private final Rivista r;
	private final RivistaDao rD;
	private final ControllerBookData cBD;
	private final CsvOggettoDao csv;
	private final ControllerSystemState vis=ControllerSystemState.getInstance();
	private static final String LOCATIONL="report/reportLibro.csv";
	private static final String LOCATIONG="report/reportGiornale.csv";
	private static final String LOCATIONR="report/reportRivista.csv";

	
	public ObservableList<Libro> getLibriById(int id) throws SQLException, CsvValidationException, IOException, IdException {
		l.setId(id);
		if(vis.getTypeOfDb().equals("file"))
			return csv.getLibroByIdTitoloAutore(new File(LOCATIONL),l);
		else return ld.getLibroIdTitoloAutore(l);
	}
	
	public ObservableList<Giornale> getGiornaliById(int id) throws SQLException, CsvValidationException, IOException, IdException {
		g.setId(id);
		if(vis.getTypeOfDb().equals("file"))
			return csv.getGiornaleByIdTitoloEditore(new File(LOCATIONG),g);

		else return gD.getGiornaleIdTitoloAutore(g);
	}
	public ObservableList<Rivista> getRivistaById(int id) throws SQLException, CsvValidationException, IOException, IdException {
		r.setId(id);
		if(vis.getTypeOfDb().equals("file"))
			return csv.getRivistaByIdTitoloEditore(new File(LOCATIONR),r);
		else return rD.getRivistaIdTitoloAutore(r);
	}
	
		
		public int checkDataG(String[] info, LocalDate d, int dispo, float prezzo,
				int copie) throws SQLException, CsvValidationException, IOException, IdException {
			

			g.setTitolo(info[0]);
			g.setTipologia(info[1]);
			g.setEditore(info[2]);
			g.setLingua(info[3]);
			g.setDataPubb(d);
			g.setDisponibilita(dispo);
			g.setPrezzo(prezzo);
			g.setCopieRimanenti(copie);
			g.setId(vis.getId());
			if(ControllerSystemState.getInstance().getTypeOfDb().equals("file"))
			{
				Giornale g1=csv.retriveGiornaleData(new File(LOCATIONG),g).get(0);
				csv.removeGiornaleById(g1);
				csv.inserisciGiornale(g);
				return 1;

			}
			
			else return gD.aggiornaGiornale(g);

			
		}

		
		

		public int checkDataR(String [] info, LocalDate d,
				int dispo, float prezzo, int copie, int id, String desc) throws SQLException, CsvValidationException, IOException, IdException {

			int state = 0;
			r.setTitolo(info[0]);
			r.setTipologia(info[1]);
			r.setAutore(info[2]);
			r.setLingua(info[3]);
			r.setEditore(info[4]);
			r.setDescrizione(desc);
			r.setDataPubb(d);
			r.setDisp(dispo);
			r.setPrezzo(prezzo);
			r.setCopieRim(copie);
			r.setId(id);

			if (ControllerSystemState.getInstance().getTypeOfDb().equals("file")) {
				Rivista r1=csv.retrieveRivistaData(new File(LOCATIONR),r).get(0);
				csv.removeRivistaById(r1);
				csv.inserisciRivista(r);
				state = 1;
			} else
				state = rD.aggiornaRivista(r);

			return state;
			
		}
		
	
	
	public ControllerModifPage() throws IOException {
		ld=new LibroDao();
		l=new Libro();
		g=new Giornale();
		gD=new GiornaleDao();
		r=new Rivista();
		rD=new RivistaDao();
		cBD=new ControllerBookData();
		csv=new CsvOggettoDao();
	}
	
	
	public boolean checkDataL(String []info,String recensione,String descrizione,LocalDate data,String[] infoCosti) throws NullPointerException, CsvValidationException, IOException, IdException {

		boolean status=false;
		if(vis.getTypeOfDb().equalsIgnoreCase("file"))
		{

			l = cBD.checkBookData(info, recensione, descrizione, data, infoCosti);
			Libro l1=csv.retrieveLibroData(new File(LOCATIONL),l).get(0);
			csv.removeLibroById(l1);
			csv.inserisciLibro(l);
			status=true;

		}
		else
			status=ld.aggiornaLibro(cBD.checkBookData(info, recensione, descrizione, data, infoCosti));


		return status;

	}

	

}
