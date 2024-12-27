package web.primoUC;

import laptop.controller.ControllerHomePage;
import laptop.controller.ControllerSystemState;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class InitTest {
    private static final ControllerHomePage cHP=new ControllerHomePage();


    @Test
    void testPopola() throws IOException {
         cHP.dB();
         cHP.files();

        String init="initial test--> create dbs";
        Logger.getLogger("init").log(Level.INFO, "{0}",init);
        ControllerSystemState.getInstance().setTypeOfDb("db");
        assertNotEquals("",init);
     }
}
