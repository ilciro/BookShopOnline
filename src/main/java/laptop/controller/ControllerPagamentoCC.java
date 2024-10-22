package laptop.controller;

import java.io.IOException;
import java.sql.SQLException;

import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.database.*;

import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.database.csvpagamento.FatturaPagamentoCCredito;
import laptop.database.csvreport.CsvReport;
import laptop.exception.IdException;
import laptop.model.CartaDiCredito;
import laptop.model.Pagamento;
import laptop.model.Report;
import laptop.model.User;
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

	private final CsvReport csvReport;
	private final Report report;

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

			pDao = new PagamentoDao();



			cCPD = new ControllerCheckPagamentoData();

			csvFattura = new FatturaPagamentoCCredito();
			csv = new CsvOggettoDao();
			csvReport=new CsvReport();
			report=new Report();

	}

	public void aggiungiCartaDB(String n, String c, String cod, java.sql.Date data, String civ, float prezzo)
            throws SQLException, IdException, CsvValidationException, IOException {
		
		
		
			cc = new CartaDiCredito(n, c, cod,  data, civ, prezzo);


			Pagamento p=new Pagamento();
			p.setIdPag(0);
			p.setMetodo("cc");
			p.setNomeUtente(cc.getNomeUser());


		if(vis.getTypeOfDb().equalsIgnoreCase("file"))
			{

				csvFattura.inserisciCartaCredito(cc);
				cCPD.checkPagamentoData(n);
				//inserisciCartaCredito

			}
			else
		{
			cDao.insCC(cc);
			cCPD.checkPagamentoData(n);

			pDao.inserisciPagamento(p);

		}

		
		

	}

	public ObservableList<CartaDiCredito> ritornaElencoCC(String nomeU) throws CsvValidationException, IOException {

		cc=new CartaDiCredito();
		ObservableList<CartaDiCredito> lista;
		cc.setNomeUser(nomeU);

		if(vis.getTypeOfDb().equalsIgnoreCase("file"))
			lista= csvFattura.getListaCartaCreditoByNome(cc);
		else  lista= cDao.getCarteCredito(nomeU);
		return lista;


	}
	


	public void pagamentoCC(String nome) throws SQLException, IdException, IOException, CsvValidationException {
		Pagamento p = new Pagamento();
		p.setIdPag(0);
		p.setMetodo("cCredito");
		p.setNomeUtente(nome);
		p.setAmmontare(vis.getSpesaT());
		p.setEmail(null);


		switch (vis.getType()) {
			case "libro" -> {


				if (vis.getTypeOfDb().equalsIgnoreCase("file")) {
					Libro l=new Libro();
					l.setId(vis.getId());
					Libro l1 = csv.retrieveLibroData(l).get(0);
					p.setTipo(l1.getCategoria());
					p.setIdOggetto(l1.getId());
					csvFattura.inserisciPagamento(p);
					checkData();
				} else {
					cCPD.checkPagamentoData(nome);
				}
			}
			case "giornale" -> {


				if (vis.getTypeOfDb().equalsIgnoreCase("file")) {
					Giornale g=new Giornale();
					g.setId(vis.getId());
					Giornale g1 = csv.retriveGiornaleData( g).get(0);
					p.setTipo(g1.getCategoria());
					p.setIdOggetto(g1.getId());
					csvFattura.inserisciPagamento(p);
					checkData();

				} else {
					cCPD.checkPagamentoData(nome);
				}

			}
			case "rivista" -> {


				if (vis.getTypeOfDb().equalsIgnoreCase("file")) {
					Rivista r=new Rivista();
					r.setId(vis.getId());
					Rivista r1 = csv.retrieveRivistaData(r).get(0);
					p.setTipo(r1.getCategoria());
					p.setIdOggetto(r1.getId());

					csvFattura.inserisciPagamento(p);
					checkData();

				} else {
					cCPD.checkPagamentoData(nome);
				}
			}
			default -> throw new IdException(" id not found");

		}



		java.util.logging.Logger.getLogger("Pagamento effettuato").log(Level.INFO, "Payment  done!!");

	}

	public String[] getInfo()
	{
		String [] dati=new String[2];
		dati[0]= User.getInstance().getNome();
		dati[1]=User.getInstance().getCognome();
		return dati;
	}

	private void checkData() throws CsvValidationException, IOException, IdException {
		report.setIdReport(0);
		report.setTipologiaOggetto(vis.getType());
		if(vis.getType().equals("libro"))
		{
			Libro l=new Libro();
			l.setId(vis.getId());
			report.setTitoloOggetto(csv.getLibroByIdTitoloAutore(l).get(0).getTitolo());
			report.setPrezzo(csv.getLibroByIdTitoloAutore(l).get(0).getPrezzo());
			report.setTotale(csv.getLibroByIdTitoloAutore(l).get(0).getPrezzo()*vis.getQuantita());

		} else if (vis.getType().equalsIgnoreCase("giornale")) {
			Giornale g=new Giornale();
			g.setId(vis.getId());
			report.setTitoloOggetto(csv.getGiornaleByIdTitoloEditore(g).get(0).getTitolo());
			report.setPrezzo(csv.getGiornaleByIdTitoloEditore(g).get(0).getPrezzo());
			report.setTotale(csv.getGiornaleByIdTitoloEditore(g).get(0).getPrezzo()*vis.getQuantita());

		} else if (vis.getType().equalsIgnoreCase("rivista")) {
			Rivista r=new Rivista();
			r.setId(vis.getId());
			report.setTitoloOggetto(csv.getRivistaByIdTitoloEditore(r).get(0).getTitolo());
			report.setPrezzo(csv.getRivistaByIdTitoloEditore(r).get(0).getPrezzo());
			report.setTotale(csv.getRivistaByIdTitoloEditore(r).get(0).getPrezzo()*vis.getQuantita());

		}
		report.setNrPezzi(vis.getQuantita());
		csvReport.inserisciReport(report);
	}

}

		

		


	

