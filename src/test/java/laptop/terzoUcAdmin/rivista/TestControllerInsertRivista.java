package laptop.terzoUcAdmin.rivista;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerGestione;
import laptop.controller.ControllerSystemState;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerInsertRivista {
     private static final ResourceBundle RBOGGETTO= ResourceBundle.getBundle("configurations/objects");
     private static final ControllerSystemState vis= ControllerSystemState.getInstance();
     private static final ControllerGestione cG;

     static {
         try {
             cG = new ControllerGestione();
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
     }




     @ParameterizedTest
     @ValueSource(strings = {"file","db"})
     void testInsertRivista(String strings) throws CsvValidationException, SQLException, IOException {
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
}
