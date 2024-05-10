package laptop.controller;

import laptop.model.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class ControllerLoginTest {
    private final ControllerLogin cL=new ControllerLogin();
    private final ResourceBundle rbOggetto=ResourceBundle.getBundle("configurations/infoObjects");

    @Test
    void controlla() throws SQLException {
        User.getInstance().setEmail(rbOggetto.getString("email1"));
        User.getInstance().setPassword(rbOggetto.getString("pwd1"));
        assertTrue(cL.controlla(User.getInstance().getEmail(), User.getInstance().getPassword()));
    }

    @Test
    void getRuoloTempUSer() throws SQLException {
        User.getInstance().setEmail(rbOggetto.getString("email1"));
        assertEquals("A",cL.getRuoloTempUSer(User.getInstance().getEmail()));
    }
}