package laptop.terzoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerGestioneUtente;
import laptop.controller.ControllerSystemState;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestControllerUtenteElenco {

     private static final ControllerSystemState vis= ControllerSystemState.getInstance();
     private static final ControllerGestioneUtente cGU=new ControllerGestioneUtente();

     @ParameterizedTest
     @ValueSource(ints = {1,2,3,4,5,6,7,8})
     void testUserDataDB(int ints) throws SQLException {
         vis.setTypeOfDb("db");
         vis.setId(ints);
         assertNotNull(cGU.getDataDao());
     }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8})
    void testUserDataF(int ints) throws  CsvValidationException, IOException {
        vis.setTypeOfDb("file");
        vis.setId(ints);
        assertNotNull(cGU.getDataCSV().getEmail());
    }
}
