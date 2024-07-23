
package laptop.database.csv;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.raccolta.Raccolta;

import java.io.File;
import java.io.IOException;

public interface   DaoInterface {
    void generaReport() throws IOException;
    ObservableList<Raccolta> retrieveAllData(File fd) throws CsvValidationException, IOException, IdException;

}


