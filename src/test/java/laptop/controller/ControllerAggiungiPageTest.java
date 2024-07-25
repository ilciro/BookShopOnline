package laptop.controller;

import com.opencsv.exceptions.CsvValidationException;
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
    void checkDataG() throws SQLException, IOException, CsvValidationException {
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
    void checkDataR() throws SQLException, IOException, CsvValidationException {
        String[] info =new String[5];
        info[0]=rbOggetto.getString("titoloR");
        info[1]=rbOggetto.getString("tipoR");
        info[2]=rbOggetto.getString("autoreR");
        info[3]=rbOggetto.getString("lingua");
        info[4]= rbOggetto.getString("editoreR");
        LocalDate data=LocalDate.of(2024,3,6);

        assertTrue(cAP.checkDataR(info,data,Integer.parseInt(rbOggetto.getString("dispR")),Float.parseFloat(rbOggetto.getString("prezzoR")),Integer.parseInt(rbOggetto.getString("copieR")),rbOggetto.getString("descR")));

        //titolo,tipologia,autore,ligua,editore
    }

    @Test
    void checkData() throws SQLException, CsvValidationException, IOException {
        String[] info=new String[7];
        String [] infoGen=new String[6];

        info[0]=rbOggetto.getString("titoloL");
        infoGen[0]=rbOggetto.getString("pagineL");
        infoGen[1]=rbOggetto.getString("isbn");
        info[4]=rbOggetto.getString("editoreL");
        info[2]=rbOggetto.getString("autoreL");
        info[3]=rbOggetto.getString("lingua");
        info[5]=rbOggetto.getString("categoriaL");
        infoGen[3]=rbOggetto.getString("dispL");
        infoGen[4]=rbOggetto.getString("prezzoL");
        infoGen[5]=rbOggetto.getString("copieL");

        assertTrue(cAP.checkData(info,rbOggetto.getString("recensioneL"),rbOggetto.getString("descrizioneL"),LocalDate.of(2024,5,5),infoGen));
    }
}