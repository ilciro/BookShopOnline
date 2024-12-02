package laptop.primoUc;

import laptop.controller.ControllerPagamentoCash;
import laptop.controller.ControllerSystemState;
import laptop.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestControllerPagamentoCash {
     private static final ControllerPagamentoCash cPCash;
     private static final ResourceBundle RBUTENTI=ResourceBundle.getBundle("configurations/users");
     private static final User u= User.getInstance();
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    static {
        try {
            cPCash = new ControllerPagamentoCash();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void testGetData()
    {
        u.setNome(RBUTENTI.getString("nome"));
        u.setCognome(RBUTENTI.getString("cognome"));
        assertNotNull(cPCash.getInfo());
    }

    @ParameterizedTest
    @ValueSource(strings = {"file","db"})
    void testControllaDBF(String strings)
    {
        vis.setTypeOfDb(strings);

        vis.setMetodoP("cash");
        assertDoesNotThrow(()->cPCash.controlla(RBUTENTI.getString("nome"),RBUTENTI.getString("cognome"),RBUTENTI.getString("via"), RBUTENTI.getString("com")));
    }




}
