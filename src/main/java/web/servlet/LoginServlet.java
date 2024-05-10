package web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.database.UsersDao;
import laptop.model.User;
import org.apache.commons.beanutils.PropertyUtils;

import web.bean.SystemBean;
import web.bean.UserBean;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.logging.Level;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final UserBean uB=UserBean.getInstance();
    private static final User u= User.getInstance();
    private static final SystemBean sB=SystemBean.getInstance();
    private static final String BEANS="beanS";
    private static final String BEANUB="beanUb";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email=req.getParameter("emailL");
        String pass=req.getParameter("passL");
        String login=req.getParameter("loginB");
        String annulla=req.getParameter("annullaB");
        String registra=req.getParameter("registerB");
        String reset=req.getParameter("resetB");
        RequestDispatcher view;
        try {
            if (login != null && login.equals("login")) {
                //switch page to go

                uB.setEmailB(email);
                uB.setPassB(pass);

                u.setEmail(uB.getEmailB());
                u.setPassword(uB.getPassB());
                if(UsersDao.checkUser(u)==1) {
                    u.setIdRuolo(UsersDao.getRuolo(u));
                    uB.setRuoloB(u.getIdRuolo());

                    switch (uB.getRuoloB()) {
                        case "A", "a"-> {
                            sB.setLoggedB(true);
                            req.setAttribute(BEANUB, uB);
                            req.setAttribute(BEANS, sB);
                            view = getServletContext().getRequestDispatcher("/admin.jsp");
                            view.forward(req, resp);
                        }
                        case "U", "u", "W", "w", "S", "s", "E", "e"-> {
                            sB.setLoggedB(true);
                            req.setAttribute(BEANUB, uB);
                            req.setAttribute(BEANS, sB);
                            view = getServletContext().getRequestDispatcher("/utenti.jsp");
                            view.forward(req, resp);
                        }
                        default->java.util.logging.Logger.getLogger("login").log(Level.SEVERE, "login  error");


                    }
                }
                else {
                    uB.setMexB("credenziali sbagliare .. riprovare");
                    req.setAttribute(BEANUB,uB);
                    view= getServletContext().getRequestDispatcher("/login.jsp");
                    view.forward(req,resp);
                }


            }
            if(annulla!=null && annulla.equals("indietro"))
            {
                view = getServletContext().getRequestDispatcher("/index.jsp");
                view.forward(req,resp);

            }
            if(registra!=null && registra.equals("registrati"))
            {
                view = getServletContext().getRequestDispatcher("/registrazione.jsp");
                view.forward(req,resp);
            }
            if(reset!=null && reset.equals("reset password"))
            {
                view = getServletContext().getRequestDispatcher("/resetPassword.jsp");
                view.forward(req,resp);
            }
            else {
                PropertyUtils.setProperty(uB,"mexB","utente non registrato / credenziali sbagliate ... per favore registrarsi");
                PropertyUtils.setProperty(sB,"loggedB",false);

                req.setAttribute(BEANUB,uB);
                req.setAttribute(BEANS,sB);
                view = getServletContext().getRequestDispatcher("/login.jsp");
                view.forward(req,resp);
            }




        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SQLException e) {
            java.util.logging.Logger.getLogger("post ").log(Level.INFO, "eccezione nel post {0}.",e.toString());

        }


    }
}
