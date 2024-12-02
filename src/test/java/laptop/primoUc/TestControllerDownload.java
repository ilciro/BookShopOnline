package laptop.primoUc;

import laptop.controller.ControllerDownload;
import laptop.controller.ControllerSystemState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TestControllerDownload {
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ControllerDownload cD;

    static {
        try {
            cD = new ControllerDownload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10,11,12})
    void testDownloadL(int ints)
    {
        vis.setTypeAsBook();
        vis.setId(ints);
        assertDoesNotThrow(()->cD.scarica(vis.getType()));
    }
    @Test
    void testDownloadRivista()
    {
        vis.setTypeAsMagazine();
        vis.setId(1);
        assertDoesNotThrow(()->cD.scarica(vis.getType()));

    }
    @Test
    void testDownloadGiornale()
    {
        vis.setTypeAsDaily();
        vis.setId(1);
        assertDoesNotThrow(()->cD.scarica(vis.getType()));

    }


}
