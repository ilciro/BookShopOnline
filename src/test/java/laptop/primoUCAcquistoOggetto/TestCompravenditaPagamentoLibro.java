package laptop.primoUCAcquistoOggetto;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.*;
import laptop.exception.IdException;
import laptop.model.raccolta.Libro;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class TestCompravenditaPagamentoLibro {

     private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");
     private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ControllerCompravendita cC;
     private static final ControllerAcquista cA=new ControllerAcquista();
     private static final ControllerPagamentoCash cPCash;
     private static final ControllerDownload cD;
     private static final ControllerScegliNegozio cSN=new ControllerScegliNegozio();
     private static final ControllerPagamentoCC cPCC;
     private static final ControllerAnnullaPagamento cAnnP;

    static {
        try {
            cC = new ControllerCompravendita();
            cPCash=new ControllerPagamentoCash();
            cD=new ControllerDownload();
            cPCC=new ControllerPagamentoCC();
            cAnnP=new ControllerAnnullaPagamento();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10,11,12})
    void testAcquistoLibroCashDownloadDB(int ints) throws CsvValidationException, IOException, IdException, SQLException, DocumentException, URISyntaxException {
         //acquisto libro con id 6 con download
         vis.setTypeAsBook();
         vis.setTypeOfDb("db");
         vis.setMetodoP("cash");
         vis.setId(ints);
        //compravendita
         cC.getLista(vis.getType());
         //acquista
         cA.getNomeCostoDisp();
         vis.setSpesaT(cA.getPrezzo("3"));
         //cash
         cPCash.controlla(RBUTENTE.getString("nome"),RBUTENTE.getString("cognome"),RBUTENTE.getString("via"),RBUTENTE.getString("com"));
         //download
         cD.scarica(vis.getType());
         assertNotEquals(0,vis.getId());

     }

     @Test
    void testAcquistoLibroCashNegozioFile() throws CsvValidationException, IOException, IdException, SQLException {
        //acquisto libro con id 6 con download
        vis.setTypeAsBook();
        vis.setTypeOfDb("file");
        vis.setMetodoP("cash");
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idL")));
        //compravendita
        cC.getLista(vis.getType());
        //acquista
        cA.getNomeCostoDisp();
        vis.setSpesaT(cA.getPrezzo("3"));
        //cash
        cPCash.controlla(RBUTENTE.getString("nome"),RBUTENTE.getString("cognome"),RBUTENTE.getString("via"),RBUTENTE.getString("com"));
        //negozio

        assertTrue(cSN.getNegozi().get(2).getIsOpen());

    }
     @ParameterizedTest
     @ValueSource(strings = {"file","db"})
    void testAcquistoLibroCreditoDB(String strings) throws CsvValidationException, IOException, IdException, SQLException, DocumentException, URISyntaxException {
        //acquisto libro con id 6 con download
        vis.setTypeAsBook();
        vis.setTypeOfDb(strings);
        vis.setMetodoP("cCredito");
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idL")));
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
    @Test
    void testPagamentoLibroAnnullatoDb() throws CsvValidationException, IOException, IdException, SQLException {
        //acquisto libro con id 6 con download
        vis.setTypeAsBook();
        vis.setTypeOfDb("db");
        vis.setMetodoP("cash");
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idL")));
        //compravendita
        cC.getLista(vis.getType());
        //acquista
        cA.getNomeCostoDisp();
        vis.setSpesaT(cA.getPrezzo("3"));
        //cash
        cPCash.controlla(RBUTENTE.getString("nome"),RBUTENTE.getString("cognome"),RBUTENTE.getString("via"),RBUTENTE.getString("com"));
        //annulla Pagamento

        String[] arr=cAnnP.getFattura().split(", ");
        int numero=0;
        for(String s1:arr)
        {
            if(s1.contains("numero"))
            {
                String[] bb = s1.split("=");
                numero=Integer.parseInt(bb[1]);
            }
        }

        String[] arr1=cAnnP.getPagamento().split(", ");
        int numero1=0;
        for(String s2:arr1)
        {
            if(s2.contains("[id"))
            {
                String[] bb = s2.split("=");
                numero1=Integer.parseInt(bb[1]);
            }
        }
        cAnnP.cancellaFattura(String.valueOf(numero));
        cAnnP.cancellaPagamento(String.valueOf(numero1));
        assertNotEquals(0,vis.getId());

    }

    @Test
    void testPagamentoLibroAnnullatoFile() throws CsvValidationException, IOException, IdException, SQLException {
        //acquisto libro con id 6 con download
        vis.setTypeAsBook();
        vis.setTypeOfDb("file");
        vis.setMetodoP("cash");
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idL")));
        //compravendita
        cC.getLista(vis.getType());
        //acquista
        cA.getNomeCostoDisp();
        vis.setSpesaT(cA.getPrezzo("3"));
        //cash
        cPCash.controlla(RBUTENTE.getString("nome"),RBUTENTE.getString("cognome"),RBUTENTE.getString("via"),RBUTENTE.getString("com"));
        //annulla Pagamento

        String[] arr=cAnnP.getFattura().split(", ");
        int numero=0;
        for(String s1:arr)
        {
            if(s1.contains("numero"))
            {
                String[] bb = s1.split("=");
                numero=Integer.parseInt(bb[1]);
            }
        }
        cAnnP.cancellaFattura(String.valueOf(numero));


        String[] arr1=cAnnP.getPagamento().split(",");
        int numero1=0;
        for(String s2:arr1)
        {
            if(s2.contains("[id"))
            {
                String[] bb = s2.split("=");
                numero1=Integer.parseInt(bb[1]);
            }
        }
        cAnnP.cancellaPagamento(String.valueOf(numero1));
        assertNotEquals(0,vis.getId());
    }


    @ParameterizedTest
    @ValueSource(strings = {"ADOLESCENTI_RAGAZZI","ARTE","CINEMA_FOTOGRAFIA","BIOGRAFIE","DIARI_MEMORIE","CALENDARI_AGENDE","DIRITTO","DIZINARI_OPERE","ECONOMIA","FAMIGLIA","FANTASCIENZA_FANTASY","FUMETTI_MANGA","GIALLI_THRILLER","COMPUTER_GIOCHI","HUMOR","INFORMATICA","WEB_DIGITAL_MEDIA","LETTERATURA_NARRATIVA","LIBRI_BAMBINI","LIBRI_SCOLASTICI","LIBRI_UNIVERSITARI","RICETTARI_GENERALI","LINGUISTICA_SCRITTURA","POLITICA","RELIGIONE","ROMANZI_ROSA","SCIENZE","TECNOLOGIA_MEDICINA"})
    void testSetCategorie(String strings)
    {
        Libro l=new Libro();
        l.setCategoria(strings);
        assertEquals(strings,l.getCategoria());
    }



}
