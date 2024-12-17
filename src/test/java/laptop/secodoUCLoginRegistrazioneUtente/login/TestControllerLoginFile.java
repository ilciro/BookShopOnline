package laptop.secodoUCLoginRegistrazioneUtente.login;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerAdmin;
import laptop.controller.ControllerLogin;
import laptop.controller.ControllerSystemState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestControllerLoginFile {
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private static final ControllerLogin cL=new ControllerLogin();
    private static final ControllerAdmin cA=new ControllerAdmin();


    @AfterEach
    void logoutDb() throws CsvValidationException, SQLException, IOException {
        cA.logout("file");
    }

    @Test
    void testLoginAdmin() throws CsvValidationException, SQLException, IOException {
        vis.setTypeOfDb("file");
        String emailA=RBUTENTE.getString("emailA");
        String passA=RBUTENTE.getString("passA");
        assertNotNull(cL.login(emailA,passA));
    }
    @Test
    void testLoginUser() throws CsvValidationException, SQLException, IOException {
        vis.setTypeOfDb("file");
        String emailA=RBUTENTE.getString("emailU");
        String passA=RBUTENTE.getString("passU");
        assertNotNull(cL.login(emailA,passA));
    }
    @Test
    void testLoginScrittore() throws CsvValidationException, SQLException, IOException {
        vis.setTypeOfDb("file");
        String emailA=RBUTENTE.getString("emailS");
        String passA=RBUTENTE.getString("passS");
        assertNotNull(cL.login(emailA,passA));
    }
    @Test
    void testLoginEditore() throws CsvValidationException, SQLException, IOException {
        vis.setTypeOfDb("file");
        String emailA=RBUTENTE.getString("emailE");
        String passA=RBUTENTE.getString("passE");
        assertNotNull(cL.login(emailA,passA));
    }

}
