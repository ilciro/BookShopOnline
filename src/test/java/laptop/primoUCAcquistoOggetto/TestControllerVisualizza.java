package laptop.primoUCAcquistoOggetto;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.ControllerVisualizza;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestControllerVisualizza {

     private  final ControllerSystemState vis = ControllerSystemState.getInstance();
     private static final ControllerVisualizza cV;

    static {
        try {
            cV = new ControllerVisualizza();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testInfoLibro(String strings) throws CsvValidationException, IOException, IdException {
       vis.setId(6);
        vis.setTypeOfDb(strings);
        assertNotNull(cV.getListLibro());
    }

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testInfoGiornale(String strings) throws CsvValidationException, IOException, IdException {
        vis.setId(1);
        vis.setTypeOfDb(strings);
        assertNotNull(cV.getListGiornale());
    }

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testInfoRivista(String strings) throws CsvValidationException, IOException, IdException {
        vis.setId(1);
        vis.setTypeOfDb(strings);
        assertNotNull(cV.getListRivista());
    }
}
