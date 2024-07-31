package laptop.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


import com.opencsv.exceptions.CsvValidationException;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;

import laptop.database.RivistaDao;
import laptop.database.csv.CsvOggettoDao;
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

	private float costo;//aggiunto per costo (vedere metodo in fondo ((getCosto()))

	private static final String LIBRO = "libro";
	private static final String RIVISTA="rivista";
	private static final String GIORNALE="giornale";
	private int disp;
	private final CsvOggettoDao csv=new CsvOggettoDao();

	

	public float totale1 (String type,String titolo,int disp,int quantita) throws SQLException, IdException, CsvValidationException, IOException {
		float x;
		switch (type)
		{
			case LIBRO->
			{
				checkID(vis.getId());

				vis.setQuantita(quantita);
				// vedere csv
				if(vis.getTypeOfDb().equalsIgnoreCase("file"))
				{

					Libro l1=csv.retrieveAllLibroData(new File("report/reportLibro.csv"),l.getId(),titolo);
					x=l1.getPrezzo();


				}
				else {
					l.setTitolo(titolo);
					l.setNrCopie(disp);
					x = lD.getData(l).getPrezzo();
					lD.aggiornaDisponibilita(l);
				}
			}
			case GIORNALE->
			{
				checkID(vis.getId());
				g.setTitolo(titolo);
				g.setId(vis.getId());
				g.setCopieRimanenti(disp);
				vis.setQuantita(quantita);
				x = gD.getData(g).getPrezzo();
				gD.aggiornaDisponibilita(g);
			}
			case RIVISTA->
			{
				checkID(vis.getId());
				r.setTitolo(titolo);
				r.setId(vis.getId());
				r.setCopieRim(disp);
				vis.setQuantita(quantita);
				x= rD.getData(r).getPrezzo();
				rD.aggiornaDisponibilita(r);
			}
			default -> throw new IdException("id incorrect");

		}
		return x;
	}
	private void checkID(int id) throws IdException {

		if(id <=0 || id>30)
		{
			throw  new IdException("wrong id");
		}

	}



	public ControllerAcquista() throws IOException {
		lD = new LibroDao();
		gD = new GiornaleDao();
		rD = new RivistaDao();
		l = new Libro();
		g = new Giornale();
		r = new Rivista();
		
		

	}


	public void inserisciAmmontare(String type) throws AcquistaException, IdException, CsvValidationException, IOException {
		int rimanenza;
		switch(type)
		{
			case LIBRO-> {

				l.setId(vis.getId());
				if (vis.getTypeOfDb().equalsIgnoreCase("file")) {
					Libro l1 = csv.retrieveAllLibroData(new File("report/reportLibro.csv"), l.getId(), "");
					rimanenza = l1.getNrCopie();
					checkRimanenza(rimanenza);
				} else {
					rimanenza = lD.getData(l).getNrCopie();
					checkRimanenza(rimanenza);
				}
			}
			case GIORNALE-> {

				g.setId(vis.getId());
				if (vis.getTypeOfDb().equalsIgnoreCase("file")) {
					Giornale g1 = csv.retrieveAllGiornaleData(new File("report/reportGiornale.csv"), g.getId(), "");
					rimanenza = g1.getCopieRimanenti();
					checkRimanenza(rimanenza);
				} else {
					rimanenza = gD.getData(g).getCopieRimanenti();
					checkRimanenza(rimanenza);
				}}
			case RIVISTA-> {

				r.setId(vis.getId());
				if (vis.getTypeOfDb().equalsIgnoreCase("file")) {
					Rivista r1 = csv.retrieveAllRivistaData(new File("report/reportRivista.csv"), r.getId(), "","");
					rimanenza = r1.getCopieRim();
					checkRimanenza(rimanenza);
				} else {
					rimanenza = rD.getData(r).getCopieRim();
					checkRimanenza(rimanenza);
				}
			}
			default-> throw new IdException("incorrect id");


		}
	}

	private void checkRimanenza(int quantita) throws AcquistaException {
		if(quantita<0)
		{
			throw new AcquistaException("rimanenza <0");
		}
	}

	public String getNomeById() throws SQLException, CsvValidationException, IOException, IdException {
		 String name;


		int id = vis.getId();
		String type =vis.getType();
        switch (type) {
            case LIBRO -> {
                l.setId(id);
				if(vis.getTypeOfDb().equalsIgnoreCase("file")) {
					Libro l1 = csv.retrieveAllLibroData(new File("report/reportLibro.csv"), l.getId(), "");
					name=l1.getTitolo();
				}
				else {
					name = lD.getData(l).getTitolo();
				}
            }
            case GIORNALE -> {
                g.setId(id);
				if(vis.getTypeOfDb().equalsIgnoreCase("file")) {
					Giornale g1 = csv.retrieveAllGiornaleData(new File("report/reportGiornale.csv"), g.getId(), "");
					name=g1.getTitolo();
				}
				else {
					name = gD.getData(g).getTitolo();
				}
            }
            case RIVISTA -> {
                r.setId(id);
				if(vis.getTypeOfDb().equalsIgnoreCase("file")) {
					Rivista r1 = csv.retrieveAllRivistaData(new File("report/reportRivista.csv"), r.getId(), "","");
					name=r1.getTitolo();
				}
				else {
					name = rD.getData(r).getTitolo();
				}
            }
			default ->  throw new IdException(" id not match!!");


        }
		return name ;
	}

	/*
	 * metodo aggiunto per stampare appena carica la schermata anche il costo di 
	 * ogni singolo elemento(giornale,rivista o lbro)
	 */
	 
	public float getCosto(String type) throws SQLException, IdException, CsvValidationException, IOException {

		int id = vis.getId();
		checkID(id);
		switch (type)
		{
			case LIBRO -> {
				l.setId(id);
				if (vis.getTypeOfDb().equalsIgnoreCase("file")) {
					Libro l1 = csv.retrieveAllLibroData(new File("report/reportLibro.csv"), l.getId(), "");
					costo = l1.getPrezzo();
				} else
					costo = lD.getData(l).getPrezzo();
			}
			case GIORNALE-> {
				g.setId(id);
				if (vis.getTypeOfDb().equalsIgnoreCase("file")) {
					Giornale g1 = csv.retrieveAllGiornaleData(new File("report/reportGiornale.csv"), g.getId(), "");
					costo = g1.getPrezzo();
				} else
					costo = gD.getData(g).getPrezzo();
			}
			case RIVISTA-> {
				r.setId(id);
				if (vis.getTypeOfDb().equalsIgnoreCase("file")) {
					Rivista r1 = csv.retrieveAllRivistaData(new File("report/reportLibro.csv"), r.getId(), "","");
					costo = r1.getPrezzo();
				} else
					costo = rD.getData(r).getPrezzo();
			}
			default-> throw new IdException(" id not found!!");
		}



		return costo;

		
	}
	public int getDisp(String type) throws SQLException, IdException, CsvValidationException, IOException {

		switch (type) {
			case LIBRO -> {
				l.setId(vis.getId());
				if (vis.getTypeOfDb().equalsIgnoreCase("file")) {
					Libro l1 = csv.retrieveAllLibroData(new File("report/reportLibro.csv"), l.getId(), "");
					disp = l1.getNrCopie();
				} else
					disp = lD.getData(l).getNrCopie();

			}
			case GIORNALE -> {
				g.setId(vis.getId());
				if (vis.getTypeOfDb().equalsIgnoreCase("file")) {
					Giornale g1 = csv.retrieveAllGiornaleData(new File("report/reportGiornale.csv"), g.getId(), "");
					disp = g1.getCopieRimanenti();
				} else
					disp = gD.getData(g).getCopieRimanenti();
			}
			case RIVISTA -> {

				r.setId(vis.getId());
				if (vis.getTypeOfDb().equalsIgnoreCase("file")) {
					Rivista r1 = csv.retrieveAllRivistaData(new File("report/reportRivista.csv"), l.getId(), "","");
					disp = r1.getCopieRim();
				} else
					disp = rD.getData(r).getCopieRim();
			}
			default-> checkID(vis.getId());

		}
		return disp;
	}




}
