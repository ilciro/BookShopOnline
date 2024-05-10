package web.servlet;

import java.io.IOException;
import java.io.Serial;

import java.util.logging.Level;

import laptop.database.LibroDao;
import laptop.model.raccolta.Libro;
import web.bean.AcquistaBean;
import web.bean.LibroBean;
import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LibriServlet")
public class LibriServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final LibroBean lB = new LibroBean();
    private static final String LIBRI= "/libri.jsp";
    private final LibroDao lD = new LibroDao();
    private static final Libro l = new Libro();
    private static final String BEANL = "beanL";
    private static final SystemBean sB=SystemBean.getInstance();
    private static final AcquistaBean aB=new AcquistaBean();

    public LibriServlet() throws IOException {
        java.util.logging.Logger.getLogger("initialize").log(Level.INFO, "costruttore");

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String i = req.getParameter("procedi");
        String g = req.getParameter("genera lista");
        String a = req.getParameter("annulla");
        String id = req.getParameter("idOgg");
        RequestDispatcher view;
        if (g != null && g.equals("genera lista")) {

           lB.setElencoLibriB(lD.getLibri());
           req.setAttribute(BEANL,lB);
           req.setAttribute("beanS",sB);
            view= getServletContext().getRequestDispatcher(LIBRI);
           view.forward(req,resp);

        }
        if(i!=null && i.equals("procedi"))
        {
            //cast
            int idOgg=Integer.parseInt(id);
            sB.setIdB(idOgg);

                lB.setIdB(idOgg);
                l.setId(lB.getIdB());

                lB.setTitoloB(lD.getData(l).getTitolo());
                sB.setTitoloB(lB.getTitoloB());
                sB.setIdB(lB.getIdB());
                //aggiungo categoria
                sB.setCategoriaB(lD.getData(l).getCategoria());
                sB.setTypeB("libro");

                //setto i parametri nel bean acquista
                aB.setTitoloB(lB.getTitoloB());
                aB.setPrezzoB(lD.getData(l).getPrezzo());

                req.setAttribute("beanS", sB);
                req.setAttribute(BEANL, lB);
                req.setAttribute("beanA",aB);
                view = getServletContext().getRequestDispatcher("/acquista.jsp");
                view.forward(req, resp);


        }
        if(a!=null && a.equals("home"))
        {
             view= getServletContext().getRequestDispatcher("/index.jsp");
            view.forward(req,resp);
        }

    }
}