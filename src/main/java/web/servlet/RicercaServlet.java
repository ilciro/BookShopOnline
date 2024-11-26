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
import laptop.database.UsersDao;
import laptop.model.User;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import web.bean.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/RicercaServlet")

public class RicercaServlet extends HttpServlet {
    private static final SystemBean sB=SystemBean.getInstance();

    private static final LibroDao lD=new LibroDao();
    private static final GiornaleDao gD=new GiornaleDao();
    private static final RivistaDao rD=new RivistaDao();
    private static final RicercaBean rB=new RicercaBean();
    private static final String BEANS="beanS";
    private static final String BEANRIC="beanRic";
    private static final String RICERCA="/ricerca.jsp";
    private static final UserBean uB=UserBean.getInstance();
    private static final String INDIETRO="indietro";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ricerca=req.getParameter("ricerca");
        String tipoR=req.getParameter("tipoR");
        String cerca=req.getParameter("cerca");
        String indietro=req.getParameter(INDIETRO);


        try {
            if (cerca != null && cerca.equals("genera lista")) {
                rB.setMexB(null);
                switch (tipoR) {
                    case "libro" -> listaLibri(ricerca,req,resp);

                    case "giornale"-> listaGiornali(ricerca,req,resp);

                    case "rivista"-> listaRiviste(ricerca,req,resp);

                    default -> Logger.getLogger(" do post type").log(Level.SEVERE, " type is incorrect !!");

                }
            }
            if(indietro!=null && indietro.equals(INDIETRO))
            {
                RequestDispatcher view;
                User u= User.getInstance();
                u.setEmail(uB.getEmailB());

                String ruolo=UsersDao.pickData(u).getIdRuolo();
                switch (ruolo)
                {
                    case "ADMIN","A"->{
                       view = getServletContext().getRequestDispatcher("/admin.jsp");
                        view.forward(req,resp);
                    }
                    case "SCRITTORE","EDITORE","W","S","E"->
                    {
                        view = getServletContext().getRequestDispatcher("/scrittoreEditore.jsp");
                        view.forward(req, resp);
                    }
                    case "UTENTE","U"->
                    {
                        view = getServletContext().getRequestDispatcher("/utente.jsp");
                        view.forward(req, resp);
                    }
                    default -> Logger.getLogger(INDIETRO).log(Level.SEVERE," type of user not correct !!");
                }

            }
        }catch (IOException|ServletException |SQLException e)
        {
            Logger.getLogger(" do post").log(Level.SEVERE,"exception .",e);
        }

    }

    private void listaRiviste(String ricerca, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        sB.setTypeAsMagazine();
        Rivista r=new Rivista();
        r.setTitolo(ricerca);
        r.setAutore(ricerca);
        r.setEditore(ricerca);
        rB.setListaRB(rD.getRivistaIdTitoloAutore(r));
        if(rB.getListaRB().isEmpty())
            rB.setMexB(new SQLException(" list of magazine is empty !!"));
        req.setAttribute(BEANS, sB);
        req.setAttribute(BEANRIC, rB);
        RequestDispatcher view = getServletContext().getRequestDispatcher(RICERCA);
        view.forward(req, resp);
    }

    private void listaLibri(String ricerca,HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        sB.setTypeAsBook();
        Libro l = new Libro();
        LibroBean lB=new LibroBean();
        lB.setTitoloB(ricerca);
        lB.setAutoreB(ricerca);
        lB.setEditoreB(ricerca);
        l.setTitolo(lB.getTitoloB());
        l.setAutore(lB.getAutoreB());
        l.setEditore(lB.getEditoreB());
        rB.setListaLB(lD.getLibroByIdTitoloAutoreLibro(l));
        if (rB.getListaLB().isEmpty())
            rB.setMexB(new SQLException(" list of book is empty !!"));
        req.setAttribute(BEANS, sB);
        req.setAttribute(BEANRIC, rB);
        RequestDispatcher view= getServletContext().getRequestDispatcher(RICERCA);
        view.forward(req, resp);
    }
    private void listaGiornali(String ricerca,HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        sB.setTypeAsDaily();
        Giornale g=new Giornale();
        g.setTitolo(ricerca);
        g.setEditore(ricerca);
        rB.setListaGB(gD.getGiornaleIdTitoloAutore(g));
        if(rB.getListaGB().isEmpty())
            rB.setMexB(new SQLException(" list of daily is empty !!"));
        req.setAttribute(BEANS, sB);
        req.setAttribute(BEANRIC, rB);
        RequestDispatcher view = getServletContext().getRequestDispatcher(RICERCA);
        view.forward(req, resp);
    }
}
