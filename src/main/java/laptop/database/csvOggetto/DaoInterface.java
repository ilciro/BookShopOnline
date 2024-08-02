
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

public interface   DaoInterface {
    ObservableList<Raccolta> retrieveAllData(File fd) throws CsvValidationException, IOException, IdException;
    void inserisciLibro(Libro l) throws IOException, CsvValidationException;
    void inserisciGiornale(Giornale g) throws IOException, CsvValidationException;
    void inserisciRivista(Rivista r) throws IOException, CsvValidationException;
    void eliminaOggetto(File fd) throws CsvValidationException, IOException, IdException;
    void modificaLibro(File fd,Libro l) throws CsvValidationException, IOException, IdException;
    void modificaRivista(File file, Rivista r) throws CsvValidationException, IOException, IdException;
    void modificaGiornale(File file, Giornale g) throws CsvValidationException, IOException, IdException;
    Libro retrieveAllLibroData(File fd,int id,String titolo) throws CsvValidationException, IOException, IdException;
    Giornale retrieveAllGiornaleData(File fd,int id,String editore) throws CsvValidationException, IOException, IdException;
    Rivista retrieveAllRivistaData(File fd,int id,String editore,String autore)throws CsvValidationException, IOException, IdException;
}



