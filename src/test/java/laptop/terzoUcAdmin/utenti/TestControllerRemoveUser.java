package laptop.terzoUcAdmin.utenti;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.ControllerUtenti;
import laptop.database.UsersDao;
import laptop.model.TempUser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TestControllerRemoveUser {

     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ControllerUtenti cU=new ControllerUtenti();
     private static final ResourceBundle RBUTNTE=ResourceBundle.getBundle("configurations/users");

     @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testGetList(String strings) throws CsvValidationException, SQLException, IOException {
         vis.setTypeOfDb(strings);
         assertNotNull(cU.getList());
     }

     @ParameterizedTest
     @ValueSource(strings = {"file","db"})
    void deleteUser(String strings) throws CsvValidationException, SQLException, IOException {
         //tempUser
         vis.setTypeOfDb(strings);
         TempUser tu=new TempUser();
         tu.setEmailT(RBUTNTE.getString("emailMod"));


         int id=UsersDao.getTempUserSingolo(tu).getId();

         vis.setId(id);

         assertTrue(cU.elimina(tu.getEmailT()));
     }
}
