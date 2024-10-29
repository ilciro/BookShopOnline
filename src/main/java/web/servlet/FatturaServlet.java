package web.servlet;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;

import laptop.controller.ControllerSystemState;
import laptop.database.*;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import web.bean.FatturaBean;
import web.bean.PagamentoBean;
import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.model.Fattura;
import laptop.model.Pagamento;
@WebServlet("/FatturaServlet")

public class FatturaServlet extends HttpServlet implements Serializable {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    private final transient FatturaBean fB=new FatturaBean();
    private final transient  Fattura f=new Fattura();

    private final transient PagamentoDao pD=new PagamentoDao();
    private final transient ContrassegnoDao fD=new ContrassegnoDao();
    private final transient Pagamento p=new Pagamento();
    private final transient PagamentoBean pB=new PagamentoBean();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nome=req.getParameter("nomeL");
        String cognome=req.getParameter("cognomeL");
        String indirizzo=req.getParameter("indirizzoL");
        String com=req.getParameter("com");
        String invia=req.getParameter("buttonC");
        String annullaO=req.getParameter("annulla");
        String tipologia="";


        try {
        if(checkData(fB.getNomeB(),fB.getCognomeB(),fB.getIndirizzoB()) && (invia!=null )&& invia.equals("procedi"))
        {

            fB.setNomeB(nome);
            fB.setCognomeB(cognome);
            fB.setIndirizzoB(indirizzo);
            fB.setComunicazioniB(com);
            fB.setAmmontareB(SystemBean.getInstance().getSpesaTB());
            ControllerSystemState.getInstance().setSpesaT(SystemBean.getInstance().getSpesaTB());

                f.setNome(fB.getNomeB());
                f.setCognome(fB.getCognomeB());
                f.setVia(fB.getIndirizzoB());
                f.setCom(fB.getComunicazioniB());
                f.setAmmontare(fB.getAmmontareB());


                pB.setIdB(0);
                pB.setMetodoB(SystemBean.getInstance().getMetodoPB());
                pB.setAmmontareB(SystemBean.getInstance().getSpesaTB());
                pB.setEsitoB(0);
                pB.setNomeUtenteB(fB.getNomeB());
                pB.setIdOggettoB(SystemBean.getInstance().getIdB());
                switch (SystemBean.getInstance().getTypeB())
                {
                    case "libro"->{
                        LibroDao lD=new LibroDao();
                        Libro l=new Libro();
                        l.setId(SystemBean.getInstance().getIdB());
                         tipologia=lD.getLibroByIdTitoloAutoreLibro(l).get(0).getCategoria();
                    }
                    case "giornale"->{
                        GiornaleDao gD=new GiornaleDao();
                        Giornale g=new Giornale();
                        g.setId(SystemBean.getInstance().getIdB());
                        tipologia=gD.getGiornaleIdTitoloAutore(g).get(0).getCategoria();
                    }
                    case "rivista"->
                    {
                        RivistaDao rD=new RivistaDao();
                        Rivista r=new Rivista();
                        r.setId(SystemBean.getInstance().getIdB());
                        tipologia=rD.getRivistaIdTitoloAutore(r).get(0).getCategoria();
                    }
                    default -> {
                        break;
                    }
                }
                pB.setTipoB(tipologia);

                p.setIdPag(pB.getIdB());
                p.setMetodo(pB.getMetodoB());
                p.setAmmontare(pB.getAmmontareB());

                p.setNomeUtente(pB.getNomeUtenteB());
                p.setTipo(pB.getTipoB());
                p.setIdOggetto(pB.getIdOggettoB());


                fD.inserisciFattura(f);
                pD.inserisciPagamento(p);


            if(SystemBean.getInstance().isNegozioSelezionatoB())
            {
                RequestDispatcher view = getServletContext().getRequestDispatcher("/negozi.jsp");
                view.forward(req,resp);
            }
            else {
                req.setAttribute("bean1",SystemBean.getInstance());
                req.setAttribute("fBean",fB);
                req.setAttribute("pBean",pB);
                RequestDispatcher view = getServletContext().getRequestDispatcher("/download.jsp");
                view.forward(req,resp);
            }
        }


        if(annullaO!=null && annullaO.equals("annulla"))
        {
            req.setAttribute("bean1",SystemBean.getInstance());

            RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp");
            view.forward(req,resp);
        }
        } catch (SQLException |ServletException  e) {
            java.util.logging.Logger.getLogger("post ").log(Level.INFO, "eccezione nel post {0}.",e.getMessage());
        }


    }

    private  boolean checkData(String nome,String cognome ,String indirizzo)
    {
        return !"".equals(nome) && !"".equals(cognome) && !"".equals(indirizzo);
    }



}