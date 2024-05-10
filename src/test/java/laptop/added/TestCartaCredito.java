package laptop.added;

import laptop.model.CartaDiCredito;
import org.junit.jupiter.api.Test;
import web.bean.CartaCreditoBean;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

 class TestCartaCredito {
    private final CartaDiCredito cc=new CartaDiCredito();
    private final CartaCreditoBean cCB=new CartaCreditoBean();

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
