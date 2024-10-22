package laptop.controller;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.ContrassegnoDao;
import laptop.database.PagamentoDao;
import laptop.database.csvpagamento.FatturaPagamentoCCredito;
import laptop.model.Fattura;
import laptop.model.Pagamento;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerAnnullaPagamento {
    private final ContrassegnoDao cd;
    private final PagamentoDao pd;
    private final FatturaPagamentoCCredito csv;
    private final ControllerSystemState vis=ControllerSystemState.getInstance();

    public String getFattura() throws CsvValidationException, IOException {
        String fattura;
        if(vis.getTypeOfDb().equalsIgnoreCase("db"))
            fattura= cd.ultimaFattura().toString();
        else fattura=csv.ultimaFattura().toString();
        return fattura;

    }
    public String getPagamento() throws CsvValidationException, IOException {
        String pagamento;
        if(vis.getTypeOfDb().equalsIgnoreCase("db"))
            pagamento=pd.ultimoPagamento().toString();
        else pagamento=csv.ultimoPagamento().toString();
        return pagamento;
    }


    public ControllerAnnullaPagamento() throws IOException {
        cd=new ContrassegnoDao();
        pd=new PagamentoDao();
        csv=new FatturaPagamentoCCredito();
    }

    public boolean cancellaFattura(String text) throws SQLException, CsvValidationException, IOException {
        boolean status;
        if(vis.getTypeOfDb().equalsIgnoreCase("db"))
        {
            status=cd.cancellaFattura(Integer.parseInt(text));
        }
        else{
            Fattura f=new Fattura();
            f.setIdFattura(Integer.parseInt(text));
            csv.cancellaFattura(f);
            status=true;
        }
        return status;
    }
    public boolean cancellaPagamento(String text) throws SQLException, CsvValidationException, IOException {
        boolean status;
        Pagamento p=new Pagamento();
        p.setIdPag(Integer.parseInt(text));
        if(vis.getTypeOfDb().equalsIgnoreCase("db"))
        {
            status=pd.cancellaPagamento(p);
        }
        else
        {

            csv.cancellaPagamento(p);
            status=true;

        }
        return status;
    }
}
