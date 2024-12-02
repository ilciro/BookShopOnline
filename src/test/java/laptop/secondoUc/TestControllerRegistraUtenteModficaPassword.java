package laptop.secondoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerAggiornaPassword;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerRegistraUtenteModficaPassword {
     private static final ControllerAggiornaPassword cAP=new ControllerAggiornaPassword();
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final User u=User.getInstance();
     private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");

     @ParameterizedTest
    @ValueSource(strings = {"db","file"})
    void testUpdateUserEmail(String strings) throws CsvValidationException, SQLException, IOException {
         vis.setTypeOfDb(strings);
         u.setEmail(RBUTENTE.getString("email"));
         assertNotNull(cAP.getEmail());
     }

    @ParameterizedTest
    @ValueSource(strings = {"db","file"})
    void testUpdateUserPass(String strings) throws CsvValidationException, SQLException, IOException {
        vis.setTypeOfDb(strings);
        u.setPassword(RBUTENTE.getString("pwd"));
        assertNotNull(cAP.getEmail());
    }
    @ParameterizedTest
    @ValueSource(strings = {"db","file"})
    void testUpdatePass(String strings) throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeOfDb(strings);
        u.setEmail(RBUTENTE.getString("email"));
        assertTrue(cAP.aggiorna(RBUTENTE.getString("pwd1")));
    }


}
