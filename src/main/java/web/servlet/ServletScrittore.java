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

@WebServlet("/ServletScrittore")
public class ServletScrittore extends HttpServlet {
    private static final UserBean uB=UserBean.getInstance();
    private static final SystemBean sB=SystemBean.getInstance();
    private static final String BEANS="beanS";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String buttonL=req.getParameter("buttonL");
        String buttonG=req.getParameter("buttonG");
        String buttonR=req.getParameter("buttonR");
        String buttonGestione=req.getParameter("buttonGestione");
        String buttonRic=req.getParameter("buttonRic");
        String logoutB=req.getParameter("logoutB");
        String profiloB=req.getParameter("profiloB");

        RequestDispatcher view;
        try {
            if(buttonL!=null && buttonL.equals("libri"))
            {
                sB.setTypeB("libro");
                sB.setLoggedB(true);
                req.setAttribute(BEANS,sB);
                 view=getServletContext().getRequestDispatcher("/libri.jsp");
                view.forward(req, resp);
            }
            if(buttonG!=null && buttonG.equals("giornali"))
            {
                sB.setTypeB("giornale");
                sB.setLoggedB(true);

                req.setAttribute(BEANS,sB);
                 view=getServletContext().getRequestDispatcher("/giornali.jsp");
                view.forward(req, resp);
            }
            if(buttonR!=null && buttonR.equals("riviste"))
            {
                sB.setTypeB("rivista");
                sB.setLoggedB(true);

                req.setAttribute(BEANS,sB);
                 view=getServletContext().getRequestDispatcher("/riviste.jsp");
                view.forward(req, resp);
            }
            if(buttonGestione!=null && buttonGestione.equals("gestione"))
            {
                view=getServletContext().getRequestDispatcher("/raccolta.jsp");
                view.forward(req, resp);

            }
            if(buttonRic!=null && buttonRic.equals("ricerca"))
            {
                 view=getServletContext().getRequestDispatcher("/ricerca.jsp");
                view.forward(req, resp);
            }

            if(logoutB!=null && logoutB.equals("logout") )
            {
                 view=getServletContext().getRequestDispatcher("/index.jsp");
                view.forward(req, resp);
            }

            if(profiloB!=null && profiloB.equals("vai al profilo"))
            {
                uB.setRuoloB("W");
                req.setAttribute("beanUb",uB);
                 view=getServletContext().getRequestDispatcher("/profilo.jsp");
                view.forward(req, resp);
            }


        } catch ( ServletException | IOException  e) {
            java.util.logging.Logger.getLogger("post ").log(Level.INFO, "eccezione nel post .",e);



        }

    }

   }
