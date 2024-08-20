package controller.dao;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerAggiungiPage;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class ControllerAggiungiPageTest {
    private final Giornale g=new Giornale();
    private static final ResourceBundle rbOggetto=ResourceBundle.getBundle("configurations/infoObjects");
    private final ControllerAggiungiPage cAP=new ControllerAggiungiPage();

    ControllerAggiungiPageTest() throws IOException {
    }

    @Test
    void checkDataG() throws SQLException, IOException, CsvValidationException, IdException {
        g.setTitolo(rbOggetto.getString("titoloG"));
        g.setTipologia(rbOggetto.getString("tipoG"));
        g.setLingua(rbOggetto.getString("lingua"));
        g.setEditore(rbOggetto.getString("editoreG"));
        g.setCopieRimanenti(Integer.parseInt(rbOggetto.getString("copieG")));
        g.setDisponibilita(Integer.parseInt(rbOggetto.getString("dispG")));
        g.setPrezzo(Float.parseFloat(rbOggetto.getString("prezzoG")));
        g.setDataPubb(LocalDate.of(2024,1,5));
        assertTrue(cAP.checkDataG(g));
    }

    @Test
    void checkDataR() throws SQLException, IOException, CsvValidationException, IdException {
        String[] info =new String[10];
        info[0]=rbOggetto.getString("titoloR");
        info[1]=rbOggetto.getString("tipoR");
        info[2]=rbOggetto.getString("autoreR");
        info[3]=rbOggetto.getString("lingua");
        info[4]= rbOggetto.getString("editoreR");
        info[5]=rbOggetto.getString("descR");
        LocalDate data=LocalDate.of(2024,3,6);
        info[6]= String.valueOf(data);
        info[7]=rbOggetto.getString("dispR");
        info[8]=rbOggetto.getString("prezzoR");
        info[9]=rbOggetto.getString("copieR");


        assertTrue(cAP.checkDataR(info));

        //titolo,tipologia,autore,ligua,editore
    }

    @Test
    void checkData() throws SQLException, CsvValidationException, IOException, IdException {
        String[] info=new String[13];


        info[0]=rbOggetto.getString("titoloL");
        info[1]=rbOggetto.getString("pagineL");
        info[2]=rbOggetto.getString("isbn");
        info[3]=rbOggetto.getString("editoreL");
        info[4]=rbOggetto.getString("autoreL");
        info[5]=rbOggetto.getString("lingua");
        info[6]=rbOggetto.getString("categoriaL");
        info[7]=String.valueOf(LocalDate.of(2024,2,6));
        info[8]=rbOggetto.getString("recensioneL");
        info[9]=rbOggetto.getString("copieL");
        info[10]=rbOggetto.getString("descrizioneL");
        info[11]=rbOggetto.getString("dispL");
        info[12]=rbOggetto.getString("prezzoL");


        assertTrue(cAP.checkDataL(info));
    }
}