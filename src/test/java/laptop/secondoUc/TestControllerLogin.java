package laptop.secondoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerLogin;
import laptop.controller.ControllerSystemState;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerLogin {
    private static final ControllerLogin cL=new ControllerLogin();
    private static final ResourceBundle RBUSERS=ResourceBundle.getBundle("configurations/users");
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();

    @ParameterizedTest
    @ValueSource(strings = {"db","file"})
    void loginAsAdmin(String strings) throws CsvValidationException, SQLException, IOException {
        vis.setTypeOfDb(strings);
        assertNotNull(cL.login(RBUSERS.getString("emailA"),RBUSERS.getString("passA") ));
    }

     @ParameterizedTest
     @ValueSource(strings = {"db","file"})
     void loginAsEditore(String strings) throws CsvValidationException, SQLException, IOException {
         vis.setTypeOfDb(strings);
         assertNotNull(cL.login(RBUSERS.getString("emailE"),RBUSERS.getString("passE") ));
     }

     @ParameterizedTest
     @ValueSource(strings = {"db","file"})
     void loginAsScrittore(String strings) throws CsvValidationException, SQLException, IOException {
         vis.setTypeOfDb(strings);
         assertNotNull(cL.login(RBUSERS.getString("emailS"),RBUSERS.getString("passS") ));
     }
     @ParameterizedTest
     @ValueSource(strings = {"db","file"})
     void loginAsUtente(String strings) throws CsvValidationException, SQLException, IOException {
         vis.setTypeOfDb(strings);
         assertNotNull(cL.login(RBUSERS.getString("emailU"),RBUSERS.getString("passU") ));
     }

     @ParameterizedTest
     @ValueSource(strings = {"db","file"})
     void utentePreneste(String strings) throws CsvValidationException, SQLException, IOException {
         vis.setTypeOfDb(strings);
         assertTrue(cL.userPresente(RBUSERS.getString("emailE"),RBUSERS.getString("passE") ));
     }
}
