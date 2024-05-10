package laptop.controller;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerCancellaUserTest {
    private final ControllerCancellaUser cCU=new ControllerCancellaUser();
    private final ControllerSystemState vis= ControllerSystemState.getInstance();

    @Test
    void cancellaUser() throws SQLException {
        vis.setId(8);
        assertTrue(cCU.cancellaUser());
    }
}