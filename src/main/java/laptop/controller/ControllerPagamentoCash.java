package laptop.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.ContrassegnoDao;
import laptop.database.csvpagamento.FatturaPagamentoCCredito;
import laptop.exception.IdException;
import laptop.model.Fattura;
import laptop.model.Pagamento;


public class ControllerPagamentoCash {
	private final ContrassegnoDao cD;
	private final Fattura f;
	private final ControllerSystemState vis= ControllerSystemState.getInstance();
	private final ControllerCheckPagamentoData cCPD;
	private final FatturaPagamentoCCredito fCsv;

	

	public void controlla(String nome, String cognome, String via, String com) throws SQLException, IdException, IOException, CsvValidationException {
		
			
			float spesa=vis.getSpesaT();


			f.setNome(nome);
			f.setCognome(cognome);
			f.setVia(via);
			f.setCom(com);
			f.setAmmontare(spesa);



		if(vis.getTypeOfDb().equalsIgnoreCase("file"))
			{

				fCsv.inserisciFattura(f);
				cCPD.checkPagamentoData(f.getNome());

			}

			else {
				cD.inserisciFattura(f);
				cCPD.checkPagamentoData(f.getNome());

		}

			
	}

	public ControllerPagamentoCash() throws IOException {
		cD = new ContrassegnoDao();
		f = new Fattura();
		cCPD=new ControllerCheckPagamentoData();
		fCsv=new FatturaPagamentoCCredito();

		
	}

}
