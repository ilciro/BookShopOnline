package laptop.database.csvpagamento;

import com.opencsv.exceptions.CsvValidationException;
import laptop.model.CartaDiCredito;
import laptop.model.Fattura;
import laptop.model.Pagamento;

import java.io.IOException;

public interface PagamentoInterface {
     void report() throws IOException;
     void inserisciFattura(Fattura f) throws CsvValidationException, IOException;
     void inserisciCartaCredito(CartaDiCredito cc) throws IOException, CsvValidationException;
     void inserisciPagamento(Pagamento p) throws IOException, CsvValidationException;
}
