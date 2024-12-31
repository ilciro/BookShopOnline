package laptop.controller;


import java.io.*;
import java.net.URISyntaxException;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.itextpdf.text.DocumentException;


import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;



public class ControllerDownload {
    private final ControllerSystemState vis=ControllerSystemState.getInstance();

	private final Giornale g;
	private final Rivista r;
	private final Libro l;

	private static final String LIBRO="libro";
	private static final String GIORNALE="giornale";
	private static final String RIVISTA="rivista";

	private final LibroDao lD;
	private final RivistaDao rD;
	private final GiornaleDao gD;







	public void scarica(String type) throws IOException, URISyntaxException, DocumentException, SQLException {
		switch (type)
		{
			case LIBRO->
			{

				l.setId(vis.getId());
				l.scarica(vis.getId());
				l.leggi(vis.getId());
				lD.aggiornaDisponibilita(l);

			}
			case GIORNALE->
			{
				g.setId(vis.getId());
				g.scarica(vis.getId());
				g.leggi(vis.getId());
				gD.aggiornaDisponibilita(g);

			}
			case RIVISTA ->
			{
				r.setId(vis.getId());
				r.scarica(vis.getId());
				r.leggi(vis.getId());
				rD.aggiornaDisponibilita(r);


			}
			default -> 	Logger.getLogger("Test scarica").log(Level.SEVERE,"download error");

		}
	}


	public ControllerDownload() throws IOException {


        l = new Libro();

		g=new Giornale();

		r=new Rivista();

		lD=new LibroDao();
		gD=new GiornaleDao();
		rD=new RivistaDao();

	}




	}



