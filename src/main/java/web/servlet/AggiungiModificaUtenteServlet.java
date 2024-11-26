package web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.database.UsersDao;
import laptop.model.TempUser;
import laptop.model.User;
import web.bean.SystemBean;
import web.bean.TempUserBean;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/AggiungiModificaUtenteServlet")
public class AggiungiModificaUtenteServlet extends HttpServlet {
    private static final SystemBean sB=SystemBean.getInstance();
    private static final TempUserBean tUB=new TempUserBean();
    private static final User u= User.getInstance();
    private static final String TEMPUB="tempUb";
    private static final String AGGIUNGIMODIFICA="/aggiungiModificaUtente.jsp";
    private static final String GESTIONE="/gestioneUtenti.jsp";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String r=req.getParameter("ruolo");
        String n=req.getParameter("nome");
        String c=req.getParameter("cognome");
        String m=req.getParameter("email");
        String p=req.getParameter("pass");
        String desc=req.getParameter("desc");
        LocalDate data= LocalDate.parse(req.getParameter("data"));
        String inserisci=req.getParameter("inserisci");
        String indietro=req.getParameter("indietro");
        //roba per modifica
        String ruoloM=req.getParameter("ruoloN");
        String nomeM=req.getParameter("nomeN");
        String cognomeM=req.getParameter("cognomeN");
        String mailN=req.getParameter("emailN");
        String passM=req.getParameter("passN");
        String descM=req.getParameter("descN");
        LocalDate dataM= LocalDate.parse(req.getParameter("dataN"));
        String modifica=req.getParameter("modifica");

        RequestDispatcher view;

try {

            if (inserisci != null && inserisci.equals("inserisci")) {
                if (checkRuolo(r)) {
                    tUB.setRuoloBNOS(r);
                    tUB.setNomeBNOS(n);
                    tUB.setCognomeBNOS(c);
                    tUB.setEmailBNOS(m);
                    tUB.setPassBNOS(p);
                    tUB.setDescrizioneBNOS(desc);
                    tUB.setDataDiNascitaBNOS(data);

                    u.setIdRuolo(tUB.getRuoloB());
                    u.setNome(tUB.getNomeBNOS());
                    u.setCognome(tUB.getCognomeBNOS());
                    u.setEmail(tUB.getEmailBNOS());
                    u.setPassword(tUB.getPassBNOS());
                    u.setDescrizione(tUB.getDescrizioneBNOS());
                    u.setDataDiNascita(tUB.getDataDiNascitaBNOS());
                    if (UsersDao.createUser(u)) {

                        tUB.setMexBNOS(" user is created successfully!!");
                        req.setAttribute(TEMPUB, tUB);
                        view = getServletContext().getRequestDispatcher(GESTIONE);
                        view.forward(req, resp);
                    }


                } else {
                    tUB.setMexBNOS(" role of user is wrong!!");
                    req.setAttribute(TEMPUB,tUB);
                    view=getServletContext().getRequestDispatcher(AGGIUNGIMODIFICA);
                    view.forward(req,resp);

                }
            }



            if(modifica!=null && modifica.equals("modifica") ) {

                if(checkRuolo(ruoloM)) {
                    tUB.setIdBNOS(sB.getIdB() + 1);
                    tUB.setRuoloBNOS(ruoloM);
                    tUB.setNomeBNOS(nomeM);
                    tUB.setCognomeBNOS(cognomeM);
                    tUB.setEmailBNOS(mailN);
                    tUB.setPassBNOS(passM);
                    tUB.setDescrizioneBNOS(descM);
                    tUB.setDataDiNascitaBNOS(dataM);


                    if (UsersDao.aggiornaTempUser(passaDati()) == 1) {
                        req.setAttribute(TEMPUB, tUB);
                        view = getServletContext().getRequestDispatcher(GESTIONE);
                        view.forward(req, resp);
                    } else {
                        req.setAttribute(TEMPUB, tUB);
                        view = getServletContext().getRequestDispatcher(AGGIUNGIMODIFICA);
                        view.forward(req, resp);
                    }
                }
                else {
                    tUB.setMexBNOS(" role of user is wrong!!");
                    req.setAttribute(TEMPUB,tUB);
                    view=getServletContext().getRequestDispatcher(AGGIUNGIMODIFICA);
                    view.forward(req,resp);

                }
                }


    if(indietro!=null && indietro.equals("indietro"))
    {
        view=getServletContext().getRequestDispatcher(GESTIONE);
        view.forward(req,resp);
    }
}catch (SQLException |ServletException |IOException e)
{
    Logger.getLogger("doPost").log(Level.SEVERE, " exception " , e);
    req.setAttribute(TEMPUB,tUB);
    view=getServletContext().getRequestDispatcher(AGGIUNGIMODIFICA);
    view.forward(req,resp);
}
    }

    private TempUser passaDati() {
        TempUser tu=new TempUser();
        tu.setId(AggiungiModificaUtenteServlet.tUB.getIdBNOS());
        tu.setIdRuoloT(AggiungiModificaUtenteServlet.tUB.getRuoloB());
        tu.setNomeT(AggiungiModificaUtenteServlet.tUB.getNomeBNOS());
        tu.setCognomeT(AggiungiModificaUtenteServlet.tUB.getCognomeBNOS());
        tu.setEmailT(AggiungiModificaUtenteServlet.tUB.getEmailBNOS());
        tu.setPasswordT(AggiungiModificaUtenteServlet.tUB.getPassBNOS());
        tu.setDescrizioneT(AggiungiModificaUtenteServlet.tUB.getDescrizioneBNOS());
        tu.setDataDiNascitaT(AggiungiModificaUtenteServlet.tUB.getDataDiNascitaBNOS());
        return tu;
    }

    private boolean checkRuolo(String ruolo)
    {
        String ruoloU;
        boolean status=false;
        ruoloU=ruolo.toUpperCase();
        if(ruoloU.charAt(0) == 'E'|| ruoloU.charAt(0) == 'A'
        || ruoloU.charAt(0) == 'S'|| ruoloU.charAt(0) == 'W'
        || ruoloU.charAt(0) == 'U')
            status=true;
        return status;
    }
}
