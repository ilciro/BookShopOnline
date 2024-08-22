package controller;

import laptop.controller.ControllerSystemState;
import laptop.controller.ControllerVisualizzaProfilo;
import laptop.model.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class ControllerVisualizzaProfiloTest {
    private final ControllerVisualizzaProfilo cvP=new ControllerVisualizzaProfilo();
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final ResourceBundle rbOggetto=ResourceBundle.getBundle("configurations/infoObjects");

    @Test
    void cancellaUtente() throws SQLException {
        User.getInstance().setEmail(rbOggetto.getString("email"));
        assertTrue(cvP.cancellaUtente());
    }
}