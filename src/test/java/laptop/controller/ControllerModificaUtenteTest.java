package laptop.controller;

import laptop.model.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class ControllerModificaUtenteTest {
    private final ControllerModificaUtente cMU=new ControllerModificaUtente();
    private final ResourceBundle rbOggetto=ResourceBundle.getBundle("configurations/infoObjects");

    @Test
    void prendi() throws SQLException {
        User.getInstance().setId(7);
        assertNotNull(cMU.prendi());
    }

    @Test
    void aggiornaTot() throws SQLException {
        User.getInstance().setId(7);
        assertTrue(cMU.aggiornaTot(rbOggetto.getString("nomeM"),rbOggetto.getString("cognomeM"),rbOggetto.getString("emailM"),rbOggetto.getString("pwdM"), rbOggetto.getString("descrM"), LocalDate.of(1975,4,28),rbOggetto.getString("ruoloM")));

    }
}