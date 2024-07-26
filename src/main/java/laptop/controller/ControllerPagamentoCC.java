package laptop.controller;

import java.io.IOException;
import java.sql.SQLException;

import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.database.*;

import laptop.database.csvpagamento.FatturaPagamentoCCredito;
import laptop.exception.IdException;
import laptop.model.CartaDiCredito;
import laptop.model.Pagamento;


public class ControllerPagamentoCC {
	private final CartaCreditoDao cDao;
	private String appoggio = "";
	private CartaDiCredito cc;
	private final PagamentoDao pDao;
	private final ControllerSystemState vis= ControllerSystemState.getInstance();

	private boolean state=false;


	private final FatturaPagamentoCCredito csv;
	
	
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

		csv=new FatturaPagamentoCCredito();
	}

	public void aggiungiCartaDB(String n, String c, String cod, java.sql.Date data, String civ, float prezzo)
            throws SQLException, IdException, CsvValidationException, IOException {
		
		
		
			cc = new CartaDiCredito(n, c, cod,  data, civ, prezzo);
						
			cDao.insCC(cc);

			if(vis.getTypeOfDb().equalsIgnoreCase("file"))
			{
				csv.inserisciCartaCredito(cc);
			}
						
			Pagamento p;
			 p=new Pagamento(0,"cc",0,cc.getNomeUser(),vis.getSpesaT(),null);
				p.setMetodo("cc");
				p.setNomeUtente(cc.getNomeUser());
				cCPD.checkPagamentoData(n);
								
				pDao.inserisciPagamento(p);
		
		

	}

	public ObservableList<CartaDiCredito> ritornaElencoCC(String nomeU) throws CsvValidationException, IOException, IdException {

        if(vis.getTypeOfDb().equalsIgnoreCase("file"))
		{
            csv.getAllDataCredito(nomeU);
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
		Pagamento p;
		p=new Pagamento(0,"cCredito", 0,nome,vis.getSpesaT(), null);

		cCPD.checkPagamentoData(nome);
		
		
		//ammontare,acquisto,idProd
		//settare in p
		
		java.util.logging.Logger.getLogger("Pagamento effettuato").log(Level.INFO, "info {0}",p.getAmmontare()+p.getTipo()+p.getId());



		// uso il pagamento in quanto guest
		//non ha carte registrate


		if(vis.getTypeOfDb().equalsIgnoreCase("file"))
		{
			csv.report();
		}
	}
	
}
