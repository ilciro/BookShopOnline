package laptop.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.PagamentoDao;
import laptop.database.RivistaDao;
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
	private final FatturaPagamentoCCredito csv;
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
			case "libro":
			{
				l.setId(vis.getId());
				checkID(vis.getId());
				p.setAmmontare(vis.getSpesaT());
				p.setIdOggetto(l.getId());
				p.setTipo(lD.getData(l).getCategoria());

				break;
			}
			case "giornale" :
			{
				g.setId(vis.getId());
				checkID(vis.getId());
				p.setAmmontare(vis.getSpesaT());
				p.setIdOggetto(g.getId());
				p.setTipo("QIOTIDIANO");
				break;
			}
			case "rivista":
			{
				r.setId(vis.getId());
				checkID(vis.getId());
				p.setAmmontare(vis.getSpesaT());
				p.setIdOggetto(r.getId());
				p.setTipo(rD.getData(r).getTipologia());
				break;
			}
			default: break;
		}
		pagD.inserisciPagamento(p);
		if(vis.getTypeOfDb().equalsIgnoreCase("file"))
		{
			csv.report();
			csv.inserisciPagamento(p);
		}

		
		
	}
	public ControllerCheckPagamentoData() throws IOException {
		l=new Libro();
		g=new Giornale();
		r=new Rivista();
		lD=new LibroDao();
		rD=new RivistaDao();
		pagD=new PagamentoDao();
		csv=new FatturaPagamentoCCredito();
		
	}
	private void checkID(int id) throws IdException {

		if (id<=0 || id>25)
		{

			throw new IdException("id not correct");
		}

	}


}
