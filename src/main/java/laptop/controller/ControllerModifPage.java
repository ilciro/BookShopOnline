package laptop.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
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
	private final ControllerBookData cBD;

	
	public ObservableList<Libro> getLibriById(int id) throws SQLException {
		l.setId(id);
		return ld.getLibroIdTitoloAutore(l);
	}
	
	public ObservableList<Giornale> getGiornaliById(int id) throws SQLException, CsvValidationException, IOException, IdException {
		g.setId(id);
		return gD.getGiornaleIdTitoloAutore(g);
	}
	
		
		public void checkDataG(String[] info, LocalDate d, int dispo, float prezzo,
				int copie) throws SQLException, CsvValidationException, IOException {
			

			g.setTitolo(info[0]);
			g.setTipologia(info[1]);
			g.setEditore(info[2]);
			g.setLingua(info[3]);
			g.setDataPubb(d);
			g.setDisponibilita(dispo);
			g.setPrezzo(prezzo);
			g.setCopieRimanenti(copie);
			
			gD.aggiornaGiornale(g);

			
		}
		public ObservableList<Rivista> getRivistaById(int id) throws SQLException {
			r.setId(id);
			return rD.getRivistaIdTitoloAutore(r);
		}
		
		

		public void checkDataR(String [] info, LocalDate d,
				int dispo, float prezzo, int copie, int id, String desc) throws SQLException {
			
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
			
			rD.aggiornaRivista(r);
			
			
		}
		
	
	
	public ControllerModifPage() throws IOException {
		ld=new LibroDao();
		l=new Libro();
		g=new Giornale();
		gD=new GiornaleDao();
		r=new Rivista();
		rD=new RivistaDao();
		cBD=new ControllerBookData();
	}
	
	
	public boolean checkDataL(String []info,String recensione,String descrizione,LocalDate data,String[] infoCosti) throws NullPointerException
	{
		
		
		return ld.aggiornaLibro(cBD.checkBookData(info, recensione, descrizione, data, infoCosti));
	}
	
	

}
