package web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.database.UsersDao;
import laptop.model.User;
import web.bean.SystemBean;
import web.bean.UserBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final UserBean uB=UserBean.getInstance();
    private static final User u= User.getInstance();
    private static final SystemBean sB=SystemBean.getInstance();
    private static final String BEANUB="beanUb";
    private static final String BEANS="beanS";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("emailL");
        String passL = req.getParameter("passL");
        String login = req.getParameter("login");
        String reset=req.getParameter("reset");
        RequestDispatcher view;

        try {
            if (login != null && login.equals("login")) {
                uB.setEmailB(email);
                uB.setPassB(passL);
                u.setEmail(uB.getEmailB());
                u.setPassword(uB.getPassB());
                if (UsersDao.checkUser(u) == 1) {
                    switch (UsersDao.getRuolo(u)){


                        case "ADMIN", "A" -> {
                            sB.setLoggedB(true);
                            uB.setNomeB(UsersDao.pickData(u).getNome());
                            uB.setCognomeB(UsersDao.pickData(u).getCognome());
                            req.setAttribute(BEANUB,uB);
                            req.setAttribute(BEANS,sB);
                            view = getServletContext().getRequestDispatcher("/admin.jsp");
                            view.forward(req, resp);
                        }
                        case "SCRITTORE", "EDITORE", "S", "E","W" -> {
                            sB.setLoggedB(true);

                            uB.setNomeB(UsersDao.pickData(u).getNome());
                            uB.setCognomeB(UsersDao.pickData(u).getCognome());
                            req.setAttribute(BEANUB,uB);
                            req.setAttribute(BEANS,sB);


                            view = getServletContext().getRequestDispatcher("/scrittoreEditore.jsp");
                            view.forward(req, resp);

                        }
                        case "UTENTE", "U" -> {
                            sB.setLoggedB(true);
                            uB.setNomeB(UsersDao.pickData(u).getNome());
                            uB.setCognomeB(UsersDao.pickData(u).getCognome());
                            req.setAttribute(BEANUB,uB);
                            req.setAttribute(BEANS,sB);


                            view = getServletContext().getRequestDispatcher("/utente.jsp");
                            view.forward(req, resp);

                        }
                        default -> throw new SQLException(" user not found \n");
                    }
                }
                else{
                    uB.setMexB(new SQLException(" please register as a new user" +
                            "\nclick on button register!!").toString());
                    view = getServletContext().getRequestDispatcher("/login.jsp");
                    view.forward(req, resp);
                }

            }
            if(reset!=null && reset.equals("reset"))
            {
                uB.setEmailB(email);
                uB.setPassB(passL);
                u.setEmail(uB.getEmailB());
                u.setPassword(uB.getPassB());
                if(UsersDao.checkUser(u)==1)
                {
                    req.setAttribute(BEANUB,uB);
                    view=getServletContext().getRequestDispatcher("/reset.jsp");
                    view.forward(req,resp);
                }

            }
        } catch (SQLException | ServletException e) {

            Logger.getLogger("do post ").log(Level.SEVERE, " exception has occurred :.",e);


        }
    }

}
