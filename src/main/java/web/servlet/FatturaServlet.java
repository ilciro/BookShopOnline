package web.servlet;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.logging.Level;

import laptop.exception.IdException;
import web.bean.FatturaBean;
import web.bean.LibroBean;
import web.bean.PagamentoBean;
import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.database.ContrassegnoDao;
import laptop.database.PagamentoDao;
import laptop.model.Fattura;
import laptop.model.Pagamento;
@WebServlet("/FatturaServlet")
public class FatturaServlet extends HttpServlet{

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    private static final FatturaBean fB=new FatturaBean();
    private static final  Fattura f=new Fattura();

    private static final PagamentoDao pD=new PagamentoDao();
    private static final ContrassegnoDao fD=new ContrassegnoDao();
    private static final Pagamento p=new Pagamento();
    private static final PagamentoBean pB=new PagamentoBean();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome=req.getParameter("nomeT");
        String cognome=req.getParameter("cognomeT");
        String indirizzo=req.getParameter("indirizzoT");
        String com=req.getParameter("com");
        String invia=req.getParameter("buttonC");
        String annullaO=req.getParameter("annulla");

        if((invia!=null )&& invia.equals("procedi"))
        {

            fB.setNomeB(nome);
            fB.setCognomeB(cognome);
            fB.setIndirizzoB(indirizzo);
            fB.setComunicazioniB(com);
            try {
                f.setNome(fB.getNomeB());
                f.setCognome(fB.getCognomeB());
                f.setVia(fB.getIndirizzoB());
                f.setCom(fB.getComunicazioniB());
                f.setAmmontare(SystemBean.getInstance().getSpesaTB());


                pB.setIdB(0);
                pB.setMetodoB(SystemBean.getInstance().getMetodoPB());
                pB.setAmmontareB(SystemBean.getInstance().getSpesaTB());
                pB.setEsitoB(0);
                pB.setNomeUtenteB(fB.getNomeB());
                //
                pB.setTipoB(SystemBean.getInstance().getCategoriaB());
                //added for idOgg
                pB.setIdOggettoB(SystemBean.getInstance().getIdB());

                p.setId(pB.getIdB());
                p.setMetodo(pB.getMetodoB());
                p.setAmmontare(pB.getAmmontareB());
                p.setEsito(pB.getEsitoB());
                p.setNomeUtente(pB.getNomeUtenteB());
                p.setTipo(pB.getTipoB());
                //added for idOgg
                p.setIdOggetto(pB.getIdOggettoB());


                fD.inserisciFattura(f);
                pD.inserisciPagamento(p);
            } catch (SQLException  | IdException e) {
                java.util.logging.Logger.getLogger("post ").log(Level.INFO, "eccezione nel post {0}.",e.getMessage());
            }

            if(SystemBean.getInstance().isNegozioSelezionatoB())
            {
                RequestDispatcher view = getServletContext().getRequestDispatcher("/negozi.jsp");
                view.forward(req,resp);
            }
            else {
                req.setAttribute("beanS",SystemBean.getInstance());
                RequestDispatcher view = getServletContext().getRequestDispatcher("/download.jsp");
                view.forward(req,resp);
            }
        }


        if(annullaO!=null && annullaO.equals("annulla"))
        {
            RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp");
            view.forward(req,resp);
        }


    }





}