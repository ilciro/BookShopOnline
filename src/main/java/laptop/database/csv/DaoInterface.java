
package laptop.database.csv;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;

import java.io.File;
import java.io.IOException;

public interface   DaoInterface {
    void generaReport() throws IOException;
    ObservableList<Raccolta> retrieveAllData(File fd) throws CsvValidationException, IOException, IdException;
    void inserisciLibro(Libro l) throws IOException, CsvValidationException;
    void inserisciGiornale(Giornale g) throws IOException, CsvValidationException;
    void inserisciRivista(Rivista r) throws IOException, CsvValidationException;
    void eliminaOggetto(File fd) throws CsvValidationException, IOException, IdException;
    void modificaLibro(File fd,Libro l) throws CsvValidationException, IOException;
    void modificaRivista(File file, Rivista r) throws CsvValidationException, IOException;
    void modificaGiornale(File file, Giornale g) throws CsvValidationException, IOException;
}


