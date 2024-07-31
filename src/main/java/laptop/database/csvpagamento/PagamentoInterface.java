package laptop.database.csvpagamento;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.CartaDiCredito;
import laptop.model.Fattura;
import laptop.model.Pagamento;

import java.io.IOException;

public interface PagamentoInterface {

     void inserisciFattura(Fattura f) throws CsvValidationException, IOException;

     void inserisciCartaCredito(CartaDiCredito cc) throws IOException, CsvValidationException;

     void inserisciPagamento(Pagamento p) throws IOException, CsvValidationException;

     ObservableList<CartaDiCredito> getAllDataCredito(String nome) throws CsvValidationException, IOException, IdException;
}