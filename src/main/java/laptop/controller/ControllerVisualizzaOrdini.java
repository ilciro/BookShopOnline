package laptop.controller;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.database.PagamentoDao;
import laptop.database.csvpagamento.FatturaPagamentoCCredito;
import laptop.model.Pagamento;
import laptop.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerVisualizzaOrdini {
    private final FatturaPagamentoCCredito csv;
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final PagamentoDao pDAo;


    public ControllerVisualizzaOrdini() throws IOException {
        csv=new FatturaPagamentoCCredito();
        pDAo=new PagamentoDao();
    }

    public String getEmail()
    {
        return User.getInstance().getEmail();
    }

    public ObservableList<Pagamento> getLista() throws CsvValidationException, IOException {
        Pagamento p=new Pagamento();
        p.setEmail(User.getInstance().getEmail());
        ObservableList<Pagamento> list;
        if(vis.getTypeOfDb().equals("db"))
            list=pDAo.listPagamenti(p);
        else
            list=csv.getPagamenti(p);
        return list;
    }
    public boolean cancellaPagamento(int id) throws SQLException, CsvValidationException, IOException {
        boolean status = false;
        Pagamento p=new Pagamento();
        p.setIdPag(id);

        switch (vis.getTypeOfDb())
        {
            case "db"->{
                if(pDAo.cancellaPagamento(p))
                    status=true;
            }
            case "file"->{
                if(csv.cancellaPagamento(p))
                    status=true;
            }
            default -> Logger.getLogger("cancella Pagamento").log(Level.SEVERE," error with delete");
        }


        return status;
    }
}
