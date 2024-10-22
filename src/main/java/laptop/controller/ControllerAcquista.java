package laptop.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;


import com.opencsv.exceptions.CsvValidationException;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;

import laptop.database.RivistaDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.exception.AcquistaException;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;



public class ControllerAcquista {
	
	// Levatoil agamento in quanto lo faccio dopo a seconda del tipo
	 

	private final LibroDao lD;
	private final GiornaleDao gD;
	private final RivistaDao rD;
	private final Libro l;
	private final Giornale g;
	private final Rivista r;
	private static final ControllerSystemState vis = ControllerSystemState.getInstance() ;

    private static final String LIBRO = "libro";
	private static final String RIVISTA="rivista";
	private static final String GIORNALE="giornale";
	private int disp;
	private  CsvOggettoDao csv;


	public ControllerAcquista()  {
		lD=new LibroDao();
		gD=new GiornaleDao();
		rD=new RivistaDao();
		l=new Libro();
		g=new Giornale();
		r=new Rivista();
		try {
			csv = new CsvOggettoDao();
		}catch (IOException e)
		{
			java.util.logging.Logger.getLogger("costruttore acquista").log(Level.SEVERE," errore nel csotruttore");
		}
	}

	//uso una stringa di 3
	public String[] getNomeCostoDisp() throws CsvValidationException, IOException, IdException {
		String [] dati=new String[3];
		switch (vis.getType())
		{
			case LIBRO->
			{
				l.setId(vis.getId());
				if(vis.getTypeOfDb().equalsIgnoreCase("db")) {
					dati[0] = lD.getLibroByIdTitoloAutoreLibro(l).get(0).getTitolo();
					dati[1]= String.valueOf(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getPrezzo());
					dati[2]= String.valueOf(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getNrCopie());
				}

				else
				{
					dati[0]=csv.getLibroByIdTitoloAutore(l).get(0).getTitolo();
					dati[1]= String.valueOf(csv.getLibroByIdTitoloAutore(l).get(0).getPrezzo());
					dati[2]=String.valueOf(csv.getLibroByIdTitoloAutore(l).get(0).getNrCopie());
				}

			}
			case GIORNALE->
			{
				g.setId(vis.getId());
				if(vis.getTypeOfDb().equalsIgnoreCase("db")) {
					dati[0] = gD.getGiornaleIdTitoloAutore(g).get(0).getTitolo();
					dati[1]= String.valueOf(gD.getGiornaleIdTitoloAutore(g).get(0).getPrezzo());
					dati[2]=String.valueOf(gD.getGiornaleIdTitoloAutore(g).get(0).getCopieRimanenti());
				}
				else {
					dati[0] = csv.getGiornaleByIdTitoloEditore(g).get(0).getTitolo();
					dati[1]= String.valueOf(csv.getGiornaleByIdTitoloEditore(g).get(0).getPrezzo());
					dati[2]=String.valueOf(csv.getGiornaleByIdTitoloEditore(g).get(0).getCopieRimanenti());
				}
			}
			case RIVISTA->{
				r.setId(vis.getId());
				if(vis.getTypeOfDb().equalsIgnoreCase("db")) {
					dati[0] = rD.getRivistaIdTitoloAutore(r).get(0).getTitolo();
					dati[1]= String.valueOf(rD.getRivistaIdTitoloAutore(r).get(0).getPrezzo());
					dati[2]=String.valueOf(rD.getRivistaIdTitoloAutore(r).get(0).getCopieRim());
				}
				else {
					dati[0] = csv.getRivistaByIdTitoloEditore(r).get(0).getTitolo();
					dati[1]= String.valueOf(csv.getRivistaByIdTitoloEditore(r).get(0).getPrezzo());
					dati[2]=String.valueOf(csv.getRivistaByIdTitoloEditore(r).get(0).getCopieRim());
				}
			}
			default -> java.util.logging.Logger.getLogger("get nome").log(Level.SEVERE," name is wrong!!");
		}
		return dati;
	}


	public float getPrezzo(String q) throws CsvValidationException, IOException, IdException {
		int quantita=Integer.parseInt(q);
		float prezzo=Float.parseFloat(getNomeCostoDisp()[1]);
		vis.setSpesaT(prezzo);
        return quantita*prezzo;

	}
}
