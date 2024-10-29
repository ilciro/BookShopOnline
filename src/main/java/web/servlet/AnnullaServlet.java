package web.servlet;

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

import web.bean.AnnullaBean;
import web.bean.FatturaBean;
import web.bean.PagamentoBean;
import web.bean.SystemBean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;



@WebServlet ("/AnnullaServlet")
public class AnnullaServlet extends HttpServlet implements Serializable {
    private final transient FatturaBean fB=new FatturaBean();
    private final transient PagamentoBean pB=new PagamentoBean();
    private final transient ContrassegnoDao cD=new ContrassegnoDao();
    private final transient PagamentoDao pD=new PagamentoDao();
    private final transient Fattura f=new Fattura();
    private final transient Pagamento p=new Pagamento();
    private static final String ANNULLA="/annullaPagamento.jsp";
    private final transient AnnullaBean ab=new AnnullaBean();
    private static final String BEAN1="bean1";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String genera=req.getParameter("buttonG");
        String fattura=req.getParameter("buttonF");
        String pagamento=req.getParameter("buttonP");
        String idFattura=req.getParameter("idF");
        String idPagamento=req.getParameter("idP");
        RequestDispatcher view;




        try {
    if (genera != null && genera.equalsIgnoreCase("genera lista")) {

            listaFatture();
            listaPagamenti();















        req.setAttribute("fBean", fB);
        req.setAttribute("pBean", pB);
        req.setAttribute(BEAN1, SystemBean.getInstance());

        if(ab.getAnnullaFB()==1 && ab.getAnnullaPB()==1) {
             view = getServletContext().getRequestDispatcher("/index.jsp");
            view.forward(req, resp);
        }
         view = getServletContext().getRequestDispatcher(ANNULLA);
        view.forward(req, resp);

    }
    if (fattura != null && fattura.equalsIgnoreCase("annulla fattura")) {
        if(annullaF(idFattura))
            ab.setAnnullaFB(1);
        req.setAttribute(BEAN1, SystemBean.getInstance());

         view = getServletContext().getRequestDispatcher(ANNULLA);
        view.forward(req, resp);



    }
    if (pagamento != null && pagamento.equalsIgnoreCase("annulla pagamento")) {
        if(annullaP(idPagamento))
            ab.setAnnullaPB(1);

        req.setAttribute(BEAN1, SystemBean.getInstance());
         view = getServletContext().getRequestDispatcher(ANNULLA);
        view.forward(req, resp);

    }
}catch (ServletException | SQLException e)
{
     view = getServletContext().getRequestDispatcher(ANNULLA);
    view.forward(req, resp);

}
    }

    private void reportFattura()
    {
        fB.setIdFatturaB(cD.ultimaFattura().getIdFattura());
        fB.setNomeB(cD.ultimaFattura().getNome());
        fB.setCognomeB(cD.ultimaFattura().getCognome());
        fB.setIndirizzoB(cD.ultimaFattura().getVia());
        fB.setComunicazioniB(cD.ultimaFattura().getCom());
        fB.setAmmontareB(cD.ultimaFattura().getAmmontare());
    }
    private void reportPagamento()
    {
        pB.setIdB(pD.ultimoPagamento().getIdPag());
        pB.setMetodoB(pD.ultimoPagamento().getMetodo());
        pB.setNomeUtenteB(pD.ultimoPagamento().getNomeUtente());
        pB.setAmmontareB(pD.ultimoPagamento().getAmmontare());
        pB.setEmailB(pD.ultimoPagamento().getEmail());
        pB.setTipoB(pD.ultimoPagamento().getTipo());
        pB.setIdOggettoB(pD.ultimoPagamento().getIdOggetto());
    }
    private boolean annullaF(String idF) throws SQLException {
        fB.setIdFatturaB(Integer.parseInt(idF));
        f.setIdFattura(fB.getIdFatturaB());
        return cD.cancellaFattura(f.getIdFattura());

    }
    private boolean annullaP(String idP) throws SQLException {
        pB.setIdB(Integer.parseInt(idP));
        p.setIdPag(pB.getIdB());
        return pD.cancellaPagamento(p);

    }
    private void fatturaDopoCanc()
    {
        fB.setIdFatturaB(-1);
        fB.setNomeB("");
        fB.setCognomeB("");
        fB.setIndirizzoB("");
        fB.setComunicazioniB("");
        fB.setAmmontareB(0);
    }
    private void pagamentoDopoCanc()
    {
        pB.setIdB(-1);
        pB.setMetodoB("");
        pB.setNomeUtenteB("");
        pB.setAmmontareB(0);
        pB.setEmailB("");
        pB.setTipoB("");
        pB.setIdOggettoB(-1);
    }

    private void listaFatture()
    {
        if(ab.getAnnullaFB()==1)
            fatturaDopoCanc();
        else reportFattura();
    }
    private void listaPagamenti()
    {
        if(ab.getAnnullaPB()==1)
            pagamentoDopoCanc();
        else reportPagamento();

    }

}
