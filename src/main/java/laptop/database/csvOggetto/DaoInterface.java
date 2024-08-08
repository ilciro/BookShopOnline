
package laptop.database.csvOggetto;

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
    //ObservableList<Raccolta> retrieveAllData(File fd) throws CsvValidationException, IOException, IdException;
    void inserisciLibro(Libro l) throws IOException, CsvValidationException, IdException;
    void removeLibroById(Libro l) throws CsvValidationException, IOException;
    void inserisciGiornale(Giornale g) throws IOException, CsvValidationException, IdException;
    void removeGiornaleById(Giornale g) throws CsvValidationException,IOException;
    void inserisciRivista(Rivista r) throws IdException, CsvValidationException, IOException;
    void removeRivistaById(Rivista r) throws CsvValidationException, IOException;
   ObservableList<Raccolta> retrieveRaccoltaData(File fd) throws CsvValidationException, IOException, IdException;
   List<Libro> retrieveLibroData(File fd,Libro l) throws CsvValidationException, IOException, IdException;
   List<Giornale> retriveGiornaleData(File fd,Giornale g) throws CsvValidationException, IOException, IdException;
   List<Rivista> retrieveRivistaData(File fd, Rivista r) throws CsvValidationException, IOException, IdException;
}




