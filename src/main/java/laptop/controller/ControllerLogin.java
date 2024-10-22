package laptop.controller;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.UsersDao;
import laptop.database.csvusers.CsvUtente;
import laptop.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;

public class ControllerLogin {
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final User u= User.getInstance();
    private final CsvUtente csv;

    public String login(String email,String pwd) throws SQLException, CsvValidationException, IOException {

        String ruolo ;
        u.setEmail(email);
        u.setPassword(pwd);
        String nome;
        String cognome;
        int id;

        if(vis.getTypeOfDb().equalsIgnoreCase("db"))
        {

            ruolo=UsersDao.getRuolo(u);
            //added nome and cognome for fattuta
             nome=UsersDao.pickData(u).getNome();
             cognome=UsersDao.pickData(u).getCognome();
             id=UsersDao.pickData(u).getId();
        }
        else {
            ruolo = csv.userList(u).get(0).getIdRuolo();
            nome = csv.userList(u).get(0).getNome();
            cognome=csv.userList(u).get(0).getCognome();
            id=csv.userList(u).get(0).getId();
        }
        u.setNome(nome);
        u.setCognome(cognome);


        switch (ruolo)
        {
            case "U","u","utente","UTENTE"->
            {
                ruolo="UTENTE";
                vis.setIsLogged(true);
                u.setId(id);
            }
            case "A","a","admin","ADMIN"-> {
                ruolo = "ADMIN";
                vis.setIsLogged(true);
                u.setId(id);
            }
            case "S","s","W","w","SCRITTORE"-> {
                ruolo = "SCRITTORE";
                vis.setIsLogged(true);
                u.setId(id);
            }
            case "E","e","EDITORE"-> {
                ruolo = "EDITORE";
                vis.setIsLogged(true);
                u.setId(id);
            }
            default ->
            {
                ruolo="NONVALIDO";
                java.util.logging.Logger.getLogger(" login").log(Level.SEVERE, " user not found!!");
                vis.setIsLogged(false);
            }
        }
        return ruolo;

    }

    public boolean userPresente(String email,String pwd) throws SQLException, CsvValidationException, IOException {
        boolean status=false;
        u.setEmail(email);
        u.setPassword(pwd);


        if(vis.getTypeOfDb().equalsIgnoreCase("db"))
        {

            if(!Objects.equals(UsersDao.pickData(u).getNome(), ""))
            {
                status=true;
            }

        }else {
            if(!csv.userList(u).get(0).getNome().equalsIgnoreCase(""))
            {
                status=true;
            }



        }


        return status;
    }


    public ControllerLogin()
    {
        csv=new CsvUtente();
    }

}
