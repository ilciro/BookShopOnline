package laptop.controller;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import laptop.database.PagamentoDao;
import laptop.model.Pagamento;

public class ControllerVisualizzaOrdine {
	
	private final PagamentoDao pD;
	
	public ObservableList<Pagamento> getDati() throws SQLException  {
		
		return pD.getPagamenti();
		}
	
	public ControllerVisualizzaOrdine()
	{
		pD=new PagamentoDao();
		
	}

}
