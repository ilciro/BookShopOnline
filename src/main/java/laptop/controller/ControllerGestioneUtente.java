package laptop.controller;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.UsersDao;
import laptop.database.csvusers.CsvUtente;
import laptop.exception.IdException;
import laptop.model.TempUser;
import laptop.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class ControllerGestioneUtente {

    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final User u=User.getInstance();
    private final CsvUtente csv;
    private final TempUser tu;

    //modif
    public User getDataDao() throws SQLException {

        tu.setId(vis.getId());
        u.setId(tu.getId());
       return UsersDao.pickData(u);

    }

    public User getDataCSV() throws CsvValidationException, IOException {
        tu.setId(vis.getId());
        u.setId(tu.getId());
        return csv.userList(u).get(0);
    }

    public boolean inserisciUtente(String ruolo, String nome, String cognome, String email, String pwd, String desc, LocalDate data) throws SQLException, CsvValidationException, IOException, IdException {

        boolean status;
        u.setIdRuolo(ruolo.substring(0,1));
        u.setNome(nome);
        u.setCognome(cognome);
        u.setEmail(email);
        u.setPassword(pwd);
        u.setDescrizione(desc);
        u.setDataDiNascita(data);
        if(vis.getTypeOfDb().equals("db"))
            status = UsersDao.createUser(u);
        else {

            status = csv.inserisciUtente(u);
        }

        return status;
    }

    public ControllerGestioneUtente()
    {
        csv=new CsvUtente();
        tu=new TempUser();

        tu.setId(vis.getId());
    }

    public boolean modifica(String ruolo, String nome, String cognome, String email, String pwd, String desc, LocalDate data) throws SQLException, CsvValidationException, IOException, IdException {
        boolean status = false;
        

        u.setId(vis.getId());
        u.setIdRuolo(ruolo.substring(0,1));
        u.setNome(nome);
        u.setCognome(cognome);
        u.setEmail(email);
        u.setPassword(pwd);
        u.setDescrizione(desc);
        u.setDataDiNascita(data);
        if(vis.getTypeOfDb().equals("db")) {
            if(UsersDao.aggiornaUtente(u, null).getId()!=0)
                status=true;
        }
        else {

           csv.removeUserByIdEmailPwd(u);
            u.setNome(nome);
            u.setCognome(cognome);
            u.setEmail(email);
            u.setPassword(pwd);
            u.setDescrizione(desc);
            u.setDataDiNascita(data);
            status=csv.inserisciUtente(u);
           
        }

        return status;
    }
}
