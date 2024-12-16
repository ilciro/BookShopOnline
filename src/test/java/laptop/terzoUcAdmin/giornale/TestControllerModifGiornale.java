package laptop.terzoUcAdmin.giornale;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerGestione;
import laptop.controller.ControllerSystemState;
import laptop.database.GiornaleDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerModifGiornale {
    private static final ResourceBundle RBOGGETTO= ResourceBundle.getBundle("configurations/objects");
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ControllerGestione cG;
    private static final Giornale g=new Giornale();
    private static final GiornaleDao gD=new GiornaleDao();
    private static final CsvOggettoDao csv;

    static {
        try {
            csv = new CsvOggettoDao();

            cG=new ControllerGestione();
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
    void testTModifGF() throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb("file");
        g.setTitolo(RBOGGETTO.getString("titoloGI"));
        vis.setTipoModifica("modifica");
        int id=csv.getGiornaleByIdTitoloEditore(g).get(0).getId();
        vis.setId(id);


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

    @Test
    void testTModifGD() throws CsvValidationException, SQLException, IOException, IdException {
        vis.setTypeAsDaily();
        vis.setTypeOfDb("db");
        g.setTitolo(RBOGGETTO.getString("titoloGI"));
        vis.setTipoModifica("modifica");
        int id=gD.getGiornaleIdTitoloAutore(g).get(0).getId();
        vis.setId(id);

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


 }
