package web.servlet;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

import laptop.database.GiornaleDao;
import laptop.database.LibroDao;
import laptop.database.RivistaDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import web.bean.AcquistaBean;
import web.bean.GiornaleBean;
import web.bean.LibroBean;
import web.bean.RivistaBean;
import web.bean.SystemBean;
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
    private final transient AcquistaBean aB=new AcquistaBean();
    private  final transient LibroDao lD=new LibroDao();
    private final transient Libro l=new Libro();
    private final transient LibroBean lB=new LibroBean();
    private final transient GiornaleBean gB=new GiornaleBean();
    private final transient RivistaBean rB=new RivistaBean();
    private final transient Rivista r=new Rivista();
    private final transient RivistaDao rD=new RivistaDao();
    private final transient Giornale g=new Giornale();
    private final transient GiornaleDao gD=new GiornaleDao();
    private  static final String BEAN1="bean1";
    private static final String LIBRO="libro";
    private static final String GIORNALE="giornale";
    private static final String RIVISTA="rivista";

    public AcquistaServlet()
    {

        String type=SystemBean.getInstance().getTypeB();
        switch (type) {
            case LIBRO -> {

                lB.setIdB(SystemBean.getInstance().getIdB());
                l.setId(lB.getIdB());
                aB.setTitoloB(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getTitolo());
            }
            case  GIORNALE-> {

                    gB.setIdB(SystemBean.getInstance().getIdB());
                    g.setId(gB.getIdB());
                    aB.setTitoloB(gD.getGiornaleIdTitoloAutore(g).get(0).getTitolo());
                }
            case  RIVISTA-> {
                rB.setIdB(SystemBean.getInstance().getIdB());
                r.setId(rB.getIdB());
                aB.setTitoloB(rD.getRivistaIdTitoloAutore(r).get(0).getTitolo());
            }
            default -> {
                break;
            }


        }

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q=req.getParameter("quantita");
        String calcola=req.getParameter("totaleB");
        String metodo=req.getParameter("metodoP");
        String negozio=req.getParameter("negozioB");
        SystemBean.getInstance().setMetodoPB(metodo);
        String download=req.getParameter("pdfB");
        float costo;
        String type=SystemBean.getInstance().getTypeB();
        String pagamento=SystemBean.getInstance().getMetodoPB();
        try {



            if(calcola!=null && calcola.equals("calcola"))
            {
                switch(type)
                {
                    case LIBRO->
                    {

                        costo=Integer.parseInt(q)*lD.getLibroByIdTitoloAutoreLibro(l).get(0).getPrezzo();
                        aB.setPrezzoB(costo);
                        SystemBean.getInstance().setQuantitaB(Integer.parseInt(q));
                        SystemBean.getInstance().setSpesaTB(costo);
                        SystemBean.getInstance().setTitoloB(aB.getTitoloB());
                        SystemBean.getInstance().setSpesaTB(aB.getPrezzoB());


                    }
                    case  GIORNALE->
                    {

                        costo=Integer.parseInt(q)*gD.getGiornaleIdTitoloAutore(g).get(0).getPrezzo();
                        aB.setPrezzoB(costo);
                        SystemBean.getInstance().setQuantitaB(Integer.parseInt(q));
                        SystemBean.getInstance().setSpesaTB(costo);
                        SystemBean.getInstance().setTitoloB(aB.getTitoloB());
                        SystemBean.getInstance().setSpesaTB(aB.getPrezzoB());

                    }
                    case  RIVISTA->
                    {

                        costo=Integer.parseInt(q)*rD.getRivistaIdTitoloAutore(r).get(0).getPrezzo();
                        aB.setPrezzoB(costo);
                        SystemBean.getInstance().setQuantitaB(Integer.parseInt(q));
                        SystemBean.getInstance().setSpesaTB(costo);
                        SystemBean.getInstance().setTitoloB(aB.getTitoloB());
                        SystemBean.getInstance().setSpesaTB(aB.getPrezzoB());

                    }
                    default->{
                        break;
                    }
                }


                req.setAttribute("beanA",aB);
                req.setAttribute(BEAN1, SystemBean.getInstance());
                RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp");
                view.forward(req,resp);


            }



            if(negozio!=null && negozio.equals("ritiro in negozio"))
            {
                SystemBean.getInstance().setNegozioSelezionatoB(true);
                switch(pagamento)
                {
                    case "cash":
                    {
                        req.setAttribute(BEAN1, SystemBean.getInstance());

                        RequestDispatcher view = getServletContext().getRequestDispatcher("/fattura.jsp");
                        view.forward(req,resp);
                        break;
                    }
                    case "cCredito":
                    {
                        req.setAttribute(BEAN1, SystemBean.getInstance());

                        RequestDispatcher view = getServletContext().getRequestDispatcher("/cartaCredito.jsp");
                        view.forward(req,resp);
                        break;
                    }
                    default:break;
                }

            }
            if(download!=null && download.equals("scarica il pdf"))
            {
                SystemBean.getInstance().setNegozioSelezionatoB(false);
                switch(pagamento)
                {
                    case "cash":
                    {
                        req.setAttribute(BEAN1, SystemBean.getInstance());

                        RequestDispatcher view = getServletContext().getRequestDispatcher("/fattura.jsp");
                        view.forward(req,resp);
                        break;
                    }
                    case "cCredito":
                    {
                        req.setAttribute(BEAN1, SystemBean.getInstance());

                        RequestDispatcher view = getServletContext().getRequestDispatcher("/cartaCredito.jsp");
                        view.forward(req,resp);
                        break;
                    }
                    default:break;
                }
            }


        } catch (NumberFormatException | ServletException |IOException e) {
            aB.setMexB(new IdException("quantita eccede la scorta nel magazzino"));
            req.setAttribute("beanA",aB);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp");
            view.forward(req,resp);
        }
    }


}


