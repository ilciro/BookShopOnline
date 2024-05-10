package laptop.controller;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerVisualizzaTest {
    private final ControllerVisualizza cV=new ControllerVisualizza();
    private final ControllerSystemState vis=ControllerSystemState.getInstance();

    ControllerVisualizzaTest() throws IOException {
    }

    @Test
    void getDataL() throws SQLException {
        vis.setTypeAsBook();
        assertNotNull(cV.getDataL(1));
    }

    @Test
    void getDataG() throws SQLException {
        vis.setTypeAsDaily();
        assertNotNull(cV.getDataG(1));
    }

    @Test
    void getDataR() throws SQLException {
        vis.setTypeAsMagazine();
        assertNotNull(cV.getDataR(1));
    }
}