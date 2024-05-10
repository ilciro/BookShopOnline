package laptop.controller;

import java.io.IOException;
import java.sql.SQLException;

import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.PagamentoDao;
import laptop.database.RivistaDao;
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
	private final GiornaleDao gD;
	private final RivistaDao rD;
	private final PagamentoDao pagD;
	public void checkPagamentoData(String nome) throws SQLException, IdException {
		String tipo=vis.getType();
		
		Pagamento p;
		
		p=new Pagamento(0,"", 0, "",0, null);
			
		//inserire qui
		p.setMetodo("cash");
		p.setNomeUtente(nome);
		
		p.setId(vis.getId());
		
		
		
		pagD.inserisciPagamento(p);

		switch (tipo){
			case "libro":
			{
				l.setId(vis.getId());
				checkID(vis.getId());
				p.setAmmontare(vis.getSpesaT());
				p.setId(l.getId());
				p.setTipo(lD.getData(l).getCategoria());
				break;
			}
			case "giornale" :
			{
				g.setId(vis.getId());
				checkID(vis.getId());
				p.setAmmontare(vis.getSpesaT());
				p.setId(g.getId());
				p.setTipo(gD.getData(g).getTipologia());
				break;
			}
			case "rivista":
			{
				r.setId(vis.getId());
				checkID(vis.getId());
				p.setAmmontare(vis.getSpesaT());
				p.setId(r.getId());
				p.setTipo(rD.getData(r).getTipologia());
				break;
			}
			default: break;
		}

		
		
	}
	public ControllerCheckPagamentoData() throws IOException {
		l=new Libro();
		g=new Giornale();
		r=new Rivista();
		lD=new LibroDao();
		rD=new RivistaDao();
		gD=new GiornaleDao();
		pagD=new PagamentoDao();
		
	}
	private void checkID(int id) throws IdException {

		if (id<=0 || id>25)
		{

			throw new IdException("id not correct");
		}

	}


}
