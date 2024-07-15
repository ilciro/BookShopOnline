package laptop.database;

import laptop.model.raccolta.Giornale;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface   DaoInterface {
   List<Giornale> giornaliByIdTitoloEd(File fd,String titolo,String editore) throws Exception;
   void insertGiornale(File fd,Giornale g) throws Exception;
   void removeGiornale(File fd,Giornale g) throws Exception;
    List<Giornale> giornaleById(File fd, int id) throws Exception;
    void generaReport() throws IOException;

}
