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
import web.bean.UserBean;

import java.io.IOException;
import java.sql.SQLException;

import java.util.logging.Level;

@WebServlet("/ModificaUtenteServlet")
public class ModificaUtenteServlet extends HttpServlet {

    private static final UserBean uB=UserBean.getInstance();
    private static final User u=User.getInstance();
    private static final TempUser tUser=TempUser.getInstance();
    private static final String MODIFICA="/modificaUtente.jsp";
    private static final String BEANUB="beanUb";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String genera=req.getParameter("generaB");
        String aggiorna=req.getParameter("buttonI");
        String indietro=req.getParameter("buttonA");
        String elimina=req.getParameter("elimina");
        RequestDispatcher view;
        try {
            if(genera!=null &&genera.equals("prendi valori"))
            {

               u.setId(uB.getIdB());
               tUser.setId(uB.getIdB());

                //creato di dafault
                uB.setRuoloB(UsersDao.getTempUserSingolo(tUser).getIdRuolo());
                uB.setNomeB(UsersDao.getTempUserSingolo(tUser).getNomeT());
                uB.setCognomeB(UsersDao.getTempUserSingolo(tUser).getCognomeT());
                uB.setEmailB(UsersDao.getTempUserSingolo(tUser).getEmailT());
                uB.setPassB(UsersDao.getTempUserSingolo(tUser).getPasswordT());
                uB.setDescrizioneB(UsersDao.getTempUserSingolo(tUser).getDescrizioneT());
                uB.setDataDiNascitaB(UsersDao.getTempUserSingolo(tUser).getDataDiNascitaT());




                req.setAttribute(BEANUB,uB);
                 view=getServletContext().getRequestDispatcher(MODIFICA);
                view.forward(req, resp);

            }
            if(aggiorna!=null && aggiorna.equals("aggiorna"))
            {
                String ruoloN=req.getParameter("ruoloNL");
                String nomeN=req.getParameter("nomeNL");
                String cognomeN=req.getParameter("cognomeNL");
                String emailN=req.getParameter("emailNL");
                String passN=req.getParameter("passNL");
                String descN=req.getParameter("descNL");
                //String dataN=req.getParameter("dataNL");//cast


                uB.setRuoloB(ruoloN);
                uB.setNomeB(nomeN);
                uB.setCognomeB(cognomeN);
                uB.setEmailB(emailN);
                uB.setPassB(passN);
                uB.setDescrizioneB(descN);



                tUser.setId(uB.getIdB());
                tUser.setEmailT(uB.getEmailB());
                tUser.setIdRuolo(uB.getRuoloB());
                tUser.setNomeT(uB.getNomeB());
                tUser.setCognomeT(uB.getCognomeB());
                tUser.setPasswordT(uB.getPassB());
                tUser.setDescrizioneT(uB.getDescrizioneB());
                tUser.setDataDiNascitaT(uB.getDataDiNascitaB());

                if(UsersDao.aggiornaTempUser(tUser)==1)
                {
                    uB.setRuoloB("A");
                    req.setAttribute(BEANUB,uB);
                    view=getServletContext().getRequestDispatcher("/profilo.jsp");
                    view.forward(req, resp);
                }
                else{
                    uB.setRuoloB("A");
                    req.setAttribute(BEANUB,uB);
                    view=getServletContext().getRequestDispatcher(MODIFICA);
                    view.forward(req, resp);
                }

            }
            if(elimina!=null && elimina.equals("cancella utente"))
            {
                tUser.setId(uB.getIdB());
                if(UsersDao.deleteTempUser(tUser))
                {
                    uB.setRuoloB("A");
                    req.setAttribute(BEANUB,uB);
                    view=getServletContext().getRequestDispatcher("/profilo.jsp");
                    view.forward(req, resp);
                }
                else{
                    uB.setRuoloB("A");
                    req.setAttribute(BEANUB,uB);
                    view=getServletContext().getRequestDispatcher(MODIFICA);
                    view.forward(req, resp);
                }
            }
            if(indietro!=null && indietro.equals("indietro"))
            {
                uB.setRuoloB("A");
                req.setAttribute(BEANUB,uB);
                 view=getServletContext().getRequestDispatcher("/utenti.jsp");
                view.forward(req, resp);
            }
        }catch(SQLException e)
        {
            java.util.logging.Logger.getLogger("post ").log(Level.INFO, "eccezione nel post {0}.",e.toString());

        }

    }
}
