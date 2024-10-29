package web.servlet;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;

import laptop.database.*;
import web.bean.CartaCreditoBean;
import web.bean.GiornaleBean;
import web.bean.LibroBean;
import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.model.CartaDiCredito;
import laptop. model.Pagamento;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;

@WebServlet("/CartaCreditoServlet")
public class CartaCreditoServlet extends HttpServlet implements Serializable {


    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    private final transient CartaCreditoBean ccB=new CartaCreditoBean();
    private final transient CartaDiCredito cc=new CartaDiCredito();
    private  final transient Libro l=new Libro();
    private final transient LibroDao lD=new LibroDao();
    private final transient GiornaleDao gD=new GiornaleDao();
    private final transient Giornale g=new Giornale();
    private final transient GiornaleBean gB=new GiornaleBean();
    private final transient Rivista r=new Rivista();
    private final transient RivistaDao rD=new RivistaDao();
    private final transient PagamentoDao pD=new PagamentoDao();
    private final transient CartaCreditoDao ccDao=new CartaCreditoDao();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String n=req.getParameter("nomeL");
        String c=req.getParameter("cognomeL");
        String numero=req.getParameter("cartaL");
        String scad=req.getParameter("scadL");
        String civ=req.getParameter("passL");
        String invia=req.getParameter("buttonI");
        String annulla=req.getParameter("buttonA");
        String registra=req.getParameter("regB");
        String generaLista=req.getParameter("prendiDB");
        try {
            if(annulla!=null && annulla.equals("annulla"))
            {
                RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp");
                view.forward(req,resp);
            }
            if(invia!=null && invia.equals("paga"))
            {
                ccB.setNomeB(n);
                ccB.setCivB(c);
                ccB.setNumeroCCB(numero);
                ccB.setCognomeB(c);

                ccB.setDataScadB(new SimpleDateFormat("yyyy/MM/dd").parse(scad));
                ccB.setCivB(civ);
                ccB.setPrezzoTransazioneB(SystemBean.getInstance().getSpesaTB());

                if(controllaPag(scad, ccB.getNumeroCCB(), ccB.getCivB()))
                {
                    //aggiungo carta al db

                    Date sqlDate = null;
                    java.util.Date utilDate;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");


                    utilDate = format.parse(scad);
                    sqlDate = new java.sql.Date(utilDate.getTime());

                    aggiungiCartaDB(ccB.getNomeB(), ccB.getCognomeB(), ccB.getNumeroCCB(), sqlDate, ccB.getCivB(), SystemBean.getInstance().getSpesaTB());

                    //inserisco nel db



                    cc.setTipo(2);
                    cc.setNumeroCC(ccB.getNumeroCCB());
                    cc.setAmmontare(1000.0);
                    cc.setScadenza(sqlDate);
                    cc.setNomeUser(ccB.getNomeB());
                    cc.setPrezzoTransazine(SystemBean.getInstance().getSpesaTB());

                    insCC(cc);

                    if(SystemBean.getInstance().isNegozioSelezionatoB())
                    {
                        req.setAttribute("bean1",SystemBean.getInstance());
                        RequestDispatcher view = getServletContext().getRequestDispatcher("/negozi.jsp");
                        view.forward(req,resp);
                    }
                    else {
                        req.setAttribute("bean1",SystemBean.getInstance());
                        RequestDispatcher view = getServletContext().getRequestDispatcher("/download.jsp");
                        view.forward(req,resp);
                    }

                }


            }
            if(registra!=null && registra.equals("registra e paga"))
            {
                java.util.logging.Logger.getLogger("post registra ").log(Level.INFO, "da fare");

            }
            if(generaLista!=null && generaLista.equals("generaLista"))
            {
                java.util.logging.Logger.getLogger("post genera").log(Level.INFO, "da fare");

            }

        } catch (Exception e) {
            java.util.logging.Logger.getLogger("post ").log(Level.INFO, "eccezione nel post {0}.", e.toString());
        }
    }

    private boolean controllaPag(String d, String c,String civ) {
        int x;

        int anno;
        int mese;
        int giorno;
        String[] verifica=null;
        String appoggio="";
        int cont=0;
        boolean state=false;




        appoggio = appoggio + d;


        anno = Integer.parseInt((String) appoggio.subSequence(0, 4));
        mese = Integer.parseInt((String) appoggio.subSequence(5, 7));
        giorno = Integer.parseInt((String) appoggio.subSequence(8, 10));

        if (anno > 2020 && (mese >= 1 && mese <= 12) && (giorno >= 1 && giorno <= 31) && c.length() <= 20 && civ.length()==3 ) {


            verifica= c.split("-");

            for ( x=0; x<verifica.length; x++) {
                if(verifica[x].length()==4)
                {
                    cont++;
                }
            }
            if (cont==4)
            {
                state=true;
            }



        }


        return state;

    }

    private void aggiungiCartaDB(String n, String c, String cod, java.sql.Date data, String civ, float prezzo)
            throws SQLException {


        cc.setNomeUser(n);
        cc.setCognomeUser(c);
        cc.setNumeroCC(cod);
        cc.setScadenza(data);
        cc.setCiv(civ);
        cc.setPrezzoTransazine(prezzo);
        cc.setPrezzoTransazine(SystemBean.getInstance().getSpesaTB());


        insCC(cc);

        Pagamento p;
        p=new Pagamento();
        p.setMetodo("cc");
        p.setNomeUtente(cc.getNomeUser());
        p.setAmmontare(SystemBean.getInstance().getSpesaTB())  ;
        p.setTipo(SystemBean.getInstance().getTypeB());
        p.setIdOggetto(SystemBean.getInstance().getIdB());
        String tipo=SystemBean.getInstance().getTypeB();
        if(tipo.equals("libro"))
        {
            //prenod spesa da vis
            l.setId(SystemBean.getInstance().getIdB());
            p.setAmmontare(SystemBean.getInstance().getSpesaTB());
            p.setIdPag(l.getId());
            p.setTipo(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getCategoria());
        }

        if(tipo.equals("giornale"))
        {
            //prenod spesa da vis
            g.setId(SystemBean.getInstance().getIdB());
            p.setAmmontare(SystemBean.getInstance().getSpesaTB());
            p.setIdPag(g.getId());
            p.setTipo(gD.getGiornaleIdTitoloAutore(g).get(0).getCategoria());

        }

        if(tipo.equals("rivista"))
        {
            //prenod spesa da vis
            r.setId(SystemBean.getInstance().getIdB());
            p.setAmmontare(SystemBean.getInstance().getSpesaTB());
            p.setIdPag(r.getId());
            p.setTipo(rD.getRivistaIdTitoloAutore(r).get(0).getCategoria());

        }

        pD.inserisciPagamento(p);



    }

    private void insCC(CartaDiCredito cc)
    {

        ccDao.insCC(cc);

    }

}