package laptop.secodoUCLoginRegistrazioneUtente.pagamento;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.ControllerVisualizzaOrdini;
import laptop.model.User;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerAnnullaPagamento {
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ControllerVisualizzaOrdini cVO;
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private static final User u=User.getInstance();

    static {
        try {
            cVO = new ControllerVisualizzaOrdini();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void testAnnullaPagamentoDb() throws CsvValidationException, IOException, SQLException {
        vis.setTypeOfDb("db");
        u.setEmail(RBUTENTE.getString("emailE"));
        cVO.getLista();
        assertTrue(cVO.cancellaPagamento(Integer.parseInt(RBOGGETTO.getString("idPagDb"))));

    }

    @Test
    void testAnnullaPagamentoFile() throws CsvValidationException, IOException, SQLException {
        vis.setTypeOfDb("file");
        u.setEmail(RBUTENTE.getString("emailE"));
        cVO.getLista();
        assertTrue(cVO.cancellaPagamento(Integer.parseInt(RBOGGETTO.getString("idPagF"))));

    }
}
