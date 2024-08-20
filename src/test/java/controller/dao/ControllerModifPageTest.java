package controller.dao;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerModifPage;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class ControllerModifPageTest {
    private final ControllerModifPage cMP=new ControllerModifPage();
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final Libro l=new Libro();
    private final Giornale g=new Giornale();
    private final Rivista r=new Rivista();
    private static final ResourceBundle rbOggetto=ResourceBundle.getBundle("configurations/books");




    ControllerModifPageTest() throws IOException {
    }

    @Test
    void getLibriById() throws SQLException, CsvValidationException, IOException, IdException {
        l.setId(19);
        assertNotNull(cMP.getLibriById(l.getId()));
    }

    @Test
    void getGiornaliById() throws SQLException, CsvValidationException, IOException, IdException {
        g.setId(5);
        assertNotNull(cMP.getGiornaliById(g.getId()));
    }

    @Test
    void checkDataG() throws SQLException, CsvValidationException, IOException, IdException {
        //funziona
        vis.setId(5);
        String []info=new String[8];

        info[0]="titolo giornale aggiornato";
        info[1]="QUOTIDIANO";
        info[2]="editore modificato";
        info[3]="italiano";
        LocalDate date= LocalDate.of(2024,3,5);
        info[4]= String.valueOf(date);
        info[5]= String.valueOf(0);
        info[6]= String.valueOf(2.65f);
        info[7]= String.valueOf(500);


        cMP.checkDataG(info);
        assertNotNull(date);
    }

    @Test
    void getRivistaById() throws SQLException, CsvValidationException, IOException, IdException {
        r.setId(4);
        assertNotNull(cMP.getRivistaById(r.getId()));
    }

    @Test
    void checkDataR() throws SQLException, CsvValidationException, IOException, IdException {

        //tipologia no , ma aggiorna
        String []info=new String[10];


       vis.setId(4);
        info[0]="titolo rivista aggiornato";
        info[1]="BIMENSILE";
        info[2]="autore modificato";
        info[3]="italiano";
        info[4]= "editore modificato";
        info[5]="aggiorno rivista";
        LocalDate data=LocalDate.of(2024,3,6);
        info[6]= String.valueOf(data);
        info[7]= String.valueOf(1);
        info[8]= String.valueOf(4.50f);
        info[9]= String.valueOf(50);
        cMP.checkDataR(info);



    }

    @Test
    void checkDataL() throws CsvValidationException, IOException, IdException {
        String [] info=new String[13];

        vis.setId(19);

        info[0]=rbOggetto.getString("titoloMod");
        info[1]=rbOggetto.getString("numPagMod");
        info[2]=rbOggetto.getString("isbnMod");
        info[3]=rbOggetto.getString("editoreMod");
        info[4]=rbOggetto.getString("autoreMod");
        info[5]=rbOggetto.getString("linguaMod");
        info[6]=rbOggetto.getString("categoriaMod");
        info[7]=String.valueOf(LocalDate.of(2024,2,6));
        info[8]=rbOggetto.getString("recensioneMod");
        info[9]=rbOggetto.getString("nrCopieMod");
        info[10]=rbOggetto.getString("descrizioneMod");
        info[11]=rbOggetto.getString("dispMod");
        info[12]=rbOggetto.getString("prezzoMod");


        cMP.checkDataL(info);


    }
}