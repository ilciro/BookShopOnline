package laptop.testFinali;

import laptop.database.CartaCreditoDao;
import laptop.model.CartaDiCredito;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestCartaCredito {

     private static final CartaDiCredito cc=new CartaDiCredito();
     private static final CartaCreditoDao cCD=new CartaCreditoDao();

     @Test
    void testListaCC()
     {
         cc.setNumeroCC("9512-6662-2225-9962");
         assertNotNull(cCD.popolaDati(cc));
     }
}
