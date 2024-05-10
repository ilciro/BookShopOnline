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

@WebServlet("/AdminServlet")

public class AdminServlet extends HttpServlet {

    private static final UserBean uB=UserBean.getInstance();
    private static final SystemBean sB=SystemBean.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String report = req.getParameter("reportB");
    String raccolta = req.getParameter("raccoltaB");
    String utente = req.getParameter("utentiB");
    String logout = req.getParameter("logoutB");
        RequestDispatcher view;
		try {
        if (report != null && report.equals("report")) {
             view = getServletContext().getRequestDispatcher("/report.jsp");
            view.forward(req, resp);
        }
        if (raccolta != null && raccolta.equals("raccolta")) {
             view = getServletContext().getRequestDispatcher("/raccolta.jsp");
            view.forward(req, resp);
        }
        if (utente != null && utente.equals("utenti")) {

            req.setAttribute("beanUb",uB);

            view = getServletContext().getRequestDispatcher("/utenti.jsp");
            view.forward(req, resp);
        }
        if (logout != null && logout.equals("logout")) {
            String email = uB.getEmailB();

            if (email == null) {
                 view = getServletContext().getRequestDispatcher("/admin.jsp");
                view.forward(req, resp);
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
                sB.setLoggedB(false);
                 view = getServletContext().getRequestDispatcher("/index.jsp");
                view.forward(req, resp);

            }

        }


    } catch (LogoutException | ServletException | IOException e) {
        java.util.logging.Logger.getLogger("post ").log(Level.INFO, "eccezione nel post .", e);

    }


    }
}
