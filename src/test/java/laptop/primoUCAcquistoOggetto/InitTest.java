package laptop.primoUCAcquistoOggetto;

import laptop.controller.ControllerHomePage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class InitTest {
    private static final ControllerHomePage cHP=new ControllerHomePage();

    @BeforeAll
    static void init() throws IOException {
        cHP.dB();
        cHP.files();
    }
    @Test
    void test()
    {
        String init="initial test--> create dbs";
        Logger.getLogger("init").log(Level.INFO, "{0}",init);
        assertNotEquals("",init);
    }
}
