package laptop.primoUc;

import laptop.controller.ControllerScegliNegozio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestControllerScegliNegozio {
     private static final ControllerScegliNegozio cSN=new ControllerScegliNegozio();

     @Test
    void testListaNegozi()
     {
         assertNotNull(cSN.getNegozi());
     }
}
