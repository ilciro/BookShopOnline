package web.servlet;

import java.io.IOException;
import java.io.Serial;
import java.util.logging.Level;

import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import web.bean.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AcquistaServlet")
public class AcquistaServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    private final AcquistaBean aB=new AcquistaBean();
    private final LibroDao lD=new LibroDao();
    private final Libro l=new Libro();
    private final LibroBean lB=new LibroBean();

    private static final Rivista r=new Rivista();
    private final RivistaDao rD=new RivistaDao();
    private static final RivistaBean rB=new RivistaBean();

    private static final   String LIBRO = "libro";
    private static final String BEANS="beanS";
    private static final String BEANA="beanA";
    private  static final String GIORNALE="giornale";
    private  static final String RIVISTA="rivista";
    private static final Giornale g=new Giornale();
    private static final GiornaleDao gD=new GiornaleDao();
    private static final GiornaleBean gB=new GiornaleBean();
    private static final SystemBean sB=SystemBean.getInstance();

    public AcquistaServlet() throws IOException {
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q=req.getParameter("quantita");
        String calcola=req.getParameter("totaleB");
        String metodo=req.getParameter("metodoP");
        String negozio=req.getParameter("negozioB");
        sB.setMetodoPB(metodo);
        String download=req.getParameter("pdfB");
        float costo;
        String type=sB.getTypeB();
        String pagamento=sB.getMetodoPB();
        try {

            if(calcola!=null && calcola.equals("calcola"))
            {

                switch (type)
                {
                    case LIBRO -> {
                        lB.setIdB(sB.getIdB());
                        l.setId(lB.getIdB());
                        aB.setTitoloB(lD.getData(l).getTitolo());
                        costo = Integer.parseInt(q) * lD.getData(l).getPrezzo();
                        aB.setPrezzoB(costo);
                        sB.setQuantitaB(Integer.parseInt(q));
                        sB.setSpesaTB(aB.getPrezzoB());
                        sB.setTitoloB(aB.getTitoloB());
                        sB.setIdB(lB.getIdB());
                        req.setAttribute(BEANS,sB);
                        req.setAttribute(BEANA,aB);
                    }
                    case GIORNALE -> {
                        gB.setIdB(sB.getIdB());
                        g.setId(gB.getIdB());
                        aB.setTitoloB(gD.getData(g).getTitolo());
                        costo = Integer.parseInt(q) * gD.getData(g).getPrezzo();
                        aB.setPrezzoB(costo);
                        sB.setQuantitaB(Integer.parseInt(q));
                        sB.setSpesaTB(aB.getPrezzoB());
                        sB.setTitoloB(aB.getTitoloB());
                        sB.setIdB(gB.getIdB());
                        req.setAttribute(BEANS,sB);
                        req.setAttribute(BEANA,aB);
                    }
                    case RIVISTA -> {
                        rB.setIdB(sB.getIdB());
                        r.setId(rB.getIdB());
                        aB.setTitoloB(rD.getData(r).getTitolo());
                        costo = Integer.parseInt(q) * rD.getData(r).getPrezzo();
                        aB.setPrezzoB(costo);
                        sB.setQuantitaB(Integer.parseInt(q));
                        sB.setSpesaTB(aB.getPrezzoB());
                        sB.setTitoloB(aB.getTitoloB());
                        sB.setIdB(rB.getIdB());
                        req.setAttribute(BEANS,sB);
                        req.setAttribute(BEANA,aB);

                    }
                    default -> 	java.util.logging.Logger.getLogger("calcola not correct").log(Level.SEVERE, "choice not correct");

                }


                RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp");
                view.forward(req,resp);


            }



            if(negozio!=null && negozio.equals("ritiro in negozio"))
            {
                sB.setNegozioSelezionatoB(true);
                switch(pagamento)
                {
                    case "cash"->
                    {
                        req.setAttribute(BEANS, sB);
                        RequestDispatcher view = getServletContext().getRequestDispatcher("/fattura.jsp");
                        view.forward(req,resp);

                    }
                    case "cCredito"->
                    {
                        sB.setSpesaTB(aB.getPrezzoB());
                        req.setAttribute(BEANS, sB);
                        RequestDispatcher view = getServletContext().getRequestDispatcher("/cartaCredito.jsp");
                        view.forward(req,resp);

                    }
                    default->	java.util.logging.Logger.getLogger("ritiro in negozio").log(Level.SEVERE," pick from shop not avalaible");

                }

            }
            if(download!=null && download.equals("scarica il pdf"))
            {
                sB.setNegozioSelezionatoB(false);
                switch(pagamento)
                {
                    case "cash"->
                    {
                        req.setAttribute(BEANS, sB);
                        RequestDispatcher view = getServletContext().getRequestDispatcher("/fattura.jsp");
                        view.forward(req,resp);

                    }
                    case "cCredito"->
                    {
                        sB.setSpesaTB(aB.getPrezzoB());
                        req.setAttribute(BEANS, sB);
                        RequestDispatcher view = getServletContext().getRequestDispatcher("/cartaCredito.jsp");
                        view.forward(req,resp);

                    }
                    default->			java.util.logging.Logger.getLogger("doownload").log(Level.SEVERE, "download error");

                }
            }


        } catch (NumberFormatException e) {
            aB.setMexB(new IdException("quantita eccede la scorta nel magazzino"));
            req.setAttribute(BEANA,aB);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp");
            view.forward(req,resp);
        }
    }


}


