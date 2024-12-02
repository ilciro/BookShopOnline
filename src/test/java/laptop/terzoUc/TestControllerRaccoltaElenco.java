package laptop.terzoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerRaccolta;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestControllerRaccoltaElenco {

    //sotto parte della stessa classe di test
    //mostra solo la lista
     private static final ControllerRaccolta cRacc;
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();

    static {
        try {
            cRacc = new ControllerRaccolta();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testRaccoltaListaL(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeOfDb(strings);
        vis.setTypeAsBook();
        assertNotNull(cRacc.getRaccoltaLista(vis.getType()));

    }
    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testRaccoltaListaG(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeOfDb(strings);
        vis.setTypeAsDaily();
        assertNotNull(cRacc.getRaccoltaLista(vis.getType()));

    }
    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testRaccoltaListaR(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeOfDb(strings);
        vis.setTypeAsMagazine();
        assertNotNull(cRacc.getRaccoltaLista(vis.getType()));

    }


}
