package laptop.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.database.*;

import laptop.database.csv.CsvOggettoDao;
import laptop.database.csvpagamento.FatturaPagamentoCCredito;
import laptop.exception.IdException;
import laptop.model.CartaDiCredito;
import laptop.model.Pagamento;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;


public class ControllerPagamentoCC {
	private final CartaCreditoDao cDao;
	private String appoggio = "";
	private CartaDiCredito cc;
	private final PagamentoDao pDao;
	private final ControllerSystemState vis= ControllerSystemState.getInstance();

	private boolean state=false;


	private final FatturaPagamentoCCredito csvFattura;
	private final CsvOggettoDao csv;
	
	
	private int cont=0;
	private final ControllerCheckPagamentoData cCPD;

	public boolean controllaPag(String d, String c,String civ) {
		try {
			int x;

			int anno;
			int mese;
			int giorno;
			String[] verifica;


			appoggio = appoggio + d;
			anno = Integer.parseInt((String) appoggio.subSequence(0, 4));
			mese = Integer.parseInt((String) appoggio.subSequence(5, 7));
			giorno = Integer.parseInt((String) appoggio.subSequence(8, 10));

			if (anno > 2020 && (mese >= 1 && mese <= 12) && (giorno >= 1 && giorno <= 31) && c.length() <= 20 && civ.length() == 3) {


				verifica = c.split("-");

				for (x = 0; x < verifica.length; x++) {
					if (verifica[x].length() == 4) {
						cont++;
					}
				}
				if (cont == 4) {
					state = true;
				}


			}
		}catch (NumberFormatException e)
		{
			java.util.logging.Logger.getLogger("procedi cash").log(Level.SEVERE,"\n errore nel pagamento");
		}
		
		return state;

	}

	public ControllerPagamentoCC() throws IOException {
		
		cDao = new CartaCreditoDao();
		
		pDao=new PagamentoDao();
		
		cCPD=new ControllerCheckPagamentoData();

		csvFattura=new FatturaPagamentoCCredito();
		csv=new CsvOggettoDao();
	}

	public void aggiungiCartaDB(String n, String c, String cod, java.sql.Date data, String civ, float prezzo)
            throws SQLException, IdException, CsvValidationException, IOException {
		
		
		
			cc = new CartaDiCredito(n, c, cod,  data, civ, prezzo);


			Pagamento p;
			p=new Pagamento(0,"cc",0,cc.getNomeUser(),vis.getSpesaT(),null);
			p.setMetodo("cc");
			p.setNomeUtente(cc.getNomeUser());


		if(vis.getTypeOfDb().equalsIgnoreCase("file"))
			{
				csvFattura.inserisciCartaCredito(cc);

				cCPD.checkPagamentoData(n);
			}
			else
		{
			cDao.insCC(cc);
			cCPD.checkPagamentoData(n);

			pDao.inserisciPagamento(p);

		}

		
		

	}

	public ObservableList<CartaDiCredito> ritornaElencoCC(String nomeU) throws CsvValidationException, IOException, IdException {

        if(vis.getTypeOfDb().equalsIgnoreCase("file"))
		{
            csvFattura.getAllDataCredito(nomeU);
        }

		return cDao.getCarteCredito(nomeU);


	}
	
	public CartaDiCredito tornaDalDb(String codiceCarta)
	{
		cc=new CartaDiCredito();
		cc.setNumeroCC(codiceCarta);
		return cDao.popolaDati(cc);
	}

	public void pagamentoCC(String nome) throws SQLException, IdException, IOException, CsvValidationException {
		Pagamento p = new Pagamento(0, "cCredito", 0, nome, vis.getSpesaT(), null);

		switch (vis.getType()) {
			case "libro" -> {
				Libro l1 = csv.retrieveAllLibroData(new File("report/reportLibro.csv"), vis.getId(), "");
				p.setTipo(l1.getCategoria());
				p.setIdOggetto(vis.getIdOggetto());

				if (vis.getTypeOfDb().equalsIgnoreCase("file")) {
					//inserito anche qui
					//ma visto che non è loggato
					//non importa dei dati
					csvFattura.inserisciCartaCredito(new CartaDiCredito());
					csvFattura.inserisciPagamento(p);
				} else {
					cCPD.checkPagamentoData(nome);
				}
			}
			case "giornale" -> {
				Giornale g1 = csv.retrieveAllGiornaleData(new File("report/reportgiornale.csv"), vis.getId(), "");
				p.setTipo(g1.getTipologia());
				p.setIdOggetto(vis.getIdOggetto());

				if (vis.getTypeOfDb().equalsIgnoreCase("file")) {
					//inserito anche qui
					//ma visto che non è loggato
					//non importa dei dati
					csvFattura.inserisciCartaCredito(new CartaDiCredito());
					csvFattura.inserisciPagamento(p);
				} else {
					cCPD.checkPagamentoData(nome);
				}

			}
			case "rivista" -> {
				Rivista r1 = csv.retrieveAllRivistaData(new File("report/reportRivista.csv"), vis.getId(), "","");
				p.setTipo(r1.getTipologia());
				p.setIdOggetto(vis.getIdOggetto());

				if (vis.getTypeOfDb().equalsIgnoreCase("file")) {
					//inserito anche qui
					//ma visto che non è loggato
					//non importa dei dati
					csvFattura.inserisciCartaCredito(new CartaDiCredito());
					csvFattura.inserisciPagamento(p);
				} else {
					cCPD.checkPagamentoData(nome);
				}
			}
			default -> throw new IdException(" id not found");

		}

		java.util.logging.Logger.getLogger("Pagamento effettuato").log(Level.INFO, "info {0}",p.getAmmontare()+p.getTipo()+p.getId());

	}

}

		

		


	

