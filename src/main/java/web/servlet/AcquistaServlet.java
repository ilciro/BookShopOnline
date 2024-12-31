package web.servlet;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

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

public class AcquistaServlet extends HttpServlet implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;
    private static final  AcquistaBean aB=new AcquistaBean();
    private static final SystemBean sB=SystemBean.getInstance();
    private  static final LibroDao lD=new LibroDao();
    private static final Libro l=new Libro();
    private static final LibroBean lB=new LibroBean();
    private static final GiornaleBean gB=new GiornaleBean();
    private static final RivistaBean rB=new RivistaBean();
    private static final Rivista r=new Rivista();
    private static final RivistaDao rD=new RivistaDao();
    private static final Giornale g=new Giornale();
    private static final GiornaleDao gD=new GiornaleDao();
    private  static final String BEANS="beanS";
    private static final String LIBRO="libro";
    private static final String GIORNALE="giornale";
    private static final String RIVISTA="rivista";
    private static final UserBean uB=UserBean.getInstance();
    private static final String BEANUB="beanUB";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = req.getParameter("quantita");
        String calcola = req.getParameter("totaleB");
        String metodo = req.getParameter("metodoP");
        String negozio = req.getParameter("negozioB");
        sB.setMetodoPB(metodo);
        String download = req.getParameter("pdfB");
        float costo;

        String pagamento = sB.getMetodoPB();
        try {

            if (calcola != null && calcola.equals("calcola")) {
                switch (sB.getTypeB()) {
                    case LIBRO -> {
                        lB.setIdB(sB.getIdB());
                        l.setId(lB.getIdB());
                        aB.setTitoloB(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getTitolo());

                        costo = Integer.parseInt(q) * lD.getLibroByIdTitoloAutoreLibro(l).get(0).getPrezzo();

                        aB.setPrezzoB(costo);
                        sB.setQuantitaB(Integer.parseInt(q));
                        sB.setSpesaTB(costo);
                        sB.setTitoloB(aB.getTitoloB());
                        sB.setSpesaTB(aB.getPrezzoB());

                    }
                    case GIORNALE -> {
                        gB.setIdB(sB.getIdB());
                        g.setId(gB.getIdB());
                        aB.setTitoloB(gD.getGiornaleIdTitoloAutore(g).get(0).getTitolo());


                        costo = Integer.parseInt(q) * gD.getGiornaleIdTitoloAutore(g).get(0).getPrezzo();
                        aB.setPrezzoB(costo);
                        sB.setQuantitaB(Integer.parseInt(q));
                        sB.setSpesaTB(costo);
                        sB.setTitoloB(aB.getTitoloB());
                        sB.setSpesaTB(aB.getPrezzoB());

                    }
                    case RIVISTA -> {
                        rB.setIdB(sB.getIdB());
                        r.setId(rB.getIdB());
                        aB.setTitoloB(rD.getRivistaIdTitoloAutore(r).get(0).getTitolo());

                        costo = Integer.parseInt(q) * rD.getRivistaIdTitoloAutore(r).get(0).getPrezzo();
                        aB.setPrezzoB(costo);
                        sB.setQuantitaB(Integer.parseInt(q));
                        sB.setSpesaTB(costo);
                        sB.setTitoloB(aB.getTitoloB());
                        sB.setSpesaTB(aB.getPrezzoB());

                    }
                    default -> Logger.getLogger("do post").log(Level.SEVERE, " type is incorrect !!");
                }


                req.setAttribute("beanA", aB);
                req.setAttribute(BEANS, sB);
                RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp");
                view.forward(req, resp);


            }


            if (negozio != null && negozio.equals("ritiro in negozio")) {


                sB.setNegozioSelezionatoB(true);
                switch (pagamento) {
                    case "cash"-> {
                        req.setAttribute(BEANS, sB);
                        req.setAttribute(BEANUB, uB);

                        RequestDispatcher view = getServletContext().getRequestDispatcher("/fattura.jsp");
                        view.forward(req, resp);

                    }
                    case "cCredito"-> {
                        req.setAttribute(BEANS, sB);
                        req.setAttribute(BEANUB, uB);


                        RequestDispatcher view = getServletContext().getRequestDispatcher("/cartaCredito.jsp");
                        view.forward(req, resp);
                    }
                    default-> Logger.getLogger("do post negozio").log(Level.SEVERE," type is wrong !!");

                }

            }
            if (download != null && download.equals("scarica il pdf")) {
                SystemBean.getInstance().setNegozioSelezionatoB(false);

                switch (pagamento) {
                    case "cash"-> {
                        req.setAttribute(BEANS,sB);
                        req.setAttribute(BEANUB, uB);



                        RequestDispatcher view = getServletContext().getRequestDispatcher("/fattura.jsp");
                        view.forward(req, resp);
                    }
                    case "cCredito"->{
                        req.setAttribute(BEANS, sB);
                        req.setAttribute(BEANUB, uB);


                        RequestDispatcher view = getServletContext().getRequestDispatcher("/cartaCredito.jsp");
                        view.forward(req, resp);
                    }
                    default->Logger.getLogger(" download error").log(Level.SEVERE," type of download in incorrect!!");

                }
            }


        } catch (NumberFormatException | ServletException | IOException e) {
            aB.setMexB(new IdException("quantita eccede la scorta nel magazzino"));
            req.setAttribute("beanA", aB);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp");
            view.forward(req, resp);
        }
    }


}


