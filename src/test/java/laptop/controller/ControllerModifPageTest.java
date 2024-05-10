package laptop.controller;

import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
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

    @Test
    void getLibriById() throws SQLException {
        l.setId(19);
        assertNotNull(cMP.getLibriById(l.getId()));
    }

    @Test
    void getGiornaliById() throws SQLException {
        g.setId(5);
        assertNotNull(cMP.getGiornaliById(g.getId()));
    }

    @Test
    void checkDataG() throws SQLException {
        //funziona
        vis.setId(5);
        String []info=new String[4];

        info[0]="titolo giornale aggiornato";
        info[1]="QUOTIDIANO";
        info[2]="editore modificato";
        info[3]="italiano";
        LocalDate date= LocalDate.of(2024,3,5);
        int disp=0;
        float prezzo=2.65f;
        int copie=500;

        cMP.checkDataG(info,date,disp,prezzo,copie);
        assertNotNull(date);
    }

    @Test
    void getRivistaById() throws SQLException {
        r.setId(4);
        assertNotNull(cMP.getRivistaById(r.getId()));
    }

    @Test
    void checkDataR() throws SQLException {

        //tipologia no , ma aggiorna
        String []info=new String[5];

        info[0]="titolo rivista aggiornato";
        info[1]="BIMENSILE";
        info[2]="autore modificato";
        info[3]="italiano";
        info[4]="editore modificato";
        String desc="aggoirno rivista";
        LocalDate date=LocalDate.of(2024,6,6);
        int disp=1;
        float prezzo=4.50f;
        int id=4;
        int copie=50;
        cMP.checkDataR(info,date,disp,prezzo,copie,id,desc);
        assertNotNull(date);


    }

    @Test
    void checkDataL() {
        String [] info=new String[6];
        String []infoCosti=new String[6];

        vis.setId(19);
        info[0]="titolo modificato";
        info[2]="autore modificato";
        info[3]="italiano";
        info[4]="editore modificato";
        info[5]="FANTASCIENZA_FANTASY";
        infoCosti[0]="163";
        infoCosti[1]="884263152";
        infoCosti[3]="1";
        infoCosti[4]="3.87f";
        infoCosti[5]="200";
        String recensione=" provo a modificare un libro";
        String descrizione="sto modificando un libro";
        LocalDate date=LocalDate.of(2024,2,4);

        cMP.checkDataL(info, recensione, descrizione, date, infoCosti);


    }
}