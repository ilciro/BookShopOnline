package laptop.controller;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class ControllerAggiungiUtenteTest {
    private final ControllerAggiungiUtente cAU=new ControllerAggiungiUtente();
    private final ResourceBundle rbOggetto=ResourceBundle.getBundle("configurations/infoObjects");

    @Test
    void checkData() throws SQLException, ParseException {
        assertTrue(cAU.checkData(rbOggetto.getString("nome"),rbOggetto.getString("cognome"),rbOggetto.getString("email"),rbOggetto.getString("pass"), rbOggetto.getString("data")));
    }
}