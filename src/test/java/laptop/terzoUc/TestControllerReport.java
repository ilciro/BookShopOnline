package laptop.terzoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerReport;
import laptop.controller.ControllerSystemState;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestControllerReport {
     private static final ControllerReport cRepo=new ControllerReport();
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();

     @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testReportTotale(String strings) throws CsvValidationException, SQLException, IOException {
         vis.setTypeOfDb(strings);
         assertNotNull(cRepo.reportTotale());
     }
}
