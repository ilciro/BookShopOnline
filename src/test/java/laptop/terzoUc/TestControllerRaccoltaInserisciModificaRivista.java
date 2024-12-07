package laptop.terzoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerGestione;
import laptop.controller.ControllerSystemState;

import laptop.database.RivistaDao;
import laptop.exception.IdException;

import laptop.model.raccolta.Rivista;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerRaccoltaInserisciModificaRivista {


    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ControllerGestione cG;

    static {
        try {
            cG = new ControllerGestione();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static final Rivista r=new Rivista();
    private static final RivistaDao rD=new RivistaDao();

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testInsertRivista(String strings) throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeAsMagazine();
        vis.setTypeOfDb(strings);
        String []infoR=new String[13];
        infoR[0]=RBOGGETTO.getString("titoloRI");
        infoR[2]=RBOGGETTO.getString("editoreRI");
        infoR[3]=RBOGGETTO.getString("autoreRI");
        infoR[4]=RBOGGETTO.getString("linguaRI");
        infoR[5]=RBOGGETTO.getString("categoriaR");
        infoR[6]=RBOGGETTO.getString("descrizioneR");
        infoR[7]=RBOGGETTO.getString("dataPubbR");
        infoR[10]=RBOGGETTO.getString("nrCopieR");
        infoR[11]=RBOGGETTO.getString("dispR");
        infoR[12]=RBOGGETTO.getString("prezzoRI");

        assertTrue(cG.inserisci(infoR));

    }

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testModifRivista(String strings) throws CsvValidationException, SQLException, IOException, IdException {

        vis.setTypeAsMagazine();
        vis.setTypeOfDb(strings);
        r.setTitolo(RBOGGETTO.getString("titoloRI"));
        int id=rD.getRivistaIdTitoloAutore(r).get(0).getId();
        r.setId(id);
        vis.setId(r.getId());

        String []infoR=new String[13];
        infoR[0]=RBOGGETTO.getString("titoloModR");
        infoR[2]=RBOGGETTO.getString("editoreModR");
        infoR[3]=RBOGGETTO.getString("autoreModR");
        infoR[4]=RBOGGETTO.getString("linguaModR");
        infoR[5]=RBOGGETTO.getString("categoriaModR");
        infoR[6]=RBOGGETTO.getString("descrizioneModR");
        infoR[7]=RBOGGETTO.getString("dataPubbModR");
        infoR[10]=RBOGGETTO.getString("nrCopieModR");
        infoR[11]=RBOGGETTO.getString("dispModR");
        infoR[12]=RBOGGETTO.getString("prezzoModR");

        assertTrue(cG.modifica(infoR));

    }

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testReturnListR(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeAsMagazine();
        vis.setTypeOfDb(strings);

        r.setTitolo(RBOGGETTO.getString("titoloRI"));
        int id=rD.getRivistaIdTitoloAutore(r).get(0).getId();
        r.setId(id);
        vis.setId(r.getId());
        assertNotEquals(0,cG.rivistaById().get(0).getId());
    }

}


