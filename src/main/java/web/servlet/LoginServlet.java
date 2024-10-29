package web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.database.UsersDao;
import laptop.model.User;
import org.apache.ibatis.jdbc.SQL;
import web.bean.UserBean;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private final UserBean uB=UserBean.getInstance();
    private final User u= User.getInstance();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("emailL");
        String passL = req.getParameter("passL");
        String login = req.getParameter("login");
        RequestDispatcher view;

        try {
            if (login != null && login.equals("login")) {
                uB.setEmailB(email);
                uB.setPassB(passL);
                u.setEmail(uB.getEmailB());
                u.setPassword(uB.getPassB());
                if (UsersDao.checkUser(u) == 1) {
                    switch (UsersDao.getRuolo(u)) {
                        case "ADMIN", "A" -> {
                            view = getServletContext().getRequestDispatcher("/admin.jsp");
                            view.forward(req, resp);
                        }
                        case "SCRITTORE", "EDITORE", "S", "E","W" -> {
                            view = getServletContext().getRequestDispatcher("/scrittoreEditore.jsp");
                            view.forward(req, resp);

                        }
                        case "UTENTE", "U" -> {
                            view = getServletContext().getRequestDispatcher("/utente.jsp");
                            view.forward(req, resp);

                        }
                        default -> throw new SQLException(" user not found \n");
                    }
                }
                else{
                    throw new SQLException(" please register as a new user" +
                            "\nclick on button register!!");
                }

            }
        } catch (SQLException | ServletException e) {

            uB.setMexB(e.toString());
            req.setAttribute("beanUb", uB);
            view = getServletContext().getRequestDispatcher("/login.jsp");
            view.forward(req, resp);

        }
    }
}
