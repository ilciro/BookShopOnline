package laptop.primoUCAcquistoOggetto;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.*;
import laptop.exception.IdException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class TestCompravenditaPagamentoRivista {
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ControllerCompravendita cC;
    private static final ControllerAcquista cA=new ControllerAcquista();
    private static final ControllerPagamentoCash cPCash;
    private static final ControllerDownload cD;
    private static final ControllerScegliNegozio cSN=new ControllerScegliNegozio();
    private static final ControllerPagamentoCC cPCC;
    static {
        try {
            cC = new ControllerCompravendita();
            cPCash=new ControllerPagamentoCash();
            cD=new ControllerDownload();
            cPCC=new ControllerPagamentoCC();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void testAcquistoGiornaleCashDownloadDB() throws CsvValidationException, IOException, IdException, SQLException, DocumentException, URISyntaxException {
        //acquisto rivista con id 5 con download
        vis.setTypeAsMagazine();
        vis.setTypeOfDb("db");
        vis.setMetodoP("cash");
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idR")));
        //compravendita
        cC.getLista(vis.getType());
        //acquista
        cA.getNomeCostoDisp();
        vis.setSpesaT(cA.getPrezzo("5"));
        //cash
        cPCash.controlla(RBUTENTE.getString("nome1"),RBUTENTE.getString("cognome1"),RBUTENTE.getString("via1"),RBUTENTE.getString("com1"));
        //download
        cD.scarica(vis.getType());
        assertNotEquals(0,vis.getId());

    }

    @Test
    void testAcquistoGiornaleCashNegozioFile() throws CsvValidationException, IOException, IdException, SQLException {
        //acquisto giornale con id 1 con download
        vis.setTypeAsMagazine();
        vis.setTypeOfDb("file");
        vis.setMetodoP("cash");
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idR")));
        //compravendita
        cC.getLista(vis.getType());
        //acquista
        cA.getNomeCostoDisp();
        vis.setSpesaT(cA.getPrezzo("5"));
        //cash
        cPCash.controlla(RBUTENTE.getString("nome"),RBUTENTE.getString("cognome"),RBUTENTE.getString("via"),RBUTENTE.getString("com"));
        //negozio

        assertFalse(cSN.getNegozi().get(3).getIsValid());

    }
    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testAcquistoRivistaCreditoDB(String strings) throws CsvValidationException, IOException, IdException, SQLException, DocumentException, URISyntaxException {
        //acquisto libro con id 6 con download
        vis.setTypeAsMagazine();
        vis.setTypeOfDb(strings);
        vis.setMetodoP("cCredito");
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idR")));
        //compravendita
        cC.getLista(vis.getType());
        //acquista
        cA.getNomeCostoDisp();
        vis.setSpesaT(cA.getPrezzo("3"));
        //ccredito
        cPCC.pagamentoCC(RBUTENTE.getString("nome1"));
        //download
        cD.scarica(vis.getType());
        assertNotEquals(0,vis.getId());

    }
}
