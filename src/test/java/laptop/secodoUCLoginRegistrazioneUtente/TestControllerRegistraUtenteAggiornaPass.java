package laptop.secodoUCLoginRegistrazioneUtente;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerAggiornaPassword;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerRegistraUtenteAggiornaPass {
     private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ControllerAggiornaPassword cAP=new ControllerAggiornaPassword();
     private static final User u= User.getInstance();

     @ParameterizedTest
     @ValueSource(strings = {"file","db"})
     void testAggiornaPassword(String strings) throws CsvValidationException,  IOException, IdException {
        vis.setTypeOfDb(strings);
        u.setEmail(RBUTENTE.getString("email"));
        u.setPassword(RBUTENTE.getString("pwd"));


        assertTrue(cAP.aggiorna(RBUTENTE.getString("pwdMod")));


     }
}
