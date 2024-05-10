package web.servlet;

import java.io.IOException;
import java.io.Serial;

import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RicercaServlet")
public class RicercaServlet extends HttpServlet {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String BEANS="beanS";
    private static final String RICERCAINCATALOGO="/ricercaInCatalogo.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String buttonL=req.getParameter("buttonL");
        String buttonG=req.getParameter("buttonG");
        String buttonR=req.getParameter("buttonR");
        String buttonI=req.getParameter("buttonI");
        if(buttonL!=null && buttonL.equals("libri"))
        {
            SystemBean.getInstance().setTypeAsBook();
            req.setAttribute(BEANS,SystemBean.getInstance());
            RequestDispatcher view=getServletContext().getRequestDispatcher(RICERCAINCATALOGO);
            view.forward(req, resp);
        }
        if(buttonG!=null && buttonG.equals("giornali"))
        {
            SystemBean.getInstance().setTypeAsDaily();
            req.setAttribute(BEANS,SystemBean.getInstance());
            RequestDispatcher view=getServletContext().getRequestDispatcher(RICERCAINCATALOGO);
            view.forward(req, resp);
        }
        if(buttonR!=null && buttonR.equals("riviste"))
        {
            SystemBean.getInstance().setTypeAsMagazine();
            req.setAttribute(BEANS,SystemBean.getInstance());
            RequestDispatcher view=getServletContext().getRequestDispatcher(RICERCAINCATALOGO);
            view.forward(req, resp);
        }
        if(buttonI!=null && buttonI.equals("indietro"))
        {
            if(SystemBean.getInstance().isLoggedB())
            {
                RequestDispatcher view=getServletContext().getRequestDispatcher("/scrittore.jsp");
                view.forward(req, resp);
            }
            else {
                RequestDispatcher view=getServletContext().getRequestDispatcher("/index.jsp");
                view.forward(req, resp);
            }
        }

    }


}