package laptop.terzoUcAdmin;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerAdmin;
import laptop.controller.ControllerLogin;
import laptop.controller.ControllerRaccolta;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerRaccoltaListaElimina {

     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ControllerLogin cL=new ControllerLogin();
     private static final ControllerRaccolta cR;
     private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
     private static final ControllerAdmin cA=new ControllerAdmin();
    static {
        try {
            cR = new ControllerRaccolta();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testListaLibri(String strings) throws CsvValidationException, SQLException, IOException, IdException {
        //login
        vis.setTypeOfDb(strings);
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"));
        vis.setTypeAsBook();
        assertNotNull(cR.getRaccoltaLista(vis.getType()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testListaGiornali(String strings) throws CsvValidationException, SQLException, IOException, IdException {
        //login
        vis.setTypeOfDb(strings);

        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"));
        vis.setTypeAsDaily();
        assertNotNull(cR.getRaccoltaLista(vis.getType()));
    }
    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testListaRiviste(String strings) throws CsvValidationException, SQLException, IOException, IdException {
        //login
        vis.setTypeOfDb(strings);

        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"));
        vis.setTypeAsMagazine();
        vis.setTypeOfDb(strings);
        assertNotNull(cR.getRaccoltaLista(vis.getType()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testLogout(String strings) throws CsvValidationException, SQLException, IOException {


        vis.setTypeOfDb(strings);
        assertTrue(cA.logout(vis.getTypeOfDb()));
    }
}
