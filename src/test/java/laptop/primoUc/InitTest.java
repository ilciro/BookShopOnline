package laptop.primoUc;

import laptop.controller.ControllerHomePage;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


class InitTest {
    private static final ControllerHomePage cHP=new ControllerHomePage();

    @Test
     void testPopolaDB()  {
        assertDoesNotThrow(cHP::creaDb);
    }
    @Test
    void testPopolaFile() {
        assertDoesNotThrow(cHP::copiaFiles);
    }

}
