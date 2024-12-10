package laptop.secodoUCLoginRegistrazioneUtente;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerRegistraUtente;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerRegistraUtente {
     private static final ControllerRegistraUtente cRU=new ControllerRegistraUtente();
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");

     @ParameterizedTest
     @ValueSource(strings = {"file","db"})
     void testRegistraUtente(String strings) throws CsvValidationException, SQLException, IOException, IdException {
         vis.setTypeOfDb(strings);
         String nome=RBUTENTE.getString("nome");
         String cognome=RBUTENTE.getString("cognome");
         String email=RBUTENTE.getString("email");
         String pwd=RBUTENTE.getString("pwd");
         String desc=RBUTENTE.getString("desc");
         LocalDate date= LocalDate.parse(RBUTENTE.getString("data"));
         String ruolo=RBUTENTE.getString("ruolo");
         assertTrue(cRU.registra(nome,cognome,email,pwd,desc,date,ruolo));
     }
}
