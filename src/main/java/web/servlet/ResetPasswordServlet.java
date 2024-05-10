package web.servlet;

import java.io.IOException;

import java.io.Serial;
import java.sql.SQLException;
import java.util.logging.Level;

import laptop.database.UsersDao;
import laptop.model.User;
import web.bean.UserBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    private static final UserBean uB=UserBean.getInstance();
    private static final User u= User.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email=req.getParameter("emailL");
        String vecchiaPass=req.getParameter("vecchiaPassL");
        String nuovaPass=req.getParameter("nuovaPassL");
        String invia=req.getParameter("buttonI");
        String indietro=req.getParameter("buttonA");
        try {
            if(invia!=null && invia.equals("reset pass") )
            {
                uB.setEmailB(email);
                uB.setPassB(vecchiaPass);
                u.setEmail(uB.getEmailB());
                u.setPassword(uB.getPassB());
                if(UsersDao.checkUser(u)==1) {
                    if(UsersDao.checkResetpass(u,nuovaPass,email))
                    {
                        RequestDispatcher view = getServletContext().getRequestDispatcher("/index.jsp");
                        view.forward(req,resp);
                    }
                    else
                    {
                        RequestDispatcher view = getServletContext().getRequestDispatcher("/resetPassword.jsp");
                        view.forward(req,resp);
                    }
                }

            }


            if(indietro!=null && indietro.equals("indietro"))
            {
                RequestDispatcher view = getServletContext().getRequestDispatcher("/login.jsp");
                view.forward(req,resp);
            }
        } catch (SQLException e) {
            java.util.logging.Logger.getLogger("post ").log(Level.INFO, "eccezione nel post {0}.",e.toString());

        }
    }




}