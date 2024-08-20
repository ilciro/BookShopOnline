package controller.dao;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerCompravendita;
import laptop.controller.ControllerScelta;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestControllerScelta {
    private final ControllerScelta cs=new ControllerScelta();
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final ControllerCompravendita ccv=new ControllerCompravendita();

    public TestControllerScelta() throws IOException {
    }

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testGetTypeDb(String strings)
    {
        cs.getTypeDb(strings);
        assertEquals(strings,vis.getTypeOfDb());
    }

    @Test
    void testGeneraReportLibri() throws CsvValidationException, IOException, IdException {
            vis.setTypeAsBook();
            vis.setTypeOfDb("file");
            ccv.getLista("libro");
    }
    @Test
    void testGeneraReportRivista() throws CsvValidationException, IOException, IdException {
        vis.setTypeAsMagazine();
        vis.setTypeOfDb("file");
        ccv.getLista("rivista");
    }
    @Test
    void testGeneraReportGiornali() throws CsvValidationException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb("file");
        ccv.getLista("giornale");
    }

}
