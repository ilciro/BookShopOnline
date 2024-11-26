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
import laptop.model.raccolta.*;
import web.bean.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/GestioneOggettiServlet")
public class GestioneOggettiServlet extends HttpServlet {
    private static final ReportBean rB=new ReportBean();
    private static final SystemBean sB=SystemBean.getInstance();
    private static final LibroDao lD=new LibroDao();
    private static final GiornaleDao gD=new GiornaleDao();
    private static final RivistaDao rD=new RivistaDao();
    private static final ModificaOggettoBean mOB=new ModificaOggettoBean();
    private static final String GENERA="genera";
    private static final String INSERISCI="inserisci";
    private static final LibroBean lB=new LibroBean();
    private static final GiornaleBean gB=new GiornaleBean();
    private static final RivistaBean rivB=new RivistaBean();
    private static final String LIBRO="libro";
    private static final String GIORNALE="giornale";
    private static final String RIVISTA="rivista";
    private static final String MOFIDICA="modifica";
    private static final String BEANS="beanS";
    private static final String BEANMOB="beanMob";
    private static final String AGGIUNGIMODIFICA="/aggiungiModificaOggetto.jsp";
    private static final String GESTIONEOGGETTI="/gestioneOggetti.jsp";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,NumberFormatException {
        String tipo=req.getParameter("tipoOgg");
        String genera=req.getParameter(GENERA);
        String inserisci=req.getParameter(INSERISCI);
        String modifica=req.getParameter(MOFIDICA);
        String id=req.getParameter("idOggetto");
        String elimina=req.getParameter("elimina");
        String indietro=req.getParameter("indietro");

        try {
            RequestDispatcher view;

            if (genera != null && genera.equals(GENERA)) {
                sB.setTypeB(tipo);
                listaOggetti(sB.getTypeB());
                req.setAttribute(BEANS, sB);
                req.setAttribute("beanRepo", rB);
                view = getServletContext().getRequestDispatcher(GESTIONEOGGETTI);
                view.forward(req, resp);
            }
            if (inserisci != null && inserisci.equals(INSERISCI)) {
                sB.setTypeB(tipo);
                sB.setTypeOfModif(INSERISCI);
                setCategorie();

                req.setAttribute(BEANS, sB);
                req.setAttribute(BEANMOB, mOB);
                view = getServletContext().getRequestDispatcher(AGGIUNGIMODIFICA);
                view.forward(req, resp);
            }
            if (modifica != null && modifica.equals(MOFIDICA)) {
                sB.setTypeOfModif(MOFIDICA);
                sB.setTypeB(tipo);
                sB.setIdB(Integer.parseInt(id));
                switch (sB.getTypeB()) {
                    case LIBRO -> getLibroInfo(sB.getIdB(), req, resp);
                    case GIORNALE -> getGiornaleInfo(sB.getIdB(), req, resp);
                    case RIVISTA -> getRivistaInfo(sB.getIdB(),req,resp);
                    default -> Logger.getLogger(MOFIDICA).log(Level.SEVERE," type to modify is wrong !!");
                }

            }
            if (elimina != null && elimina.equals("elimina")) {
                cancellaTipo(sB.getTypeB(), id, req, resp);
            }
            if(indietro!=null && indietro.equals("indietro"))
            {
                view= getServletContext().getRequestDispatcher("/admin.jsp");
                view.forward(req,resp);
            }
        }catch (SQLException|ServletException|IOException e)
        {
            Logger.getLogger(" do post").log(Level.SEVERE," exception .", e);
        }

    }


    private void cancellaTipo(String tipo,String id,HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException,SQLException {
        RequestDispatcher view;
        switch (tipo)
        {
            case LIBRO->{
                lB.setIdB(Integer.parseInt(id));
                Libro l=new Libro();
                l.setId(lB.getIdB());
                if(lD.eliminaLibro(l))
                    Logger.getLogger("elimina libro").log(Level.INFO," deleted book with id {0}: ", l.getId());
                else Logger.getLogger("elimina libro").log(Level.SEVERE, "error in delete book !!");

                req.setAttribute(BEANS,sB);
                view= getServletContext().getRequestDispatcher(GESTIONEOGGETTI);
                view.forward(req,resp);
            }
            case GIORNALE->{
                gB.setIdB(sB.getIdB());
                Giornale g=new Giornale();
                g.setId(gB.getIdB());

                if(gD.cancella(g)==1)
                    Logger.getLogger("elimina giornale").log(Level.INFO," deleted daily with id {0}: ", g.getId());
                else Logger.getLogger("elimina giornale").log(Level.SEVERE, "error in delete daily !!");
                req.setAttribute(BEANS,sB);
                view= getServletContext().getRequestDispatcher(GESTIONEOGGETTI);
                view.forward(req,resp);

            }
            case RIVISTA -> {
                rivB.setIdB(sB.getIdB());
                Rivista r=new Rivista();
                r.setId(rivB.getIdB());
                if(rD.cancella(r)==1)
                    Logger.getLogger("elimina rivista").log(Level.INFO," deleted magazine with id {0}: ", r.getId());
                else Logger.getLogger("elimina rivista").log(Level.SEVERE, "error in delete magazine !!");
                req.setAttribute(BEANS,sB);
                view= getServletContext().getRequestDispatcher(GESTIONEOGGETTI);
                view.forward(req,resp);
            }
            default -> Logger.getLogger(" elimina").log(Level.SEVERE," type is incorrect !!");

        }

    }

    private void listaOggetti(String tipo) {
        switch (tipo)
        {
            case LIBRO->
            {
                rB.setElencoReportB(lD.getLibri());
                sB.setTypeAsBook();
            }
            case GIORNALE->{
                rB.setElencoReportB(gD.getGiornali());
                sB.setTypeAsDaily();
            }
            case RIVISTA->{
                rB.setElencoReportB(rD.getRiviste());
                sB.setTypeAsMagazine();
            }
            default -> Logger.getLogger(GENERA).log(Level.SEVERE," type is wrong !!");
        }
    }

    private void setCategorie() {
        switch (sB.getTypeB()) {
            case LIBRO -> mOB.setListaCategorieB(categorieL());
            case GIORNALE -> mOB.setListaCategorieB(categorieG());
            case RIVISTA -> mOB.setListaCategorieB(categorieR());
            default -> Logger.getLogger(INSERISCI).log(Level.SEVERE," type is wrong !!");
        }
    }


    private ArrayList<String> categorieL()
    {
        ArrayList<String> catL=new ArrayList<>();
        CategorieLibro[] categorie =CategorieLibro.values();

        for(CategorieLibro cat:categorie)
        {
           catL.add(cat.toString());
        }
        return catL;
    }

    private ArrayList<String> categorieG()
    {
        ArrayList<String> catG=new ArrayList<>();
        catG.add("QUOTIDIANO");
        return catG;
    }
    private ArrayList<String> categorieR()
    {
        ArrayList<String> catR=new ArrayList<>();
        CategorieRivista[] categorie = CategorieRivista.values();

        for(CategorieRivista cat:categorie)
        {
            catR.add(cat.toString());
        }
        return catR;
    }

    private  void getLibroInfo(int id,HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {


        lB.setIdB(id);
        Libro l=new Libro();
        l.setId(lB.getIdB());
        lB.setTitoloB(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getTitolo());
        lB.setCodIsbnB(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getCodIsbn());
        lB.setEditoreB(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getEditore());
        lB.setAutoreB(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getAutore());
        lB.setLinguaB(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getLingua());
        lB.setCategoriaB(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getCategoria());
        lB.setDescB(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getDesc());
        lB.setDateB(Date.valueOf(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getDataPubb()));
        lB.setRecensioneB(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getRecensione());
        lB.setNumeroPagineB(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getNrPagine());
        lB.setNrCopieB(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getNrCopie());
        lB.setDisponibilitaB(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getDisponibilita());
        lB.setPrezzoB(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getPrezzo());
        mOB.setListaCategorieB(categorieL());
        if(lB.getTitoloB()!=null )
        {
            req.setAttribute("beanL",lB);
            req.setAttribute(BEANS,sB);
            req.setAttribute(BEANMOB,mOB);
            RequestDispatcher view= getServletContext().getRequestDispatcher(AGGIUNGIMODIFICA);
            view.forward(req,resp);
        }
    }
    private void getGiornaleInfo(int idB, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        gB.setIdB(idB);
        Giornale g=new Giornale();
        g.setId(gB.getIdB());
        gB.setTitoloB(gD.getGiornaleIdTitoloAutore(g).get(0).getTitolo());
        gB.setEditoreB(gD.getGiornaleIdTitoloAutore(g).get(0).getEditore());
        gB.setLinguaB(gD.getGiornaleIdTitoloAutore(g).get(0).getLingua());
        gB.setTipologiaB(gD.getGiornaleIdTitoloAutore(g).get(0).getCategoria());
        gB.setDataB(Date.valueOf(gD.getGiornaleIdTitoloAutore(g).get(0).getDataPubb()));
        gB.setCopieRimanentiB(gD.getGiornaleIdTitoloAutore(g).get(0).getCopieRimanenti());
        gB.setDisponibilitaB(gD.getGiornaleIdTitoloAutore(g).get(0).getDisponibilita());
        gB.setPrezzoB(gD.getGiornaleIdTitoloAutore(g).get(0).getPrezzo());
        mOB.setListaCategorieB(categorieG());
        if(gB.getDataB()!=null)
        {
            req.setAttribute("beanG",gB);
            req.setAttribute(BEANS,sB);
            req.setAttribute(BEANMOB,mOB);
            RequestDispatcher view= getServletContext().getRequestDispatcher(AGGIUNGIMODIFICA);
            view.forward(req,resp);
        }

    }
    private void getRivistaInfo(int idB, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        rivB.setIdB(idB);
        Rivista r=new Rivista();
        r.setId(rivB.getIdB());
        rivB.setTitoloB(rD.getRivistaIdTitoloAutore(r).get(0).getTitolo());
        rivB.setEditoreB(rD.getRivistaIdTitoloAutore(r).get(0).getEditore());
        rivB.setAutoreB(rD.getRivistaIdTitoloAutore(r).get(0).getAutore());
        rivB.setLinguaB(rD.getRivistaIdTitoloAutore(r).get(0).getLingua());
        rivB.setTipologiaB(rD.getRivistaIdTitoloAutore(r).get(0).getCategoria());
        rivB.setDescrizioneB(rD.getRivistaIdTitoloAutore(r).get(0).getDescrizione());
        rivB.setDataB(Date.valueOf(rD.getRivistaIdTitoloAutore(r).get(0).getDataPubb()));
        rivB.setCopieRimB(rD.getRivistaIdTitoloAutore(r).get(0).getCopieRim());
        rivB.setDispB(rD.getRivistaIdTitoloAutore(r).get(0).getDisp());
        rivB.setPrezzoB(rD.getRivistaIdTitoloAutore(r).get(0).getPrezzo());
        mOB.setListaCategorieB(categorieR());
        if (rivB.getDataB()!=null)
        {
            req.setAttribute("beanR",rivB);
            req.setAttribute(BEANS,sB);
            req.setAttribute(BEANMOB,mOB);
            RequestDispatcher view= getServletContext().getRequestDispatcher(AGGIUNGIMODIFICA);
            view.forward(req,resp);
        }
    }


}
