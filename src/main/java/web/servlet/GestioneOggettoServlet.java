package web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import web.bean.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

@WebServlet("/GestioneOggettoServlet")
public class GestioneOggettoServlet extends HttpServlet {
    private static final SystemBean sB=SystemBean.getInstance();
    private static final ModificaOggettoBean mOB=new ModificaOggettoBean();

    private static final Libro l=new Libro();
    private final LibroDao lD=new LibroDao();
    private static final GiornaleDao gD=new GiornaleDao();
    private static final Giornale g=new Giornale();
    private static final Rivista r=new Rivista();
    private final RivistaDao rD=new RivistaDao();
    private static final LibroBean lB=new LibroBean();
    private static final RivistaBean rB=new RivistaBean();
    private static final GiornaleBean gB=new GiornaleBean();
    private static final String LIBRO="libro";
    private static final String GIORNALE="giornale";
    private static final String RIVISTA="rivista";
    private static final String BEANMOB="beanMOB";
    private static final String MODIFICA="/modificaOggettoPage.jsp";
    private static final String BEANS="beanS";
    private static final String GESTIONE="/gestioneOggettoPage.jsp";

    public GestioneOggettoServlet() throws IOException {
        java.util.logging.Logger.getLogger("Test Costruttore").log(Level.INFO,"costruttore");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String genera=req.getParameter("buttonGenera");
        String aggiungi=req.getParameter("buttonAdd");
        String id=req.getParameter("idL");
        String modifica=req.getParameter("buttonMod");
        String cancella=req.getParameter("buttonCanc");
        String indietro=req.getParameter("buttonI");
        RequestDispatcher view;

            if(genera!=null && genera.equals("genera lista"))
            {

                genera(req);
                req.setAttribute(BEANS,sB);
                view= getServletContext().getRequestDispatcher(GESTIONE);
                view.forward(req,resp);
            }
            if(aggiungi!=null && aggiungi.equals("inserisci"))
            {
                req.setAttribute(BEANS,sB);
                view= getServletContext().getRequestDispatcher("/aggiungiOggettoPage.jsp");
                view.forward(req,resp);
            }
            if(modifica!=null && modifica.equals("modifica"))
            {
               modifica(req);
                view= getServletContext().getRequestDispatcher(MODIFICA);
                view.forward(req,resp);


            }
            if(cancella!=null && cancella.equals("cancella"))
            {
                try {
                    cancella(req,resp,id);
                } catch (SQLException e) {
                    java.util.logging.Logger.getLogger("Test cancella").log(Level.SEVERE, " eccezione ottenuta {0}.", e.toString());

                }


            }
            if(indietro!=null && indietro.equals("indietro"))
            {
                view= getServletContext().getRequestDispatcher("/raccolta.jsp");
                view.forward(req,resp);
            }



    }
    private void genera(HttpServletRequest req) {
        switch (sB.getTypeB()) {
            case LIBRO -> {
                mOB.setMiaListaB(lD.getLibri());
                req.setAttribute(BEANMOB, mOB);
            }
            case GIORNALE -> {

                mOB.setMiaListaB(gD.getGiornali());
                req.setAttribute(BEANS, sB);
                req.setAttribute("beanG", gB);
                req.setAttribute(BEANMOB, mOB);
            }
            case RIVISTA -> {
                mOB.setMiaListaB(rD.getRiviste());
                req.setAttribute(BEANS, sB);
                req.setAttribute("beanR", rB);
                req.setAttribute(BEANMOB, mOB);
            }
            default -> java.util.logging.Logger.getLogger("genera lista").log(Level.SEVERE, "choice not correct");

        }
    }
    private void modifica(HttpServletRequest req)
    {
        switch (sB.getTypeB())
        {
            case LIBRO-> {

                sB.setIdB(lD.getIdMax());
                lB.setIdB(lD.getIdMax());
                l.setId(lB.getIdB());

                req.setAttribute(BEANS, sB);
                req.setAttribute("beanL", lB);
            }
            case GIORNALE -> {

                sB.setIdB(gD.getIdMax());
                gB.setIdB(gD.getIdMax());
                g.setId(gB.getIdB());
                req.setAttribute(BEANS, sB);
                req.setAttribute("beanG", gB);
            }
            case RIVISTA -> {
                sB.setIdB(rD.getIdMax());
                rB.setIdB(rD.getIdMax());
                r.setId(sB.getIdB());
                req.setAttribute(BEANS, sB);
                req.setAttribute("beanR", rB);
            }
            default->	java.util.logging.Logger.getLogger("modifica").log(Level.SEVERE, "modif error");

        }
    }
    private void cancella(HttpServletRequest req, HttpServletResponse resp, String id) throws SQLException, ServletException, IOException {
        RequestDispatcher view;
        switch (sB.getTypeB())
        {
            case LIBRO-> {
                int idL = Integer.parseInt(id);
                sB.setIdB(idL);
                lB.setIdB(sB.getIdB());
                l.setId(sB.getIdB());
                if (lD.cancella(l) == 1) {
                    req.setAttribute(BEANS,sB);
                    view = getServletContext().getRequestDispatcher(GESTIONE);
                    view.forward(req, resp);
                } else {
                    view = getServletContext().getRequestDispatcher(MODIFICA);
                    view.forward(req, resp);
                }
            }
            case GIORNALE -> {

                int idG = Integer.parseInt(id);
                sB.setIdB(idG);
                gB.setIdB(sB.getIdB());
                g.setId(sB.getIdB());
                if (gD.cancella(g) == 1) {
                    req.setAttribute(BEANS,sB);
                    view = getServletContext().getRequestDispatcher(GESTIONE);
                    view.forward(req, resp);
                } else {
                    view = getServletContext().getRequestDispatcher(MODIFICA);
                    view.forward(req, resp);
                }
            }
            case RIVISTA -> {
                int idR = Integer.parseInt(id);
                sB.setIdB(idR);
                rB.setIdB(sB.getIdB());
                r.setId(sB.getIdB());
                if (rD.cancella(r) == 1) {
                    req.setAttribute(BEANS,sB);
                    view = getServletContext().getRequestDispatcher(GESTIONE);
                    view.forward(req, resp);
                } else {
                    view = getServletContext().getRequestDispatcher(MODIFICA);
                    view.forward(req, resp);
                }
            }
            default->java.util.logging.Logger.getLogger("cancella").log(Level.SEVERE, "delete error");

        }
    }


}
