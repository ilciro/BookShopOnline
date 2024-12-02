package laptop.secondoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerRegistraUtente;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerRegistraUtente {
     private static final ControllerRegistraUtente cRU=new ControllerRegistraUtente();
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ResourceBundle RBUTENTI=ResourceBundle.getBundle("configurations/users");

     @ParameterizedTest
     @ValueSource(strings = {"db","file"})
    void testRegistraUtente(String strings) throws CsvValidationException, SQLException, IOException, IdException {
         LocalDate date=LocalDate.of(1984,2,6);
         vis.setTypeOfDb(strings);
         assertTrue(cRU.registra(RBUTENTI.getString("nome"),RBUTENTI.getString("cognome"),RBUTENTI.getString("email"),RBUTENTI.getString("pwd"),RBUTENTI.getString("desc"),date,RBUTENTI.getString("ruolo")));
     }
}
