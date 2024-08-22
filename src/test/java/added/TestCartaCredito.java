package added;

import laptop.model.CartaDiCredito;
import laptop.utilities.ConnToDb;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import web.bean.CartaCreditoBean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.time.LocalDate;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.jupiter.api.Assertions.assertNotNull;

 class TestCartaCredito {
    private final CartaDiCredito cc=new CartaDiCredito();
    private final CartaCreditoBean cCB=new CartaCreditoBean();

     @BeforeAll
     static void init() throws IOException {
         ConnToDb.creaPopolaDb();
         Files.copy(Path.of("src/main/resources/csvfiles/utente.csv"), Path.of("report/reportUtente.csv"), REPLACE_EXISTING);
         Files.copy(Path.of("src/main/resources/csvfiles/libro.csv"), Path.of("report/reportLibro.csv"), REPLACE_EXISTING);
         Files.copy(Path.of("src/main/resources/csvfiles/giornale.csv"), Path.of("report/reportGiornale.csv"), REPLACE_EXISTING);
         Files.copy(Path.of("src/main/resources/csvfiles/rivista.csv"), Path.of("report/reportRivista.csv"), REPLACE_EXISTING);



     }
    @Test
    void setters()
    {
        cc.setNomeUser("franco");
        cc.setCognomeUser("rossi");
        cc.setNumeroCC("1852-9662-4785-1880");
        cc.setScadenza(Date.valueOf(LocalDate.of(2025,8,8)));
        cc.setPrezzoTransazine(163f);
        cc.setCiv("185");

        cCB.setNomeB(cc.getNomeUser());
        cCB.setCognomeB(cc.getCognomeUser());
        cCB.setNumeroCCB(cc.getNumeroCC());
        cCB.setDataScadB(cc.getScadenza());
        cCB.setPrezzoTransazioneB(cc.getPrezzoTransazine());
        cCB.setCivB(cc.getCiv());

        assertNotNull(cCB.getNumeroCCB());
    }

}
