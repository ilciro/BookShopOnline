
package laptop.database.csv;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.raccolta.Raccolta;

import java.io.File;
import java.io.IOException;

public interface   DaoInterface {
 //  List<Giornale> giornaliByIdTitoloEd(File fd,String titolo,String editore) throws IdException, IOException, CsvValidationException;
   //void insertGiornale(File fd,Giornale g) throws IOException;
   //void removeGiornale(File fd,Giornale g) throws IdException, CsvValidationException, IOException;
    //List<Giornale> giornaleById(File fd, int id) throws IdException, CsvValidationException, IOException;
    void generaReport() throws IOException;
    ObservableList<Raccolta> retrieveAllData(File fd) throws CsvValidationException, IOException, IdException;

}


