package laptop.terzoUcAdmin.raccolta;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerRaccolta;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestControllerRaccolta {

     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ControllerRaccolta cR;

    static {
        try {
            cR = new ControllerRaccolta();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testRaccoltaLibri(String strings) throws CsvValidationException, IOException, IdException {
         vis.setTypeAsBook();
         vis.setTypeOfDb(strings);
         assertNotNull(cR.getRaccoltaLista(vis.getType()));
     }
    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testRaccoltaGiornali(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb(strings);
        assertNotNull(cR.getRaccoltaLista(vis.getType()));
    }
    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testRaccoltaRiviste(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeAsMagazine();
        vis.setTypeOfDb(strings);
        assertNotNull(cR.getRaccoltaLista(vis.getType()));
    }
}
