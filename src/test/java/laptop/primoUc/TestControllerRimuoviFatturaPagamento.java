package laptop.primoUc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerAnnullaPagamento;
import laptop.controller.ControllerSystemState;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerRimuoviFatturaPagamento {
     private static final ControllerAnnullaPagamento cAP;
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();

    static {
        try {
            cAP = new ControllerAnnullaPagamento();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Test
    void testEliminaUltimaFatturaDb() throws SQLException, CsvValidationException, IOException {
        vis.setTypeOfDb("db");
        String[] arr=cAP.getFattura().split(", ");
        int numero=0;
        for(String s1:arr)
        {
            if(s1.contains("numero"))
            {
                String[] bb = s1.split("=");
                numero=Integer.parseInt(bb[1]);
            }
        }

        assertTrue(cAP.cancellaFattura(String.valueOf(numero)));

    }
    @Test
    void eliminaUltimaFatturaF() throws CsvValidationException, SQLException, IOException {
        vis.setTypeOfDb("file");
        String[] arr=cAP.getFattura().split(",");
        int numero=0;
        for(String s1:arr)
        {

            if(s1.contains("numero"))
            {
                String[] bb = s1.split("=");
                numero=Integer.parseInt(bb[1]);
            }


        }
        assertTrue(cAP.cancellaFattura(String.valueOf(numero)));

    }

    @Test
    void testEliminaUltimoPagamentoDb() throws SQLException, CsvValidationException, IOException {
        vis.setTypeOfDb("db");
        String[] arr=cAP.getPagamento().split(", ");
        int numero=0;
        for(String s1:arr)
        {
            if(s1.contains("[id"))
            {
                String[] bb = s1.split("=");
                numero=Integer.parseInt(bb[1]);
            }
        }
        assertTrue(cAP.cancellaPagamento(String.valueOf(numero)));

    }
    @Test
    void testEliminaUtimoPagamentoF() throws CsvValidationException, IOException, SQLException {
        vis.setTypeOfDb("file");
        String[] arr=cAP.getPagamento().split(",");
        int numero=0;
        for(String s1:arr)
        {
            if(s1.contains("[id"))
            {
                String[] bb = s1.split("=");
                numero=Integer.parseInt(bb[1]);
            }
        }
        assertTrue(cAP.cancellaPagamento(String.valueOf(numero)));


    }
}
