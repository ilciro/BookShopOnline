package laptop.primoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerCompravendita;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.exception.PersistenzaException;
import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class TestControllerCompravendita {
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static ControllerCompravendita cC ;


    @Test
    void testGetListaLibroDb() throws CsvValidationException, PersistenzaException, IOException, IdException {
        vis.setTypeOfDb("db");
        vis.setTypeAsBook();
        assertNotNull(cC.getLista(vis.getType()));
    }
    @Test
    void testGetListaGiornaleDB() throws CsvValidationException, PersistenzaException, IOException, IdException {
        vis.setTypeOfDb("db");
        vis.setTypeAsDaily();
        assertNotNull(cC.getLista(vis.getType()));
    }
    @Test
    void testGetListaRivistaDB() throws CsvValidationException, PersistenzaException, IOException, IdException {
        vis.setTypeOfDb("db");
        vis.setTypeAsMagazine();
        assertNotNull(cC.getLista(vis.getType()));
    }

    @Test
    void testGetListaLibroFile() throws CsvValidationException, PersistenzaException, IOException, IdException {
        vis.setTypeOfDb("file");
        vis.setTypeAsBook();
        assertNotNull(cC.getLista(vis.getType()));
    }
    @Test
    void testGetListaGiornaleFile() throws CsvValidationException, PersistenzaException, IOException, IdException {
        vis.setTypeOfDb("file");
        vis.setTypeAsDaily();
        assertNotNull(cC.getLista(vis.getType()));
    }
    @Test
    void testGetListaRivistaFile() throws CsvValidationException, PersistenzaException, IOException, IdException {
        vis.setTypeOfDb("file");
        vis.setTypeAsMagazine();
        assertNotNull(cC.getLista(vis.getType()));
    }


     public TestControllerCompravendita() throws IOException {
         cC=new ControllerCompravendita();
     }




}
