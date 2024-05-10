package laptop.controller;


import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.itextpdf.text.DocumentException;
import laptop.database.ContrassegnoDao;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.PagamentoDao;
import laptop.database.RivistaDao;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;



public class ControllerDownload {
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
	private final ContrassegnoDao cDao;
	private final PagamentoDao pDao;
	private final LibroDao lD;
	private final Giornale g;
	private final GiornaleDao gD;
	private final RivistaDao rD;
	private final Rivista r;
	private final Libro l;

	private static final String LIBRO="libro";
	private static final String GIORNALE="giornale";
	private static final String RIVISTA="rivista";
	

	
	
	

	public void annullaOrdine() throws SQLException 	{
		/*
		 * MEtodo usato per cancellare pafamento e fatture.. ma con una query di ritardo
		 */
		boolean statusF;
		boolean statusP;
		String typeP=vis.getMetodoP(); //tipo pagamento
		String typeO=vis.getType(); //tipo oggetto
		
		int idF=cDao.retUltimoOrdineF(); //ultimo elemento (preso con count)
		statusF=cDao.annullaOrdineF(idF);
		
		int idP=pDao.retUltimoOrdinePag();
		statusP=pDao.annullaOrdinePag(idP);
		
		
		if(((typeP.equals("cash") &&(statusF && statusP))||(typeP.equals("cCredito") && statusP) )&& (typeO.equals(LIBRO)|| typeO.equals(GIORNALE)|| typeO.equals(RIVISTA  )))
		{
			incrementaOggetto(typeO);
		}
		// messo su come condizione		

	}
	public void scarica(String type) throws  IOException, URISyntaxException,  DocumentException {
		switch (type)
		{
			case LIBRO->
			{

				l.setId(vis.getId());
				l.scarica(vis.getId());
				l.leggi(vis.getId());

			}
			case GIORNALE->
			{
				g.setId(vis.getId());
				g.scarica(vis.getId());
				g.leggi(vis.getId());

			}
			case RIVISTA ->
			{
				r.setId(vis.getId());
				r.scarica(vis.getId());
				r.leggi(vis.getId());

			}
			default -> 	Logger.getLogger("Test scarica").log(Level.SEVERE,"download error");

		}
	}


	public ControllerDownload() throws IOException {

		l = new Libro();
		cDao=new ContrassegnoDao();
		pDao=new PagamentoDao();
		lD=new LibroDao();
		g=new Giornale();
		gD=new GiornaleDao();
		r=new Rivista();
		rD=new RivistaDao();
	}




	private void incrementaOggetto(String type)
	{
		switch (type)
		{
			case LIBRO->{
				l.setId(vis.getId());
				lD.incrementaDisponibilita(l);
			}
			case GIORNALE->{
				g.setId(vis.getId());
				gD.incrementaDisponibilita(g);
			}
			case RIVISTA->
			{

				r.setId(vis.getId());
				rD.incrementaDisponibilita(r);
			}
			default -> 	Logger.getLogger("Test incrementa").log(Level.SEVERE,"type not found");

		}
	}
	


}
