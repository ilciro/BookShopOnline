package controller;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerAggiungiUtente;
import laptop.exception.IdException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class ControllerAggiungiUtenteTest {
    private final ControllerAggiungiUtente cAU=new ControllerAggiungiUtente();
    private final ResourceBundle rbOggetto=ResourceBundle.getBundle("configurations/infoObjects");

    @Test
    void checkData() throws SQLException, ParseException, CsvValidationException, IOException, IdException {
        assertTrue(cAU.checkData(rbOggetto.getString("nome"),rbOggetto.getString("cognome"),rbOggetto.getString("email"),rbOggetto.getString("pass"), rbOggetto.getString("data")));
    }
}