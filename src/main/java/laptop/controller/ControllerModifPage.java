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
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;

public class ControllerModifPage {
	private final LibroDao ld;
	private final Libro l;
	private final Giornale g;
	private final GiornaleDao gD;
	private final Rivista r;
	private final RivistaDao rD;
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
	
		
		public boolean checkDataG(String[] info) throws SQLException, CsvValidationException, IOException, IdException {



			g.setTitolo(info[0]);
			g.setTipologia(info[1]);
			g.setEditore(info[2]);
			g.setLingua(info[3]);
			g.setDataPubb(LocalDate.parse(info[4]));
			g.setDisponibilita(Integer.parseInt(info[5]));
			g.setPrezzo(Float.parseFloat(info[6]));
			g.setCopieRimanenti(Integer.parseInt(info[7]));

			if(vis.getTypeOfDb().equals("file"))
			{

				Giornale g1=csv.retriveGiornaleData(new File(LOCATIONG),g).get(0);
				csv.removeGiornaleById(g1);
				csv.inserisciGiornale(g);


			}
			
			else  gD.aggiornaGiornale(g);

			return true;
			
		}

		
		

		public boolean checkDataR(String [] info) throws SQLException, CsvValidationException, IOException, IdException {


			r.setTitolo(info[0]);
			r.setTipologia(info[1]);
			r.setAutore(info[2]);
			r.setLingua(info[3]);
			r.setEditore(info[4]);
			r.setDescrizione(info[5]);
			r.setDataPubb(LocalDate.parse(info[6]));
			r.setDisp(Integer.parseInt(info[7]));
			r.setPrezzo(Float.parseFloat(info[8]));
			r.setCopieRim(Integer.parseInt(info[9]));

			if (vis.getTypeOfDb().equals("file")) {
				Rivista r1=csv.retrieveRivistaData(new File(LOCATIONR),r).get(0);
				csv.removeRivistaById(r1);
				csv.inserisciRivista(r);

			} else {
				rD.aggiornaRivista(r);

			}

			return true;
			
		}
		
	
	
	public ControllerModifPage() throws IOException {
		ld=new LibroDao();
		l=new Libro();
		g=new Giornale();
		gD=new GiornaleDao();
		r=new Rivista();
		rD=new RivistaDao();
		csv=new CsvOggettoDao();
	}
	
	
	public boolean checkDataL(String []data) throws NullPointerException, CsvValidationException, IOException, IdException {

		boolean status;
		l.setTitolo(data[0]);
		l.setNrPagine(Integer.parseInt(data[1]));
		l.setCodIsbn(data[2]);
		l.setEditore(data[3]);
		l.setAutore(data[4]);
		l.setLingua(data[5]);
		l.setCategoria(data[6]);
		l.setDataPubb(LocalDate.parse(data[7]));
		l.setRecensione(data[8]);
		l.setNrCopie(Integer.parseInt(data[9]));
		l.setDesc(data[10]);
		l.setDisponibilita(Integer.parseInt(data[11]));
		l.setPrezzo(Float.parseFloat(data[12]));
		if(vis.getTypeOfDb().equalsIgnoreCase("file"))
		{

			Libro l1=csv.retrieveLibroData(new File(LOCATIONL),l).get(0);
			csv.removeLibroById(l1);
			csv.inserisciLibro(l);
			status=true;

		}
		else
			status=ld.aggiornaLibro(l);


		return status;

	}

	

}
