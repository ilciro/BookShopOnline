package laptop.database.csvpagamento;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.CartaDiCredito;
import laptop.model.Fattura;
import laptop.model.Pagamento;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface PagamentoInterface {

     void inserisciFattura(Fattura f) throws CsvValidationException, IOException, IdException;

     void copiaFiles(Pagamento p) throws CsvValidationException, IOException, IdException;

     void inserisciPagamento(Pagamento p) throws IdException, CsvValidationException, IOException;

}