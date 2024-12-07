package laptop.terzoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerGestioneUtente;
import laptop.controller.ControllerSystemState;
import laptop.database.UsersDao;
import laptop.exception.IdException;
import laptop.model.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerUtenteInserisciModifica {
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ControllerGestioneUtente cGU=new ControllerGestioneUtente();
     private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
     private static final User u=User.getInstance();
     @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testInsertUser(String strings) throws CsvValidationException, SQLException, IOException, IdException {
         vis.setTypeOfDb(strings);
         assertTrue(cGU.inserisciUtente(RBUTENTE.getString("ruolo"),RBUTENTE.getString("nome"),RBUTENTE.getString("cognome"),RBUTENTE.getString("email"),RBUTENTE.getString("pwd"),RBUTENTE.getString("desc"), LocalDate.parse(RBUTENTE.getString("data"))));
     }

     @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testModifUser(String strings) throws SQLException, CsvValidationException, IOException, IdException {
         vis.setTypeOfDb(strings);
         u.setEmail(RBUTENTE.getString("email"));

         vis.setId(UsersDao.pickData(u).getId());
         //aggiorno su id
         assertTrue(cGU.modifica(RBUTENTE.getString("ruoloMod"),RBUTENTE.getString("nomeMod"),RBUTENTE.getString("cognomeMod"),RBUTENTE.getString("emailMod"),RBUTENTE.getString("pwdMod"),RBUTENTE.getString("descMod"), LocalDate.parse(RBUTENTE.getString("dataMod"))));
     }

}


