package laptop.terzoUcAdmin.tRicerca;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerRicerca;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestControllerRicercaGiornale {
    private static final ControllerSystemState vis= ControllerSystemState.getInstance();
    private static final ControllerRicerca cRic;

    static {
        try {
            cRic = new ControllerRicerca();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"La Republica6","La Republica"})
    void testRicercaGiornaleDb(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb("db");
        assertNotEquals(0,cRic.getIdOggetto(strings));
    }
    @ParameterizedTest
    @ValueSource(strings = {"La gazzetta del profeta","Hoepli"})
    void testRicercaLibroFile(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb("file");
        assertNotEquals(0,cRic.getIdOggetto(strings));
    }
}