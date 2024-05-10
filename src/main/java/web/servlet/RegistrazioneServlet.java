package web.servlet;

import java.io.IOException;
import java.io.Serial;
import java.sql.Date;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;

import web.bean.UserBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.database.UsersDao;
import laptop.model.User;

@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    private static final UserBean uB=UserBean.getInstance();
    private static final User u=User.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome=req.getParameter("nomeL");
        String cognome=req.getParameter("cognomeL");
        String email=req.getParameter("emailL");
        String pass=req.getParameter("passL");
        String confermaPass=req.getParameter("confermaPassLL");
        String data=req.getParameter("dataL");
        String invia=req.getParameter("inviaB");
        String indietro=req.getParameter("annullaB");
        try {
            if(invia!=null && invia.equals("registrati"))
            {

                if(pass.equals(confermaPass)) {
                    uB.setNomeB(nome);
                    uB.setCognomeB(cognome);
                    uB.setEmailB(email);
                    uB.setPassB(pass);
                    Date sqlDate = null;
                    java.util.Date utilDate;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");


                    utilDate = format.parse(data);
                    sqlDate = new Date(utilDate.getTime());


                    uB.setDataDiNascitaB(sqlDate.toLocalDate());

                    u.setNome(uB.getNomeB());
                    u.setCognome(uB.getCognomeB());
                    u.setEmail(uB.getEmailB());
                    u.setPassword(uB.getPassB());
                    u.setDataDiNascita(uB.getDataDiNascitaB());


                    if (UsersDao.checkUser(u) == 1) {
                        //utente già trovato
                        uB.setMexB("utente gia registrato nel sistema !!!");
                        req.setAttribute("beanUb", uB);
                        RequestDispatcher view = getServletContext().getRequestDispatcher("/registrazione.jsp");
                        view.forward(req, resp);
                    } else {
                        throw new SQLException("user not created");
                    }
                }else {
                    RequestDispatcher view = getServletContext().getRequestDispatcher("/registrazione.jsp");
                    view.forward(req, resp);
                }

            }
            if(indietro!=null && indietro.equals("indietro"))
            {
                RequestDispatcher view = getServletContext().getRequestDispatcher("/index.jsp");
                view.forward(req,resp);
            }
        }catch(SQLException | ParseException|NullPointerException e)
        {
            java.util.logging.Logger.getLogger("post ").log(Level.INFO, "eccezione nel post {0}.",e.toString());

            try {
                if (UsersDao.createUser(u)) {
                    RequestDispatcher view = getServletContext().getRequestDispatcher("/index.jsp");
                    view.forward(req, resp);
                } else {
                    RequestDispatcher view = getServletContext().getRequestDispatcher("/registrazione.jsp");
                    view.forward(req, resp);
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger("Exception ").log(Level.INFO, "eccezione nel post {0}.",e.toString());

            }
        }
    }









}