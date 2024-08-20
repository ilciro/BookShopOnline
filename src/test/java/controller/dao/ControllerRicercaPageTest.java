package controller.dao;

import laptop.controller.ControllerRicercaPage;
import laptop.controller.ControllerSystemState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerRicercaPageTest {
    private final ControllerRicercaPage cRP=new ControllerRicercaPage();
    ControllerSystemState vis=ControllerSystemState.getInstance();

    ControllerRicercaPageTest() throws IOException {
    }

    @ParameterizedTest
    @ValueSource(strings = {"Sulla verità","Zerocalcare"})
    void cercaPerTipoL(String strings) throws SQLException {
        vis.setTypeAsBook();
        assertNotNull(cRP.cercaPerTipo(strings));
    }
    @ParameterizedTest
    @ValueSource(strings = {"La gazzetta del profeta","La Republica"})
    void cercaPerTipoG(String strings) throws SQLException {
        vis.setTypeAsDaily();
        assertNotNull(cRP.cercaPerTipo(strings));
    }
    @ParameterizedTest
    @ValueSource(strings = {"Focus","Bao Publishing"})
    void cercaPerTipoR(String strings) throws SQLException {
        vis.setTypeAsMagazine();
        assertNotNull(cRP.cercaPerTipo(strings));
    }
}