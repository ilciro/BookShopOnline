package web.servlet;

import java.io.IOException;
import java.io.Serial;
import java.util.logging.Level;

import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import web.bean.RicercaBean;
import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RicercaCatalogoServlet")
public class RicercaCatalogoServlet extends HttpServlet {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    private final RicercaBean rB=new RicercaBean();
    private final LibroDao lD=new LibroDao();
    private final RivistaDao rD=new RivistaDao();
    private final GiornaleDao gD=new GiornaleDao();
    private static final String LIBRO="libro";
    private static final String GIORNALE= "giornale";
    private static final String RIVISTA="rivista";
    private static final Libro l=new Libro();
    private static final Giornale g=new Giornale();

    private static final Rivista r=new Rivista();

    public RicercaCatalogoServlet() throws IOException {
        java.util.logging.Logger.getLogger("initialize ").log(Level.INFO, "costruttore");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String titolo=req.getParameter("cercaL");
        String cercaB=req.getParameter("cercaB");
        String visualizza=req.getParameter("visualizzaB");
        String indietro=req.getParameter("buttonI");
        String type=SystemBean.getInstance().getTypeB();

        if(cercaB!=null && cercaB.equals("cerca"))
        {
            switch(type)
            {
                case LIBRO-> {
                    rB.setTitoloB(titolo);
                    rB.setAutoreB(titolo);
                    l.setAutore(rB.getAutoreB());
                    l.setTitolo(rB.getTitoloB());
                    rB.setListaB(lD.getLibriIdTitoloAutore(l));
                }
                case GIORNALE-> {
                    rB.setTitoloB(titolo);
                    rB.setEditoreB(titolo);
                    g.setTitolo(rB.getTitoloB());
                    g.setEditore(rB.getEditoreB());
                    rB.setListaB(gD.getGiornaliIdTitoloAutore(g));
                }
                case RIVISTA-> {
                    rB.setTitoloB(titolo);
                    rB.setAutoreB(titolo);
                    r.setAutore(rB.getAutoreB());
                    r.setTitolo(rB.getTitoloB());
                    rB.setListaB(rD.getRivisteIdTitoloAutore(r));
                }
                default->java.util.logging.Logger.getLogger("cerca").log(Level.SEVERE, "find error");


            }
            req.setAttribute("beanRicerca",rB);
            req.setAttribute("beanS",SystemBean.getInstance());
            RequestDispatcher view=getServletContext().getRequestDispatcher("/ricercaInCatalogo.jsp");
            view.forward(req, resp);
        }
        if(indietro!=null && indietro.equals("indietro"))
        {
            RequestDispatcher view=getServletContext().getRequestDispatcher("/ricerca.jsp");
            view.forward(req, resp);
        }
        if(visualizza!=null && visualizza.equals("visualizza"))
        {
            RequestDispatcher view;
            switch(type)
            {
                case LIBRO-> {
                    view = getServletContext().getRequestDispatcher("/libri.jsp");
                    view.forward(req, resp);
                }
                case GIORNALE-> {
                    view = getServletContext().getRequestDispatcher("/giornali.jsp");
                    view.forward(req, resp);
                }
                case RIVISTA-> {
                    view = getServletContext().getRequestDispatcher("/riviste.jsp");
                    view.forward(req, resp);
                }
                default->java.util.logging.Logger.getLogger("visualizza").log(Level.SEVERE, "show error");


            }
        }


    }


}