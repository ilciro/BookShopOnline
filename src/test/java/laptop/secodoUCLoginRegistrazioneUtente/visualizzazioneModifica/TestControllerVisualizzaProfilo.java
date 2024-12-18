package laptop.secodoUCLoginRegistrazioneUtente.visualizzazioneModifica;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.ControllerVisualizzaProfilo;
import laptop.exception.IdException;
import laptop.model.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerVisualizzaProfilo {
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ControllerVisualizzaProfilo cVP=new ControllerVisualizzaProfilo();
     private static final User u= User.getInstance();
     private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");

     @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testModificaProfiloDaLogggato(String strings) throws CsvValidationException, IOException, IdException {
         vis.setTypeOfDb(strings);
         u.setEmail(RBUTENTE.getString("emailE"));
         String ruolo=RBUTENTE.getString("ruoloModL");
         String nome=RBUTENTE.getString("nomeModL");
         String cognome=RBUTENTE.getString("cognomeModL");
         String email=RBUTENTE.getString("emailModL");
         String pwd=RBUTENTE.getString("pwdModL");
         String desc=RBUTENTE.getString("descModL");
         LocalDate date= LocalDate.parse(RBUTENTE.getString("dataModL"));
               assertTrue(cVP.modifica(ruolo,nome,cognome,email,pwd,desc,date));
     }
}
