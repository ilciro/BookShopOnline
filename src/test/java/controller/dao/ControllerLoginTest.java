package controller.dao;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerLogin;
import laptop.exception.IdException;
import laptop.model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class ControllerLoginTest {
    private final ControllerLogin cL=new ControllerLogin();
    private final ResourceBundle rbOggetto=ResourceBundle.getBundle("configurations/infoObjects");

    @Test
    void controlla() throws SQLException, CsvValidationException, IOException, IdException {
        User.getInstance().setEmail(rbOggetto.getString("email1"));
        User.getInstance().setPassword(rbOggetto.getString("pwd1"));
        assertTrue(cL.controlla(User.getInstance().getEmail(), User.getInstance().getPassword()));
    }

    @Test
    void getRuoloTempUSer() throws SQLException, CsvValidationException, IOException, IdException {
        User.getInstance().setEmail(rbOggetto.getString("email1"));
        assertEquals("A",cL.getRuoloTempUSer(User.getInstance().getEmail()));
    }
}