package web.servlet;

import java.io.IOException;
import java.io.Serial;
import java.util.logging.Level;

import laptop.database.GiornaleDao;
import web.bean.AcquistaBean;
import web.bean.GiornaleBean;
import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.model.raccolta.Giornale;


@WebServlet("/GiornaliServlet")
public class GiornaliServlet extends HttpServlet{

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    private static final GiornaleBean gB=new GiornaleBean();
    private static final GiornaleDao gD=new GiornaleDao();
    private static final Giornale giornale=new Giornale();
    private static final SystemBean sB=SystemBean.getInstance();
    private static final AcquistaBean aB=new AcquistaBean();

    public GiornaliServlet() throws IOException {
        java.util.logging.Logger.getLogger("Test costruttore").log(Level.INFO,"costruttore");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String i=req.getParameter("procedi");
        String g=req.getParameter("genera lista");
        String a=req.getParameter("annulla");
        String id=req.getParameter("idOgg");
        RequestDispatcher view;

        if(g!=null && g.equals("genera lista"))
        {

            gB.setListaGiornaliB(gD.getGiornali());
            req.setAttribute("beanG",gB);
            String giornali = "/giornali.jsp";
            view = getServletContext().getRequestDispatcher(giornali);
            view.forward(req,resp);


        }
        if(i!=null && i.equals("procedi"))
        {
            int idOgg=Integer.parseInt(id);
            sB.setIdB(idOgg);
            gB.setIdB(idOgg);
            giornale.setId(gB.getIdB());
            gB.setTitoloB(gD.getData(giornale).getTitolo());
            sB.setTitoloB(gB.getTitoloB());
            sB.setIdB(gB.getIdB());
            sB.setTypeB("giornale");

            //aggiungo categoria
            sB.setCategoriaB(gD.getData(giornale).getTipologia());

            //setto i parametri nel bean acquista
            aB.setTitoloB(gB.getTitoloB());
            aB.setPrezzoB(gD.getData(giornale).getPrezzo());

            req.setAttribute("beanS", sB);
            req.setAttribute("beanG", gB);
            req.setAttribute("beanA",aB);
            view = getServletContext().getRequestDispatcher("/acquista.jsp");
            view.forward(req, resp);
            }


        if(a!=null && a.equals("indietro"))
        {
             view = getServletContext().getRequestDispatcher("/index.jsp");
            view.forward(req,resp);
        }


    }






}