package web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.bean.UserBean;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/AdminServlet")

public class AdminServlet extends HttpServlet {
    private static final UserBean uB=UserBean.getInstance();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String logout=req.getParameter("logout");
        RequestDispatcher view;
        try {
            if (logout != null && logout.equals("logout")) {
                uB.setEmailB("");
                uB.setPassB("");
                view = getServletContext().getRequestDispatcher("/index.jsp");
                view.forward(req, resp);
            }
        }catch (IOException|ServletException e)
        {
            Logger.getLogger(" do post").log(Level.SEVERE," ecxeption has occurred !! .",e);
        }
    }
}
