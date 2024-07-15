package laptop.database;

import com.opencsv.exceptions.CsvValidationException;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface   DaoInterface {
   List<Giornale> giornaliByIdTitoloEd(File fd,String titolo,String editore) throws IdException, IOException, CsvValidationException;
   void insertGiornale(File fd,Giornale g) throws IOException;
   void removeGiornale(File fd,Giornale g) throws IdException, CsvValidationException, IOException;
    List<Giornale> giornaleById(File fd, int id) throws IdException, CsvValidationException, IOException;
    void generaReport() throws IOException;

}
