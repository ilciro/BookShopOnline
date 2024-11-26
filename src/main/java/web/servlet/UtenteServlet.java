package web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.bean.SystemBean;
import web.bean.UserBean;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/UtenteServlet")
public class UtenteServlet extends HttpServlet {
    private static final SystemBean sB=SystemBean.getInstance();
    private static final String BEANS="beanS";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String libri=req.getParameter("libri");
        String giornali=req.getParameter("giornali");
        String riviste=req.getParameter("riviste");
        String ricerca=req.getParameter("cerca");
        String ordini=req.getParameter("ordini");
        String indietro=req.getParameter("indietro");
        RequestDispatcher view;
        try {
            if (libri != null && libri.equals("libri")) {
                req.setAttribute(BEANS, sB);
                view = getServletContext().getRequestDispatcher("/libri.jsp");
                view.forward(req, resp);
             }
            if (giornali != null && giornali.equals("giornali")) {
                req.setAttribute(BEANS, sB);
                view = getServletContext().getRequestDispatcher("/giornali.jsp");
                view.forward(req, resp);
            }
            if (riviste != null && riviste.equals("riviste")) {
                req.setAttribute(BEANS, sB);
                view = getServletContext().getRequestDispatcher("/riviste.jsp");
                view.forward(req, resp);
            }
            if (ricerca != null && ricerca.equals("cerca")) {
                req.setAttribute(BEANS, sB);
                view = getServletContext().getRequestDispatcher("/ricerca.jsp");
                view.forward(req, resp);
            }
            if (ordini != null && ordini.equals("ordini")) {
                req.setAttribute(BEANS, sB);
                view = getServletContext().getRequestDispatcher("/ordini.jsp");
                view.forward(req, resp);
            }
            if (indietro != null && indietro.equals("logout")) {

                sB.setLoggedB(false);
                UserBean.getInstance().setEmailB("");
                UserBean.getInstance().setPassB("");
                req.setAttribute(BEANS, sB);
                req.setAttribute("beanUb",  UserBean.getInstance());
                view = getServletContext().getRequestDispatcher("/login.jsp");
                view.forward(req, resp);
            }
        }catch (ServletException|IOException e)
        {
            Logger.getLogger("do post").log(Level.SEVERE, " exception has occurred :.",e);
        }



    }
}
