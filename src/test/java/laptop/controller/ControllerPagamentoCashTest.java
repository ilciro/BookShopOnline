package laptop.controller;

import laptop.exception.IdException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerPagamentoCashTest {
    private final ControllerPagamentoCash cPCash=new ControllerPagamentoCash();
    private final ControllerSystemState vis=ControllerSystemState.getInstance();

    ControllerPagamentoCashTest() throws IOException {
    }

    @Test
    void controllaL() throws SQLException, IdException {
        vis.setTypeAsBook();
        vis.setId(1);
        vis.setSpesaT(7.99f);
        cPCash.controlla("franco","rossi","via gardenie 8","consegna dopo le ore 12.00");
        assertNotEquals(0,vis.getSpesaT());

    }
    @Test
    void controllaG() throws SQLException, IdException {
        vis.setTypeAsDaily();
        vis.setId(6);
        vis.setSpesaT(4f);
        cPCash.controlla("franco","rossi","via gardenie 8","consegna dopo le ore 12.00");
        assertNotEquals(0,vis.getSpesaT());

    }
    @Test
    void controllaR() throws SQLException, IdException {
        vis.setTypeAsMagazine();
        vis.setId(1);
        vis.setSpesaT(4f);
        cPCash.controlla("luigi","neri","via fiordalisi","");
        assertNotEquals(0,vis.getSpesaT());

    }
}