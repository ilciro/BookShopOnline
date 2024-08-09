package laptop.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.PagamentoDao;
import laptop.database.RivistaDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.database.csvpagamento.FatturaPagamentoCCredito;
import laptop.exception.IdException;
import laptop.model.Pagamento;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;

public class ControllerCheckPagamentoData {
	private final ControllerSystemState vis=ControllerSystemState.getInstance();
	private final Libro l;
	private final Giornale g;
	private final  Rivista r;
	private final LibroDao  lD;
	private final RivistaDao rD;
	private final PagamentoDao pagD;
	private final FatturaPagamentoCCredito csvFattura;
	private final CsvOggettoDao csv;
	private final GiornaleDao gD=new GiornaleDao();
	private static final String FATTURA="report/reportFattura.csv";
	private static final String PAGAMENTO="report/reportPagamento.csv";
	public void checkPagamentoData(String nome) throws SQLException, IdException, CsvValidationException, IOException {
		String tipo=vis.getType();
		
		Pagamento p;
		
		p=new Pagamento(0,"", 0, "",0, null);
			
		//inserire qui
		if(vis.getMetodoP().equals("cash"))
			p.setMetodo("cash");
		else p.setMetodo("cCredito");
		p.setNomeUtente(nome);


		p.setId(vis.getId());
		



		switch (tipo){
			case "libro"->
			{
				l.setId(vis.getId());
				checkID(vis.getId());
				p.setAmmontare(vis.getSpesaT());
				p.setIdOggetto(l.getId());
				if(vis.getTypeOfDb().equalsIgnoreCase("file"))
				{
					Libro l1=csv.retrieveLibroData(new File("report/reportLibro.csv"),l).get(0);
					p.setTipo(l1.getCategoria());


				}
				else {

					p.setTipo(lD.getData(l).getCategoria());
					pagD.inserisciPagamento(p);

				}
				csvFattura.copia(new File(FATTURA),new File(PAGAMENTO),p);


			}
			case "giornale" ->
			{
				g.setId(vis.getId());
				checkID(vis.getId());
				p.setAmmontare(vis.getSpesaT());
				p.setIdOggetto(g.getId());
				if(vis.getTypeOfDb().equalsIgnoreCase("file"))
				{
					Giornale g1 =csv.retriveGiornaleData(new File("report/reportGiornale.csv"),g).get(0);
					p.setTipo(g1.getTipologia());


				}
				else {

					p.setTipo(gD.getData(g).getTipologia());
					pagD.inserisciPagamento(p);

				}
				csvFattura.copia(new File(FATTURA),new File(PAGAMENTO),p);


			}
			case "rivista"->
			{
				r.setId(vis.getId());
				checkID(vis.getId());
				p.setAmmontare(vis.getSpesaT());
				p.setIdOggetto(r.getId());
				if(vis.getTypeOfDb().equalsIgnoreCase("file"))
				{
					Rivista r1=csv.retrieveRivistaData(new File("report/reportRivista.csv"),r).get(0);
					p.setTipo(r1.getTipologia());
					//sistemare qui


				}
				else {

					p.setTipo(rD.getData(r).getTipologia());
					pagD.inserisciPagamento(p);

				}
				csvFattura.copia(new File(FATTURA),new File(PAGAMENTO),p);



			}
			default->java.util.logging.Logger.getLogger("Test checkPagamento Data").log(Level.SEVERE," type not correct");

		}

		
	}
	public ControllerCheckPagamentoData() throws IOException {
		l=new Libro();
		g=new Giornale();
		r=new Rivista();
		lD=new LibroDao();
		rD=new RivistaDao();
		pagD=new PagamentoDao();
		csv=new CsvOggettoDao();
		csvFattura=new FatturaPagamentoCCredito();
		
	}
	private void checkID(int id) throws IdException {

		if (id<=0 || id>25)
		{

			throw new IdException("id not correct");
		}

	}


}
