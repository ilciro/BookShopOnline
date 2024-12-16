package laptop.terzoUcAdmin.giornale;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerGestione;
import laptop.controller.ControllerSystemState;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerInsertGiornale {
    private static final ResourceBundle RBOGGETTO= ResourceBundle.getBundle("configurations/objects");
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ControllerGestione cG;

    static {
        try {
            cG=new ControllerGestione();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testInsertGiornale(String strings) throws CsvValidationException, SQLException, IOException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb(strings);
        String []infoG=new String[13];
        infoG[0]=RBOGGETTO.getString("titoloGI");
        infoG[2]=RBOGGETTO.getString("editoreGI");
        infoG[4]=RBOGGETTO.getString("linguaGI");
        infoG[5]=RBOGGETTO.getString("categoriaG");
        infoG[7]=RBOGGETTO.getString("dataPubbG");
        infoG[10]=RBOGGETTO.getString("nrCopieG");
        infoG[11]=RBOGGETTO.getString("dispG");
        infoG[12]=RBOGGETTO.getString("prezzoGI");

        assertTrue(cG.inserisci(infoG));

    }
}
