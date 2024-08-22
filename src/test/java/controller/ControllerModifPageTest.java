package controller;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerModifPage;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ControllerModifPageTest {
    private final ControllerModifPage cMP=new ControllerModifPage();
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final Libro l=new Libro();
    private final Giornale g=new Giornale();
    private final Rivista r=new Rivista();



    ControllerModifPageTest() throws IOException {
    }

    @BeforeEach
    void init()
    {
        vis.setTypeOfDb("db");
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
        info[2]="italiano";
        info[3]="editore modificato";
        LocalDate date= LocalDate.of(2024,3,5);
        info[4]= String.valueOf(date);
        info[5]= String.valueOf(500);
        info[6]= String.valueOf(1);
        info[7]= String.valueOf(2.65f);
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

        vis.setId(4);
        //tipologia no , ma aggiorna
        String []info=new String[10];

        info[0]="titolo rivista aggiornato";
        info[1]="BIMENSILE";
        info[2]="autore modificato";
        info[3]="italiano";
        info[4]="editore modificato";
        info[5]="aggoirno rivista";
        LocalDate date=LocalDate.of(2024,6,6);
        info[6]= String.valueOf(date);
        info[7]= String.valueOf(1);
        info[8]= String.valueOf(4.50f);
        info[9]= String.valueOf(50);
        cMP.checkDataR(info);
        assertNotNull(date);


    }

    @Test
    void checkDataL() throws CsvValidationException, IOException, IdException {
        String [] info=new String[13];


        vis.setId(19);
        info[0]="titolo modificato";
        info[1]="884263152";
        info[2]="editore modificato";
        info[3]="autore modificato";
        info[4]="italiano";
        info[5]="FANTASCIENZA_FANTASY";
        info[6]="163";
        info[7]="200";
        info[8]="1";
        info[9]="3.87f";
        LocalDate date=LocalDate.of(2024,2,4);
        info[10]= String.valueOf(date);
        String recensione=" provo a modificare un libro";
        String descrizione="sto modificando un libro";
        info[11]=recensione;
        info[12]=descrizione;

        cMP.checkDataL(info );


    }
}