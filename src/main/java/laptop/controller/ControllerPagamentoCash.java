package laptop.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.ContrassegnoDao;
import laptop.database.csvpagamento.FatturaPagamentoCCredito;
import laptop.exception.IdException;
import laptop.model.Fattura;
import laptop.model.User;


public class ControllerPagamentoCash {
	private final ContrassegnoDao cD;

	private final ControllerSystemState vis= ControllerSystemState.getInstance();
	private final ControllerCheckPagamentoData cCPD;
	private final FatturaPagamentoCCredito fCsv;



	

	public void controlla(String nome, String cognome, String via, String com) throws SQLException, IdException, IOException, CsvValidationException {



			Fattura f=new Fattura(nome,cognome,via,com,vis.getSpesaT(),0);





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





		
	}

	public String[] getInfo()
	{
		String [] dati=new String[2];
		dati[0]= User.getInstance().getNome();
		dati[1]=User.getInstance().getCognome();
		return dati;
	}


}
