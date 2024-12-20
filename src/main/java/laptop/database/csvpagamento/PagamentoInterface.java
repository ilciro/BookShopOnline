package laptop.database.csvpagamento;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.CartaDiCredito;
import laptop.model.Fattura;
import laptop.model.Pagamento;


import java.io.IOException;


public interface PagamentoInterface {

     void inserisciFattura(Fattura f) throws CsvValidationException, IOException, IdException;


     void inserisciPagamento(Pagamento p) throws IdException, CsvValidationException, IOException;


     void cancellaFattura( Fattura f) throws CsvValidationException, IOException;
     boolean cancellaPagamento(Pagamento p) throws CsvValidationException, IOException;

     void inserisciCartaCredito(CartaDiCredito cc) throws IOException, CsvValidationException;
     ObservableList<CartaDiCredito> getListaCartaCreditoByNome(CartaDiCredito cc) throws CsvValidationException, IOException;

     Pagamento ultimoPagamento() throws IOException, CsvValidationException;
     Fattura ultimaFattura() throws CsvValidationException, IOException;

     ObservableList<Pagamento> getPagamenti(Pagamento p) throws CsvValidationException, IOException;
}