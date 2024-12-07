package laptop.terzoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerGestione;
import laptop.controller.ControllerSystemState;
import laptop.database.GiornaleDao;

import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerRaccoltaInserisciModificaGiornale {

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


    private static final Giornale g=new Giornale();
    private static final GiornaleDao gD=new GiornaleDao();


    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testInsertGiornale(String strings) throws CsvValidationException, SQLException, IOException, IdException {
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

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testModifGiornale(String strings) throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb(strings);
        vis.setTipoModifica("modifica");



        g.setTitolo(RBOGGETTO.getString("titoloGI"));

        int id=gD.getGiornaleIdTitoloAutore(g).get(0).getId();
        g.setId(id);
        vis.setId(g.getId());


        String []infoG=new String[13];
        infoG[0]=RBOGGETTO.getString("titoloModG");
        infoG[2]=RBOGGETTO.getString("editoreModG");
        infoG[4]=RBOGGETTO.getString("linguaModG");
        infoG[5]=RBOGGETTO.getString("categoriaModG");
        infoG[7]=RBOGGETTO.getString("dataPubbModG");
        infoG[10]=RBOGGETTO.getString("nrCopieModG");
        infoG[11]=RBOGGETTO.getString("dispModG");
        infoG[12]=RBOGGETTO.getString("prezzoModGI");

        assertTrue(cG.modifica(infoG));

    }

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testReturnListG(String strings) throws CsvValidationException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb(strings);


        g.setTitolo(RBOGGETTO.getString("titoloGI"));
        int id=gD.getGiornaleIdTitoloAutore(g).get(0).getId();
        g.setId(id);
        vis.setId(g.getId());
        assertNotEquals(0,cG.giornaleById().get(0).getId());
    }


}


