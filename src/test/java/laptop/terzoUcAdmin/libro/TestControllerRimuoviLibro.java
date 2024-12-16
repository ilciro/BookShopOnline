package laptop.terzoUcAdmin.libro;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerRaccolta;
import laptop.controller.ControllerSystemState;
import laptop.database.LibroDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

 class TestControllerRimuoviLibro {
    private static final ControllerSystemState vis= ControllerSystemState.getInstance();
     private static final ControllerRaccolta cR;
     private static final LibroDao lD=new LibroDao();
     private static final Libro l=new Libro();
     private static final CsvOggettoDao csv;
     private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");


     static {
         try {
             cR = new ControllerRaccolta();
             csv=new CsvOggettoDao();
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
     }

     @BeforeEach
     void init()
     {
         vis.setId(0);
     }


     @Test
    void testRemoveLibroDB() throws CsvValidationException, IOException {
        vis.setTypeAsBook();
        vis.setTypeOfDb("db");
         l.setTitolo(RBOGGETTO.getString("titoloModL"));
         int id=lD.getLibroByIdTitoloAutoreLibro(l).get(0).getId();
         vis.setId(id);
         assertTrue(cR.elimina());


    }

     @Test
     void testRemoveLibroF() throws CsvValidationException, IOException, IdException {
         vis.setTypeAsBook();
         vis.setTypeOfDb("file");
         l.setTitolo(RBOGGETTO.getString("titoloModL"));
         int id=csv.getLibroByIdTitoloAutore(l).get(0).getId();
         vis.setId(id);
         assertTrue(cR.elimina());


     }

 }
