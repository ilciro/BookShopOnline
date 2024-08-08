package web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.exception.LogoutException;
import web.bean.SystemBean;
import web.bean.UserBean;

import java.io.IOException;
import java.util.logging.Level;

@WebServlet("/RaccoltaServlet")
public class RaccoltaServlet extends HttpServlet {

    private static final String BEANS="beanS";
    private static final UserBean uB=UserBean.getInstance();

    private static final SystemBean sB=SystemBean.getInstance();
    private static final String GESTIONE="/gestioneOggettoPage.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String libro=req.getParameter("buttonL");
        String giornale=req.getParameter("buttonG");
        String rivista=req.getParameter("buttonR");
        String logout=req.getParameter("buttonLog");
        RequestDispatcher view;
        try {
            if(libro!=null && libro.equals("libri"))
            {
                sB.setTypeAsBook();
                sB.setTypeB("libro");
                req.setAttribute(BEANS,sB);
                view = getServletContext().getRequestDispatcher(GESTIONE);
                view.forward(req,resp);
            }

            if(giornale!=null && giornale.equals("giornali") )
            {
               sB.setTypeAsDaily();
               sB.setTypeB("giornale");
                req.setAttribute(BEANS,sB);
                 view = getServletContext().getRequestDispatcher(GESTIONE);
                view.forward(req,resp);
            }

            if(rivista!=null && rivista.equals("riviste"))
            {
                sB.setTypeAsMagazine();
                sB.setTypeB("rivista");
                req.setAttribute(BEANS,sB);
                 view = getServletContext().getRequestDispatcher(GESTIONE);
                view.forward(req,resp);
            }


            if(logout!=null && logout.equals("logout"))
            {

                uB.setIdB(-1);
                uB.setNomeB(null);
                uB.setCognomeB(null);
                uB.setDataDiNascitaB(null);
                uB.setDescrizioneB(null);
                uB.setEmailB("");
                uB.setPassB("");


                java.util.logging.Logger.getLogger("Test Eccezione").log(Level.INFO, "stai sloggando {0}", UserBean.getInstance().getEmailB());
                SystemBean.getInstance().setLoggedB(false);

                if(!sB.isLoggedB())
                {
                     view = getServletContext().getRequestDispatcher("/index.jsp");
                    view.forward(req,resp);
                }
                else {
                    view = getServletContext().getRequestDispatcher("/raccolta.jsp");
                    view.forward(req,resp);
                    throw new LogoutException("user logged yet");

                }

            }
        } catch (LogoutException e) {
            java.util.logging.Logger.getLogger("post ").log(Level.INFO, "eccezione nel post {0}.",e.toString());

        }


    }



}
