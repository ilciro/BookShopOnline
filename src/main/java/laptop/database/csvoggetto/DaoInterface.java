
package laptop.database.csvoggetto;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface   DaoInterface {
    boolean inserisciLibro(Libro l) throws IOException, CsvValidationException, IdException;
    boolean removeLibroById(Libro l) throws CsvValidationException, IOException;
    boolean inserisciGiornale(Giornale g) throws IOException, CsvValidationException, IdException;
    boolean removeGiornaleById(Giornale g) throws CsvValidationException,IOException;
    boolean inserisciRivista(Rivista r) throws IdException, CsvValidationException, IOException;
    boolean removeRivistaById(Rivista r) throws CsvValidationException, IOException;
   ObservableList<Raccolta> retrieveRaccoltaData(File fd) throws CsvValidationException, IOException, IdException;
   List<Libro> retrieveLibroData(Libro l) throws CsvValidationException, IOException, IdException;
   List<Giornale> retriveGiornaleData(Giornale g) throws CsvValidationException, IOException, IdException;
   List<Rivista> retrieveRivistaData( Rivista r) throws CsvValidationException, IOException, IdException;
    ObservableList<Libro> getLibroByIdTitoloAutore(Libro l) throws CsvValidationException, IOException, IdException;
    ObservableList<Giornale> getGiornaleByIdTitoloEditore(Giornale g) throws CsvValidationException, IOException, IdException;
    ObservableList<Rivista> getRivistaByIdTitoloEditore(Rivista r) throws CsvValidationException, IOException, IdException;
}




