package laptop.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.ContrassegnoDao;
import laptop.database.csvpagamento.FatturaPagamentoCCredito;
import laptop.exception.IdException;
import laptop.model.Fattura;


public class ControllerPagamentoCash {
	private final ContrassegnoDao cD;

	private final ControllerSystemState vis= ControllerSystemState.getInstance();
	private final ControllerCheckPagamentoData cCPD;
	private final FatturaPagamentoCCredito fCsv;
	private final Fattura f;


	

	public void controlla(String nome, String cognome, String via, String com) throws SQLException, IdException, IOException, CsvValidationException {



			float spesa=vis.getSpesaT();


			this.f.setNome(nome);
			this.f.setCognome(cognome);
			this.f.setVia(via);
			this.f.setCom(com);
			this.f.setAmmontare(spesa);




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

		cCPD=new ControllerCheckPagamentoData();
		fCsv=new FatturaPagamentoCCredito();

		this.f=new Fattura();


		
	}


}
