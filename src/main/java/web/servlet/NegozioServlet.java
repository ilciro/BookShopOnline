package web.servlet;

import java.io.IOException;

import java.io.Serial;
import java.sql.SQLException;
import java.util.logging.Level;

import web.bean.NegozioBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.database.NegozioDao;
import laptop.model.Negozio;

@WebServlet("/NegozioServlet")
public class NegozioServlet extends HttpServlet {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    private static final NegozioBean nB = new NegozioBean();
    private static final Negozio n = new Negozio();
    private static final String INDEX = "/index.jsp";
    private static final String NEGOZI = "/negozi.jsp";
    private static final String NEGOZIOCHIUSO = "negozio non disponibile e/o chiuso";
    private static final String BEANN = "beanN";
    private static final NegozioDao nD = new NegozioDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String neg1 = req.getParameter("buttonNeg1");
        String neg2 = req.getParameter("buttonNeg2");
        String neg3 = req.getParameter("buttonNeg3");
        String neg4 = req.getParameter("buttonNeg4");


        try {
            if (neg1 != null && neg1.equals("Negozio A")) {
                negozio1(req, resp);

            }
            if (neg2 != null && neg2.equals("Negozio B")) {

                negozio2(req,resp);
            }
            if (neg3 != null && neg3.equals("Negozio C")) {

                negozio3(req,resp);
            }
            if (neg4 != null && neg4.equals("Negozio D")) {

                negozio4(req,resp);
            }

        } catch (SQLException e) {
            java.util.logging.Logger.getLogger("post ").log(Level.INFO, "eccezione nel post {0}.", e.getMessage());

        }

    }

    private void negozio1(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        RequestDispatcher view;
        nB.setNomeB("Negozio A");
        n.setNome(nB.getNomeB());
        n.setIsOpen(nD.checkOpen(n));
        n.setIsValid(nD.checkRitiro(n));
        nB.setValidB(n.getIsValid());
        nB.setOpenB(n.getIsOpen());

        if (nB.isOpenB() && nB.isValidB()) {
            view = getServletContext().getRequestDispatcher(INDEX);
            view.forward(req, resp);
        } else {
            nB.setMexB(NEGOZIOCHIUSO);
            req.setAttribute(BEANN, nB);
            view = getServletContext().getRequestDispatcher(NEGOZI);
            view.forward(req, resp);

        }
    }

    private void negozio2(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        RequestDispatcher view;

        nB.setNomeB("Negozio B");
        n.setNome(nB.getNomeB());
        n.setIsOpen(nD.checkOpen(n));
        n.setIsValid(nD.checkRitiro(n));
        nB.setValidB(n.getIsValid());
        nB.setOpenB(n.getIsOpen());

        if (nB.isOpenB() && nB.isValidB()) {
            view = getServletContext().getRequestDispatcher(INDEX);
            view.forward(req, resp);
        } else {
            nB.setMexB(NEGOZIOCHIUSO);
            req.setAttribute(BEANN, nB);
            view = getServletContext().getRequestDispatcher(NEGOZI);
            view.forward(req, resp);

        }

    }
    private void negozio3(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException, SQLException {
        RequestDispatcher view;
        nB.setNomeB("Negozio C");
        n.setNome(nB.getNomeB());
        n.setIsOpen(nD.checkOpen(n));
        n.setIsValid(nD.checkRitiro(n));
        nB.setValidB(n.getIsValid());
        nB.setOpenB(n.getIsOpen());

        if (nB.isOpenB() && nB.isValidB()) {
            view = getServletContext().getRequestDispatcher(INDEX);
            view.forward(req, resp);
        } else {
            nB.setMexB(NEGOZIOCHIUSO);
            req.setAttribute(BEANN, nB);
            view = getServletContext().getRequestDispatcher(NEGOZI);
            view.forward(req, resp);

        }

    }
    private void negozio4(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException, SQLException {
        RequestDispatcher view;
        nB.setNomeB("Negozio D");
        n.setNome(nB.getNomeB());
        n.setIsOpen(nD.checkOpen(n));
        n.setIsValid(nD.checkRitiro(n));
        nB.setValidB(n.getIsValid());
        nB.setOpenB(n.getIsOpen());

        if (nB.isOpenB() && nB.isValidB()) {
            view = getServletContext().getRequestDispatcher(INDEX);
            view.forward(req, resp);
        } else {
            nB.setMexB(NEGOZIOCHIUSO);
            req.setAttribute(BEANN, nB);
            view = getServletContext().getRequestDispatcher(NEGOZI);
            view.forward(req, resp);

        }
    }



}