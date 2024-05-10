package web.servlet;

import java.io.IOException;

import java.io.Serial;
import java.util.logging.Level;


import laptop.exception.LogoutException;

import web.bean.SystemBean;
import web.bean.UserBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UtentiServlet")
public class UtentiServlet extends HttpServlet {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    private static final UserBean uB=UserBean.getInstance();
    private static final SystemBean sB=SystemBean.getInstance();
    private static final String BEANS="beanS";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String profilo = req.getParameter("buttonProfilo");
        String logout = req.getParameter("buttonLogout");
        String libro = req.getParameter("buttonL");
        String giornale = req.getParameter("buttonG");
        String rivista = req.getParameter("buttonR");
        String ricerca = req.getParameter("buttonRic");


        RequestDispatcher view;
        try {

            if (profilo != null && profilo.equals("profilo")) {
                req.setAttribute("beanUb", uB);
                view = getServletContext().getRequestDispatcher("/profilo.jsp");
                view.forward(req, resp);
            }

            if (libro != null && libro.equals("libri")) {

                req.setAttribute(BEANS, sB);

                view = getServletContext().getRequestDispatcher("/libri.jsp");
                view.forward(req, resp);
            }
            if (giornale != null && giornale.equals("giornali")) {
                req.setAttribute(BEANS, sB);
                view = getServletContext().getRequestDispatcher("/giornali.jsp");
                view.forward(req, resp);
            }
            if (rivista != null && rivista.equals("riviste")) {
                req.setAttribute(BEANS, sB);
                view = getServletContext().getRequestDispatcher("/riviste.jsp");
                view.forward(req, resp);
            }
            if (logout != null && logout.equals("logout")) {

               logoutUser(req,resp);

            }
            if (ricerca != null && ricerca.equals("ricerca")) {
                view = getServletContext().getRequestDispatcher("/ricerca.jsp");
                view.forward(req, resp);
            }

        } catch (LogoutException e) {
            java.util.logging.Logger.getLogger("post ").log(Level.INFO, "eccezione nel post {0}.", e.toString());

        }
    }
    private void logoutUser(HttpServletRequest req,HttpServletResponse resp) throws LogoutException, ServletException, IOException {
        RequestDispatcher view;
        String n = uB.getNomeB();
        java.util.logging.Logger.getLogger("Test logout").log(Level.INFO, "stai sloggando come {0}", n);

        if (n == null) {
            throw new LogoutException("Errore Logout");

        } else {
            uB.setIdB(-1);
            uB.setNomeB(null);
            uB.setCognomeB(null);
            uB.setDataDiNascitaB(null);
            uB.setDescrizioneB(null);
            uB.setEmailB("");
            uB.setPassB("");


            java.util.logging.Logger.getLogger("Test Eccezione").log(Level.INFO, "stai sloggando {0}", uB.getEmailB());
            SystemBean.getInstance().setLoggedB(false);

            view = getServletContext().getRequestDispatcher("/index.jsp");
            view.forward(req, resp);
        }
    }


    }