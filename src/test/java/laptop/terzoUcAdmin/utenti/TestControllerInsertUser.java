package laptop.terzoUcAdmin.utenti;

import com.opencsv.exceptions.CsvValidationException;
import io.opentelemetry.sdk.resources.Resource;
import laptop.controller.ControllerGestioneUtente;
import laptop.controller.ControllerSystemState;
import laptop.controller.ControllerUtenti;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerInsertUser {

     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ControllerUtenti cU=new ControllerUtenti();
     private static final ControllerGestioneUtente cGU=new ControllerGestioneUtente();
     private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
     @ParameterizedTest
     @ValueSource(strings = {"file","db"})
     void testListaUtenti(String strings) throws CsvValidationException, SQLException, IOException {
         vis.setTypeOfDb(strings);
         assertNotNull(cU.getList());
     }

     @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testUtenteInsert(String strings) throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeOfDb(strings);
        String ruolo=RBUTENTE.getString("ruolo2");
        String nome=RBUTENTE.getString("nome2");
        String cognome=RBUTENTE.getString("cognome2");
        String email=RBUTENTE.getString("email2");
        String desc=RBUTENTE.getString("desc2");
        String pwd=RBUTENTE.getString("pwd2");
         LocalDate date= LocalDate.parse(RBUTENTE.getString("data2"));
        assertTrue(cGU.inserisciUtente(ruolo,nome,cognome,email,pwd,desc,date));
     }


}
