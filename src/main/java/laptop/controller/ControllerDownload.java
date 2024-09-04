package laptop.controller;


import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.itextpdf.text.DocumentException;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import laptop.database.ContrassegnoDao;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.PagamentoDao;
import laptop.database.RivistaDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.database.csvpagamento.FatturaPagamentoCCredito;
import laptop.exception.IdException;
import laptop.model.Fattura;
import laptop.model.Pagamento;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import org.apache.commons.lang.SystemUtils;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


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
	private static  FatturaPagamentoCCredito csv = null;


    private static CsvOggettoDao csvOggetto = null;

    static {
        try {
            csvOggetto = new CsvOggettoDao();
            csv = new FatturaPagamentoCCredito();
        } catch (IOException e) {
            Logger.getLogger("initialize").log(Level.SEVERE," error in intialize");
        }
    }


    public void annullaOrdine() throws SQLException, CsvValidationException, IOException, IdException {
		/*
		 * MEtodo usato per cancellare pafamento e fatture.. ma con una query di ritardo
		 */

		//fare versione db
		boolean statusF = false;
		boolean statusP=false;
		String typeP=vis.getMetodoP(); //tipo pagamento
		String typeO=vis.getType(); //tipo oggetto

		 if(vis.getTypeOfDb().equals("file"))
		 {


			 csv.cancellaFattura(new Fattura());
			 csv.cancellaPagamento(new Pagamento());


		 }
		 else {
			 int idF = cDao.retUltimoOrdineF(); //ultimo elemento (preso con count)
			 statusF = cDao.annullaOrdineF(idF);

			 int idP = pDao.retUltimoOrdinePag();
			 statusP = pDao.annullaOrdinePag(idP);


			 if (((typeP.equals("cash") && (statusF && statusP)) || (typeP.equals("cCredito") && statusP)) ) {
				 incrementaOggetto(typeO);
			 }
		 }
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




	private void incrementaOggetto(String type) throws CsvValidationException, IOException, IdException {
		switch (vis.getTypeOfDb())
		{
			case "db"->
			{
				switch (type) {
					case LIBRO -> {
						l.setId(vis.getId());
						lD.incrementaDisponibilita(l);
					}
					case GIORNALE -> {
						g.setId(vis.getId());
						gD.incrementaDisponibilita(g);
					}
					case RIVISTA -> {

						r.setId(vis.getId());
						rD.incrementaDisponibilita(r);
					}
				}

			}
			case "file"->
			{
				switch (type) {
					case LIBRO -> {
						l.setId(vis.getId());
						Libro l1=csvOggetto.retrieveLibroData(new File("report/reportLibro.csv"),l).get(0);
						csvOggetto.removeLibroById(l);
						csvOggetto.inserisciLibro(l1);
					}
					case GIORNALE -> {
						g.setId(vis.getId());
						Giornale g1=csvOggetto.retriveGiornaleData(new File("report/reportGiornale.csv"),g).get(0);
						csvOggetto.removeGiornaleById(g);
						csvOggetto.inserisciGiornale(g1);
					}
					case RIVISTA -> {

						r.setId(vis.getId());
						Rivista r1=csvOggetto.retrieveRivistaData(new File("report/reportRivista.csv"),r).get(0);
						csvOggetto.removeRivistaById(r);
						csvOggetto.inserisciRivista(r1);
					}
					default -> 	Logger.getLogger("Test incrementa").log(Level.SEVERE,"type not found");

				}

			}


		}
	}


	}



