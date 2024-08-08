package web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.database.UsersDao;
import laptop.model.User;

import web.bean.UserBean;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;

@WebServlet("/InserisciUtenteServlet")
public class InserisciUtenteServlet extends HttpServlet {
    private static final UserBean uB=UserBean.getInstance();
    private static final User u=User.getInstance();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome=req.getParameter("nomeU");
        String cognome=req.getParameter("cognomeU");
        String email=req.getParameter("emailU");
        String pass=req.getParameter("passU");
        String desc=req.getParameter("descU");
        String dataN=req.getParameter("dataU");
        String invia=req.getParameter("buttonI");
        String indietro=req.getParameter("buttonA");
        RequestDispatcher view;
        try {
            if(invia!=null && invia.equals("invia"))
            {
                uB.setRuoloB("U");
                uB.setNomeB(nome);
                uB.setCognomeB(cognome);
                uB.setEmailB(email);
                uB.setPassB(pass);
                uB.setDescrizioneB(desc);
                java.util.Date utilDate;
                java.sql.Date sqlDate;
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");


                utilDate = format.parse(dataN);
                sqlDate = new java.sql.Date(utilDate.getTime());
                UserBean.getInstance().setDataDiNascitaB(sqlDate.toLocalDate());

                u.setIdRuolo(uB.getRuoloB());
                u.setNome(uB.getNomeB());
                u.setCognome(uB.getCognomeB());
                u.setEmail(uB.getEmailB());
                u.setPassword(uB.getPassB());
                u.setDescrizione(uB.getDescrizioneB());
                u.setDataDiNascita(uB.getDataDiNascitaB());

                if(UsersDao.createUser(User.getInstance()))
                {
                   uB.setRuoloB("A");
                    req.setAttribute("beanUb",UserBean.getInstance());

                    view=getServletContext().getRequestDispatcher("/profilo.jsp");
                    view.forward(req, resp);
                }
                else {
                    uB.setRuoloB("A");

                    UserBean.getInstance().setMexB("errore nella creazione del nuovo utente");
                    req.setAttribute("beanUb",UserBean.getInstance());
                     view=getServletContext().getRequestDispatcher("/inserisciUtente.jsp");
                    view.forward(req, resp);
                }
            }
            if(indietro!=null && indietro.equals("indietro"))
            {
                 view=getServletContext().getRequestDispatcher("/profilo.jsp");
                view.forward(req, resp);
            }
        }catch(SQLException | ParseException  e)
        {
            java.util.logging.Logger.getLogger("post ").log(Level.INFO, "eccezione nel post {0}.",e.toString());

        }
     }
 }

