package controller.dao;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerCancellaUser;
import laptop.controller.ControllerSystemState;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerCancellaUserTest {
    private final ControllerCancellaUser cCU=new ControllerCancellaUser();
    private final ControllerSystemState vis= ControllerSystemState.getInstance();

    @Test
    void cancellaUser() throws SQLException, CsvValidationException, IOException {
        vis.setId(8);
        assertTrue(cCU.cancellaUser());
    }
}