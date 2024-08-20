package laptop.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.GiornaleDao;

import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;


public class ControllerAggiungiPage {

	private final GiornaleDao gD;
	private boolean status = false;
	private final Rivista r;
	private final RivistaDao rD;
	private final LibroDao lD;
	private static final ControllerSystemState vis= ControllerSystemState.getInstance();
	private  final CsvOggettoDao csv;
	private final Libro l;
	private final Giornale g;

	
	//funzione di aggiunta dei libri
	//e verifica dei dati inseriti 
	

	public boolean checkDataG(Giornale g) throws SQLException, IOException, CsvValidationException, IdException {
		if(g.getDataPubb()==null)
			throw new SQLException(" data is wrong");
		if(vis.getTypeOfDb().equalsIgnoreCase("file"))
		{

			csv.inserisciGiornale(g);
			status=true;
		}
		else

			status= gD.creaGiornale(g);
		return status;
	}
	public boolean checkDataR(String [] info) throws SQLException, IOException, CsvValidationException, IdException {
		
		if(info[6]==null)
		{
			throw new SQLException(" data is null");
		}

		

			
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




		if(vis.getTypeOfDb().equalsIgnoreCase("file"))
		{
			csv.inserisciRivista(r);
			status=true;
		}
		else status= rD.creaRivista(r);

		return status;
	}

	public boolean checkDataL(String [] data) throws CsvValidationException, IOException, IdException, SQLException {

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
		if (l.getCodIsbn().length() <= 10 && l.getDataPubb() != null) {

			switch (vis.getTypeOfDb()) {
				case "file" -> {

					csv.inserisciLibro(l);
					status = true;
				}

				case "db" ->
						status = lD.creaLibrio(l);
				default -> throw new SQLException(" data not correct");
			}




		}

		return status;
	}




	public ControllerAggiungiPage() throws IOException {
		gD=new GiornaleDao();
		r=new Rivista();
		rD=new RivistaDao();
		lD=new LibroDao();
		csv=new CsvOggettoDao();
		l=new Libro();
		g=new Giornale();

	}



}
