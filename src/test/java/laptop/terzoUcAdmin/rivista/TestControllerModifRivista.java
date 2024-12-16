package laptop.terzoUcAdmin.rivista;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerGestione;
import laptop.controller.ControllerSystemState;
import laptop.database.RivistaDao;
import laptop.database.csvoggetto.CsvOggettoDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Rivista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerModifRivista {

     private static final ResourceBundle RBOGGETTO= ResourceBundle.getBundle("configurations/objects");
     private static final ControllerSystemState vis= ControllerSystemState.getInstance();
     private static final ControllerGestione cG;
    private static final Rivista r=new Rivista();
    private static final RivistaDao rD=new RivistaDao();
    private static final CsvOggettoDao csv;


     static {
         try {
             cG = new ControllerGestione();
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
    void testModifRivistaDb() throws  IOException,CsvValidationException, SQLException, IdException {

        vis.setTypeAsMagazine();
        vis.setTypeOfDb("db");
        r.setTitolo(RBOGGETTO.getString("titoloRI"));
        int id=rD.getRivistaIdTitoloAutore(r).get(0).getId();

        vis.setId(id);

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

    @Test
    void testModifRivistaF() throws  IOException,CsvValidationException, SQLException, IdException {

        vis.setTypeAsMagazine();
        vis.setTypeOfDb("file");
        r.setTitolo(RBOGGETTO.getString("titoloRI"));
        int id=csv.getRivistaByIdTitoloEditore(r).get(0).getId();

        vis.setId(id);

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
}
