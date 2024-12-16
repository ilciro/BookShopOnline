package laptop.terzoUcAdmin.rivista;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerRaccolta;
import laptop.controller.ControllerSystemState;
import laptop.database.RivistaDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Rivista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerRemoveRivista {
    private static final ControllerSystemState vis= ControllerSystemState.getInstance();
    private static final ControllerRaccolta cR;
    private static final RivistaDao rD=new RivistaDao();
    private static final Rivista r=new Rivista();
    private static final CsvOggettoDao csv;
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");


    static {
        try {
            cR = new ControllerRaccolta();
            csv=new CsvOggettoDao();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void init()
    {
        vis.setId(0);
    }


    @Test
    void testRemoveRivistaDB() throws CsvValidationException, IOException {
        vis.setTypeAsMagazine();
        vis.setTypeOfDb("db");
        r.setTitolo(RBOGGETTO.getString("titoloModR"));
        int id=rD.getRivistaIdTitoloAutore(r).get(0).getId();
        vis.setId(id);
        assertTrue(cR.elimina());


    }

    @Test
    void testRemoveRivistaF() throws CsvValidationException, IOException, IdException {
        vis.setTypeAsMagazine();
        vis.setTypeOfDb("file");
        r.setTitolo(RBOGGETTO.getString("titoloModR"));
        int id=csv.getRivistaByIdTitoloEditore(r).get(0).getId();
        vis.setId(id);
        assertTrue(cR.elimina());


    }
}
