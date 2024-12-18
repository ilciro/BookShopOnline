package laptop.controller;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.UsersDao;
import laptop.database.csvusers.CsvUtente;
import laptop.exception.IdException;
import laptop.model.User;

import java.io.IOException;
import java.time.LocalDate;

public class ControllerVisualizzaProfilo {

    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final User u=User.getInstance();
    private final CsvUtente csv;

    public String infoUtente()
    {
        return User.getInstance().toString();
    }
    public ControllerVisualizzaProfilo()
    {
        csv=new CsvUtente();
    }

    public boolean modifica(String ruolo, String nome, String cognome, String email, String pwd, String desc, LocalDate value) throws CsvValidationException, IOException, IdException {
        boolean status;
        String vecchiaMail=u.getEmail();
        modificaUtenteDao(ruolo,nome,cognome,email,pwd,desc,value);

        if(vis.getTypeOfDb().equals("db")) {
            UsersDao.aggiornaUtente(u, vecchiaMail);
            status=true;
        }
        else {
           modificaUtenteCsv(ruolo,nome,cognome,email,pwd,desc,value);
            status=csv.inserisciUtente(u);

        }
        return status;
    }

    private void modificaUtenteDao(String ruolo, String nome, String cognome, String email, String pwd, String desc, LocalDate value)
    {
        if( !ruolo.isEmpty())
            u.setIdRuolo(ruolo.substring(0,1));
        if(!nome.isEmpty())
            u.setNome(nome);
        if(!cognome.isEmpty())
            u.setCognome(cognome);
        if(!email.isEmpty())
            u.setEmail(email);
        if(!pwd.isEmpty())
            u.setPassword(pwd);
        if(!desc.isEmpty())
            u.setDescrizione(desc);
        if(value!=null)
            u.setDataDiNascita(value);
    }
    private void modificaUtenteCsv(String ruolo, String nome, String cognome, String email, String pwd, String desc, LocalDate value) throws CsvValidationException, IOException {
        String ruoloT=u.getIdRuolo();
        String nomeT=u.getNome();
        String cognomeT=u.getCognome();
        String emailT=u.getEmail();
        String passT=u.getPassword();
        String descT=u.getDescrizione();
        LocalDate dataT=u.getDataDiNascita();

        csv.removeUserByIdEmailPwd(u);
        if(ruolo.isEmpty())
            u.setIdRuolo(ruoloT);
        if(nome.isEmpty())
            u.setNome(nomeT);
        if(cognome.isEmpty())
            u.setCognome(cognomeT);
        if(email.isEmpty())
            u.setEmail(emailT);
        if(pwd.isEmpty())
            u.setPassword(passT);
        if(desc.isEmpty())
            u.setDescrizione(descT);
        if(value==null)
            u.setDataDiNascita(dataT);
    }
}
