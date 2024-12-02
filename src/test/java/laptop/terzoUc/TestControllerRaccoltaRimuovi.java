package laptop.terzoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerRaccolta;
import laptop.controller.ControllerSystemState;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerRaccoltaRimuovi {
     //parte tre della gestione di oggetto
     private static final ControllerRaccolta cR;
     private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    static {
        try {
            cR = new ControllerRaccolta();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //zero
    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testEliminaLibro(String strings) throws CsvValidationException, IOException {
        vis.setTypeOfDb(strings);
        vis.setTypeAsBook();
        vis.setId(20);
       assertTrue(cR.elimina());
    }
    //solo uno

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testEliminaGiornale(String strings) throws CsvValidationException, IOException {
        vis.setTypeOfDb(strings);
        vis.setTypeAsDaily();
        vis.setId(2);
        assertTrue(cR.elimina());

    }

    // ok entrambi

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testEliminaRivista(String strings) throws CsvValidationException, IOException {
        vis.setTypeOfDb(strings);
        vis.setTypeAsMagazine();
        vis.setId(1);
        assertTrue(cR.elimina());

    }


}


