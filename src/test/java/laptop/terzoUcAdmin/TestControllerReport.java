package laptop.terzoUcAdmin;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerReport {
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ControllerLogin cL=new ControllerLogin();
     private static final ControllerReport cR=new ControllerReport();
     private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/users");
     private static final ControllerAdmin cA=new ControllerAdmin();


     @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testReport(String strings) throws CsvValidationException, SQLException, IOException {
         //logged ad admin
         vis.setTypeOfDb(strings);
         cL.login(RBOGGETTO.getString("emailA"),RBOGGETTO.getString("passA"));
         assertNotNull(cR.reportTotale());
     }
     @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testReportLogout(String strings) throws CsvValidationException, SQLException, IOException {
        vis.setTypeOfDb(strings);
         cL.login(RBOGGETTO.getString("emailA"),RBOGGETTO.getString("passA"));

         assertTrue(cA.logout(vis.getTypeOfDb()));
     }
}
