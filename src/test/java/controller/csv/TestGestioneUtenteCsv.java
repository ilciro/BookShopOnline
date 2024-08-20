package controller.csv;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.*;
import laptop.exception.IdException;
import laptop.model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGestioneUtenteCsv {

    private static final User u= User.getInstance();
    private final ResourceBundle RBUSER=ResourceBundle.getBundle("configurations/users");
    private final ControllerScelta cs=new ControllerScelta();
    private final ControllerLogin cL=new ControllerLogin();
    private final ControllerUserPage cUP=new ControllerUserPage();
    private final ControllerAggiungiUtente cAU=new ControllerAggiungiUtente();
    private final ControllerModificaUtente cMU=new ControllerModificaUtente();
    private final ControllerCancellaUser cCU=new ControllerCancellaUser();




    @Test
    void InsertModifDeleteUser() throws CsvValidationException, IOException, IdException, SQLException, ParseException {
       //login
        cs.getTypeDb("file");
        cL.controlla("admin@admin.com", "Admin871");
        //prendo data
        cUP.getCsvData();
        u.setIdRuolo(RBUSER.getString("ruolo").substring(0,1));
        u.setNome(RBUSER.getString("nome"));
        u.setCognome(RBUSER.getString("cognome"));
        u.setEmail(RBUSER.getString("email"));
        u.setPassword(RBUSER.getString("pwd"));
        u.setDescrizione(RBUSER.getString("desc"));
        u.setDataDiNascita(LocalDate.of(1946, 8, 8));
        //insert
        cAU.checkData(u.getNome(),u.getCognome(),u.getEmail(),u.getPassword(), "1946/08/08");
        //modif
        cMU.aggiornaTot("daniele","cinus","danielecinus10@gmail.com","danici31","utente modificato",LocalDate.of(1995,10,31),"E");
        //delete for cancel another user set email
        assertTrue(cCU.cancellaUser());
        //delete last user insetred
    }

    //funziona


}


