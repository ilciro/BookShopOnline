package laptop.terzoUcAdmin.utenti;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerGestioneUtente;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerModifUser {

    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ControllerGestioneUtente cGU=new ControllerGestioneUtente();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");

    @Test
    void testModifUserFile() throws CsvValidationException, IOException, IdException {
        User.getInstance().setEmail(RBUTENTE.getString("email"));
        vis.setTypeOfDb("file");
        String ruolo=RBUTENTE.getString("ruoloMod");
        String nome=RBUTENTE.getString("nomeMod");
        String cognome=RBUTENTE.getString("cognomeMod");
        String email=RBUTENTE.getString("emailMod");
        String desc=RBUTENTE.getString("descMod");
        String pwd=RBUTENTE.getString("pwdMod");
        LocalDate date= LocalDate.parse(RBUTENTE.getString("dataMod"));
        assertTrue(cGU.modifica(ruolo,nome,cognome,email,pwd,desc,date));
    }

    @Test
    void testModifUserDb() throws CsvValidationException, IOException, IdException {
        User.getInstance().setEmail(RBUTENTE.getString("email"));
        vis.setTypeOfDb("db");
        String ruolo=RBUTENTE.getString("ruoloMod");
        String nome=RBUTENTE.getString("nomeMod");
        String cognome=RBUTENTE.getString("cognomeMod");
        String email=RBUTENTE.getString("emailMod");
        String desc=RBUTENTE.getString("descMod");
        String pwd=RBUTENTE.getString("pwdMod");
        LocalDate date= LocalDate.parse(RBUTENTE.getString("dataMod"));
        assertTrue(cGU.modifica(ruolo,nome,cognome,email,pwd,desc,date));
    }
}
