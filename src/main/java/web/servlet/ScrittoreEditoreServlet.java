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

@WebServlet("/ScrittoreEditoreServlet")

public class ScrittoreEditoreServlet extends HttpServlet {
    private static final UserBean uB=UserBean.getInstance();
    private static final String BEANUB="beanUB";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cerca=req.getParameter("cerca");
        String ordini=req.getParameter("ordini");
        String gestione=req.getParameter("gestione");
        String logout=req.getParameter("logout");
        RequestDispatcher view;

        try {
            if (cerca != null && cerca.equals("cerca")) {
                req.setAttribute(BEANUB, uB);
                view = getServletContext().getRequestDispatcher("/ricerca.jsp");
                view.forward(req, resp);
            }
            if(ordini!=null && ordini.equals("ordini"))
            {
                req.setAttribute(BEANUB, uB);
                view = getServletContext().getRequestDispatcher("/ordini.jsp");
                view.forward(req, resp);
            }
            if(gestione!=null && gestione.equals("gestione"))
            {
                req.setAttribute(BEANUB, uB);
                view = getServletContext().getRequestDispatcher("/profiloSE.jsp");
                view.forward(req, resp);
            }
            if(logout!=null && logout.equals("logout"))
            {
                uB.setEmailB("");
                uB.setPassB("");
                req.setAttribute(BEANUB, uB);
                view = getServletContext().getRequestDispatcher("/login.jsp");
                view.forward(req, resp);
            }
        }catch (IOException|ServletException e)
        {
            Logger.getLogger("do post").log(Level.SEVERE,"exception has occurred !!.",e);
        }
    }
}
