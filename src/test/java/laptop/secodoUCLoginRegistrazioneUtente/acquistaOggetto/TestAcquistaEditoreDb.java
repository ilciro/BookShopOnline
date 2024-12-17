package laptop.secodoUCLoginRegistrazioneUtente.acquistaOggetto;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.*;
import laptop.database.UsersDao;
import laptop.exception.IdException;
import laptop.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestAcquistaEditoreDb {

     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ControllerLogin cL=new ControllerLogin();
     private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
     private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");
     private static final ResourceBundle RBCARTACREDITO=ResourceBundle.getBundle("configurations/cartaCredito");
     private static final ControllerCompravendita cC;
     private static final ControllerAcquista cA=new ControllerAcquista();
     private static final ControllerPagamentoCC cPCC;
     private static final ControllerDownload cD;
     private static final User u=User.getInstance();
     private static final ControllerAdmin cAdmin=new ControllerAdmin();

    static {
        try {
            cD = new ControllerDownload();
            cPCC = new ControllerPagamentoCC();
            cC = new ControllerCompravendita();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void userPresente() throws CsvValidationException, SQLException, IOException {
         vis.setTypeOfDb("db");
         String email=RBUTENTE.getString("emailE");
         String pass= RBUTENTE.getString("passE");
         cL.userPresente(email,pass);
    }
    @AfterEach
    void logout() throws CsvValidationException, SQLException, IOException {
        vis.setTypeOfDb("db");
        cAdmin.logout(vis.getTypeOfDb());
    }

    @Test
    void acquistoCC() throws CsvValidationException, SQLException, IOException, IdException, DocumentException, URISyntaxException {
        vis.setTypeOfDb("db");
        String email=RBUTENTE.getString("emailE");
        String pass= RBUTENTE.getString("passE");
        cL.login(email,pass);
        vis.setTypeAsBook();
        vis.setMetodoP("cCredito");
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idL")));
        //compravendita
        cC.getLista(vis.getType());
        //acquista
        cA.getNomeCostoDisp();
        vis.setSpesaT(cA.getPrezzo("20"));
        //ccredito
        String emailE=RBUTENTE.getString("emailE");
        u.setEmail(emailE);
        String nome= UsersDao.pickData(u).getNome();
        String cognome=UsersDao.pickData(u).getCognome();
        String codice=RBCARTACREDITO.getString("codice");
        String civ=RBCARTACREDITO.getString("civ");
        String data=RBCARTACREDITO.getString("data");
        cPCC.aggiungiCartaDB(nome,cognome,codice, Date.valueOf(data),civ,vis.getSpesaT());
        //download
        cD.scarica(vis.getType());
        assertNotEquals(0,vis.getId());
    }
   @Test
    void testListaCC() throws CsvValidationException, IOException {
        vis.setTypeOfDb("db");
        assertNotNull(cPCC.ritornaElencoCC(u.getNome()));
    }

}
