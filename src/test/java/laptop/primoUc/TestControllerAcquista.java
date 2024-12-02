package laptop.primoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerAcquista;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestControllerAcquista {

    // qui non faccio i test su file
     private  static final ResourceBundle RBLIBRO=ResourceBundle.getBundle("configurations/objects");
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ControllerAcquista cA=new ControllerAcquista();
     @Test
    void testGetNomeCostoDispLDB() throws CsvValidationException, IOException, IdException {
         vis.setTypeAsBook();
         vis.setTypeOfDb("db");
         vis.setId(Integer.parseInt(RBLIBRO.getString("idL")));
         String [] roba=cA.getNomeCostoDisp();
         assertNotEquals("",roba[0]);
     }

     @Test
    void testGetPrezzoLDB() throws CsvValidationException, IOException, IdException {
         vis.setTypeAsBook();
         vis.setTypeOfDb("db");
         vis.setId(Integer.parseInt(RBLIBRO.getString("idL")));
         assertNotEquals(0,cA.getPrezzo("3"));
     }





    @Test
    void testGetNomeCostoDispGDB() throws CsvValidationException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb("db");
        vis.setId(Integer.parseInt(RBLIBRO.getString("idG")));
        String [] roba=cA.getNomeCostoDisp();
        assertNotEquals("",roba[0]);
    }

    @Test
    void testGetPrezzoGDB() throws CsvValidationException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb("db");
        vis.setId(Integer.parseInt(RBLIBRO.getString("idG")));
        assertNotEquals(0,cA.getPrezzo("3"));
    }

    @Test
    void testGetNomeCostoDispRDB() throws CsvValidationException, IOException, IdException {
        vis.setTypeAsMagazine();
        vis.setTypeOfDb("db");
        vis.setId(Integer.parseInt(RBLIBRO.getString("idR")));
        String [] roba=cA.getNomeCostoDisp();
        assertNotEquals("",roba[0]);
    }

    @Test
    void testGetPrezzoRDB() throws CsvValidationException, IOException, IdException {
        vis.setTypeAsMagazine();
        vis.setTypeOfDb("db");
        vis.setId(Integer.parseInt(RBLIBRO.getString("idR")));
        assertNotEquals(0,cA.getPrezzo("3"));
    }

@Test
    void testGetNomeCostoDispLF() throws CsvValidationException, IOException, IdException {
        vis.setTypeAsBook();
        vis.setTypeOfDb("file");
        vis.setId(Integer.parseInt(RBLIBRO.getString("idL")));
        String [] roba=cA.getNomeCostoDisp();
        assertNotEquals("",roba[0]);
    }
    @Test
    void testGetPrezzoLF() throws CsvValidationException, IOException, IdException {
        vis.setTypeAsBook();
        vis.setTypeOfDb("file");
        vis.setId(Integer.parseInt(RBLIBRO.getString("idL")));
        assertNotEquals(0,cA.getPrezzo("3"));
    }

    @Test
    void testGetNomeCostoDispGF() throws CsvValidationException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb("file");
        vis.setId(Integer.parseInt(RBLIBRO.getString("idG")));
        String [] roba=cA.getNomeCostoDisp();
        assertNotEquals("",roba[0]);
    }

    @Test
    void testGetPrezzoGF() throws CsvValidationException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb("file");
        vis.setId(Integer.parseInt(RBLIBRO.getString("idG")));
        assertNotEquals(0,cA.getPrezzo("3"));
    }

    @Test
    void testGetNomeCostoDispRF() throws CsvValidationException, IOException, IdException {
        vis.setTypeAsMagazine();
        vis.setTypeOfDb("file");
        vis.setId(Integer.parseInt(RBLIBRO.getString("idR")));
        String [] roba=cA.getNomeCostoDisp();
        assertNotEquals("",roba[0]);
    }

    @Test
    void testGetPrezzoRF() throws CsvValidationException, IOException, IdException {
        vis.setTypeAsMagazine();
        vis.setTypeOfDb("file");
        vis.setId(Integer.parseInt(RBLIBRO.getString("idR")));
        assertNotEquals(0,cA.getPrezzo("3"));
    }






}
