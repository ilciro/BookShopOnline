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
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import web.bean.GiornaleBean;
import web.bean.LibroBean;
import web.bean.RivistaBean;
import web.bean.SystemBean;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/AggiungiModificaOggettoServlet")
public class AggiungiModificaOggettoServlet extends HttpServlet {
    private static final SystemBean sB=SystemBean.getInstance();
    private static final LibroBean lB=new LibroBean();
    private static final GiornaleBean gB=new GiornaleBean();
    private static final RivistaBean rB=new RivistaBean();
    private static final String INSERISCI="inserisci";
    private static final String GESTIONE="/gestioneOggetti.jsp";
    private static final String MODIFICA="/aggiungiModificaOggetto.jsp";
    private static final Libro l=new Libro();
    private static final LibroDao lD=new LibroDao();
    private static final Giornale g=new Giornale();
    private static final GiornaleDao gD=new GiornaleDao();
    private static final Rivista r=new Rivista();
    private static final RivistaDao rD=new RivistaDao();
    private static final String BEANS="beanS";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String inserisci=req.getParameter(INSERISCI);
        String modifica= req.getParameter("modifica");
        String indietro=req.getParameter("indietro");

        try {
            if (inserisci != null && inserisci.equals(INSERISCI)) {
                switch (sB.getTypeB()) {
                    case "libro" -> inserisciLibro(req, resp);
                    case "giornale" -> inserisciGiornale(req, resp);
                    case "rivista"->inserisciRivista(req,resp);
                    default -> Logger.getLogger(INSERISCI).log(Level.SEVERE, " type is wrong !!");
                }
            }
            if(modifica!=null && modifica.equals("modifica"))
            {
                switch (sB.getTypeB()) {
                    case "libro" -> modificaLibro(req, resp);
                    case "giornale" -> modificaGiornale(req, resp);
                    case "rivista"->modificaRivista(req,resp);
                    default -> Logger.getLogger(INSERISCI).log(Level.SEVERE, " type is wrong !!");
                }
            }



            if (indietro != null && indietro.equals("indietro")) {
                RequestDispatcher view;
                view = getServletContext().getRequestDispatcher(GESTIONE);
                view.forward(req, resp);
            }
        }catch (IOException|SQLException|ServletException e)
        {
            Logger.getLogger("do post aggiungi").log(Level.SEVERE," exception has occurred :",e);
        }
    }

    private void modificaRivista(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String titolo=req.getParameter("titoloRN");
        String editore=req.getParameter("editoreRN");
        String autore=req.getParameter("autoreRN");
        String lingua=req.getParameter("linguaRN");
        String categoria=req.getParameter("categorieRN");
        String desc=req.getParameter("descRN");
        LocalDate data= LocalDate.parse(req.getParameter("dataRN"));
        String copie=req.getParameter("copieRN");
        String disp=req.getParameter("dispRN");
        String prezzo=req.getParameter("prezzoRN");
        RequestDispatcher view;
        rB.setIdB(sB.getIdB());
        rB.setTitoloB(titolo);
        rB.setEditoreB(editore);
        rB.setAutoreB(autore);
        rB.setLinguaB(lingua);
        rB.setTipologiaB(categoria);
        rB.setDescrizioneB(desc);
        rB.setDataB(Date.valueOf(data));
        rB.setCopieRimB(Integer.parseInt(copie));
        rB.setDispB(Integer.parseInt(disp));
        rB.setPrezzoB(Float.parseFloat(prezzo));
        r.setId(rB.getIdB());
        r.setTitolo(rB.getTitoloB());
        r.setEditore(rB.getEditoreB());
        r.setAutore(rB.getAutoreB());
        r.setLingua(rB.getLinguaB());
        r.setCategoria(rB.getTipologiaB());
        r.setDescrizione(rB.getDescrizioneB());
        r.setDataPubb(rB.getDataB().toLocalDate());
        r.setCopieRim(rB.getCopieRimB());
        r.setDisp(rB.getDispB());
        r.setPrezzo(rB.getPrezzoB());
        req.setAttribute(BEANS,sB);
        if(rD.aggiornaRivista(r))
        {
            view= getServletContext().getRequestDispatcher(GESTIONE);
            view.forward(req,resp);
        }
        else
        {
            view= getServletContext().getRequestDispatcher(MODIFICA);
            view.forward(req,resp);
        }




    }

    private void modificaGiornale(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String titolo=req.getParameter("titoloGN");
        String editore=req.getParameter("editoreGN");
        String lingua=req.getParameter("linguaGN");
        String categoria=req.getParameter("categoriaGN");
        LocalDate data= LocalDate.parse(req.getParameter("dataGN"));
        String copie=req.getParameter("copieGN");
        String disp=req.getParameter("dispGN");
        String prezzo=req.getParameter("prezzoGN");
        RequestDispatcher view;
        gB.setTitoloB(titolo);
        gB.setEditoreB(editore);
        gB.setLinguaB(lingua);
        gB.setTipologiaB(categoria);
        gB.setDataB(Date.valueOf(data));
        gB.setCopieRimanentiB(Integer.parseInt(copie));
        gB.setDisponibilitaB(Integer.parseInt(disp));
        gB.setPrezzoB(Float.parseFloat(prezzo));
        gB.setIdB(sB.getIdB());

        g.setTitolo(gB.getTitoloB());
        g.setEditore(gB.getEditoreB());
        g.setLingua(gB.getLinguaB());
        g.setCategoria(gB.getTipologiaB());
        g.setDataPubb(gB.getDataB().toLocalDate());
        g.setCopieRimanenti(gB.getCopieRimanentiB());
        g.setDisponibilita(gB.getDisponibilitaB());
        g.setPrezzo(gB.getPrezzoB());
        g.setId(gB.getIdB());
        req.setAttribute(BEANS,sB);
        if(gD.aggiornaGiornale(g))
        {

            view= getServletContext().getRequestDispatcher(GESTIONE);
            view.forward(req,resp);
        }
        else
        {
            view= getServletContext().getRequestDispatcher(MODIFICA);
            view.forward(req,resp);
        }



    }

    private void modificaLibro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String titolo=req.getParameter("titoloN");
        String codice=req.getParameter("codiceN");
        String editore=req.getParameter("editoreN");
        String autore=req.getParameter("autoreN");
        String lingua=req.getParameter("linguaN");
        String categoria=req.getParameter("categorieN");
        String desc=req.getParameter("descN");
        LocalDate data= LocalDate.parse(req.getParameter("dataN"));
        String rec=req.getParameter("recN");
        String pagine=req.getParameter("pagineN");
        String copie=req.getParameter("copieN");
        String disp=req.getParameter("dispN");
        String prezzo=req.getParameter("prezzoN");
        RequestDispatcher view;

        lB.setTitoloB(titolo);
        lB.setCodIsbnB(codice);
        lB.setEditoreB(editore);
        lB.setAutoreB(autore);
        lB.setLinguaB(lingua);
        lB.setCategoriaB(categoria);
        lB.setDescB(desc);
        lB.setDateB(Date.valueOf(data));
        lB.setRecensioneB(rec);
        lB.setNumeroPagineB(Integer.parseInt(pagine));
        lB.setNrCopieB(Integer.parseInt(copie));
        lB.setDisponibilitaB(Integer.parseInt(disp));
        lB.setPrezzoB(Float.parseFloat(prezzo));
        lB.setIdB(sB.getIdB());

       l.setId(lB.getIdB());
       l.setTitolo(lB.getTitoloB());
       l.setCodIsbn(lB.getCodIsbnB());
       l.setEditore(lB.getEditoreB());
       l.setAutore(lB.getAutoreB());
       l.setLingua(lB.getLinguaB());
       l.setCategoria(lB.getCategoriaB());
       l.setDesc(lB.getDescB());
       l.setDataPubb(lB.getDateB().toLocalDate());
       l.setRecensione(lB.getRecensioneB());
       l.setNrPagine(lB.getNumeroPagineB());
       l.setNrCopie(lB.getNrCopieB());
       l.setDisponibilita(lB.getDisponibilitaB());
       l.setPrezzo(lB.getPrezzoB());

        req.setAttribute(BEANS,sB);



       if(lD.aggiornaLibro(l))
       {
           view= getServletContext().getRequestDispatcher(GESTIONE);
           view.forward(req,resp);
       }
       else
       {
           view= getServletContext().getRequestDispatcher(MODIFICA);
           view.forward(req,resp);
       }




    }

    private void inserisciRivista(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        RequestDispatcher view;


        String titolo=req.getParameter("titoloR");
        String editore=req.getParameter("editoreR");
        String autore=req.getParameter("autoreR");
        String lingua=req.getParameter("linguaR");
        String categoria=req.getParameter("categorieR");
        String desc=req.getParameter("descR");
        LocalDate data= LocalDate.parse(req.getParameter("dataR"));
        String copie=req.getParameter("copieR");
        String disp=req.getParameter("dispR");
        String prezzo=req.getParameter("prezzoR");

        rB.setTitoloB(titolo);
        rB.setEditoreB(editore);
        rB.setAutoreB(autore);
        rB.setLinguaB(lingua);
        rB.setTipologiaB(categoria);
        rB.setDescrizioneB(desc);
        rB.setDataB(Date.valueOf(data));
        rB.setCopieRimB(Integer.parseInt(copie));
        rB.setDispB(Integer.parseInt(disp));
        rB.setPrezzoB(Float.parseFloat(prezzo));

        req.setAttribute(BEANS,sB);

        if(rB.getTitoloB()!=null || rB.getDataB()!=null) {


            r.setTitolo(rB.getTitoloB());
            r.setEditore(rB.getEditoreB());
            r.setAutore(rB.getAutoreB());
            r.setLingua(rB.getLinguaB());
            r.setCategoria(rB.getTipologiaB());
            r.setDescrizione(rB.getDescrizioneB());
            r.setDataPubb(rB.getDataB().toLocalDate());
            r.setCopieRim(rB.getCopieRimB());
            r.setDisp(rB.getDispB());
            r.setPrezzo(rB.getPrezzoB());
            if(rD.creaRivista(r))
            {
                view=getServletContext().getRequestDispatcher(GESTIONE);
                view.forward(req,resp);
            }
            else {
                view=getServletContext().getRequestDispatcher(MODIFICA);
                view.forward(req,resp);
            }
        }

    }

    private void inserisciLibro(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
       RequestDispatcher view;


        String titolo=req.getParameter("titoloL");
        String isbn=req.getParameter("codiceL");
        String editore=req.getParameter("editore");
        String autore=req.getParameter("autoreL");
        String lingua=req.getParameter("linguaL");
        String categoria=req.getParameter("categorieI");
        String desc=req.getParameter("desc");
        LocalDate data= LocalDate.parse(req.getParameter("dataL"));
        String rec=req.getParameter("rec");
        String pagine=req.getParameter("pagineL");
        String copie=req.getParameter("copieL");
        String disp=req.getParameter("dispL");
        String prezzo=req.getParameter("prezzoL");

        lB.setTitoloB(titolo);
        lB.setCodIsbnB(isbn);
        lB.setEditoreB(editore);
        lB.setAutoreB(autore);
        lB.setLinguaB(lingua);
        lB.setCategoriaB(categoria);
        lB.setDescB(desc);
        lB.setDateB(Date.valueOf(data));
        lB.setRecensioneB(rec);
        lB.setNumeroPagineB(Integer.parseInt(pagine));
        lB.setNrCopieB(Integer.parseInt(copie));
        lB.setDisponibilitaB(Integer.parseInt(disp));
        lB.setPrezzoB(Float.parseFloat(prezzo));


        if(lB.getCodIsbnB()!=null || lB.getDateB()!=null) {


            l.setTitolo(lB.getTitoloB());
            l.setCodIsbn(lB.getCodIsbnB());
            l.setEditore(lB.getEditoreB());
            l.setAutore(lB.getAutoreB());
            l.setLingua(lB.getLinguaB());
            l.setCategoria(lB.getCategoriaB());
            l.setDesc(lB.getDescB());
            l.setDataPubb(lB.getDateB().toLocalDate());
            l.setRecensione(lB.getRecensioneB());
            l.setNrPagine(lB.getNumeroPagineB());
            l.setNrCopie(lB.getNrCopieB());
            l.setDisponibilita(lB.getDisponibilitaB());
            l.setPrezzo(lB.getPrezzoB());
            req.setAttribute(BEANS,sB);
            if(lD.inserisciLibro(l))
            {
                view=getServletContext().getRequestDispatcher(GESTIONE);
                view.forward(req,resp);
            }
            else {
                view=getServletContext().getRequestDispatcher(MODIFICA);
                view.forward(req,resp);
            }
        }




    }

   private void inserisciGiornale(HttpServletRequest req,HttpServletResponse resp) throws SQLException, ServletException, IOException {
       RequestDispatcher view;

       String titolo=req.getParameter("titoloG");
       String editore=req.getParameter("editoreG");
       String lingua=req.getParameter("linguaG");
       String categoria=req.getParameter("categorieG");
       LocalDate data= LocalDate.parse(req.getParameter("dataG"));
       String copie=req.getParameter("copieG");
       String disp=req.getParameter("dispG");
       String prezzo=req.getParameter("prezzoG");
       if(titolo!=null && !titolo.isEmpty())
       {
           gB.setTitoloB(titolo);
           gB.setEditoreB(editore);
           gB.setLinguaB(lingua);
           gB.setTipologiaB(categoria);
           gB.setDataB(Date.valueOf(data));
           gB.setCopieRimanentiB(Integer.parseInt(copie));
           gB.setDisponibilitaB(Integer.parseInt(disp));
           gB.setPrezzoB(Float.parseFloat(prezzo));
           g.setTitolo(gB.getTitoloB());
           g.setEditore(gB.getEditoreB());
           g.setLingua(gB.getLinguaB());
           g.setCategoria(gB.getTipologiaB());
           g.setDataPubb(gB.getDataB().toLocalDate());
           g.setCopieRimanenti(gB.getCopieRimanentiB());
           g.setDisponibilita(gB.getDisponibilitaB());
           g.setPrezzo(gB.getPrezzoB());

           req.setAttribute(BEANS,sB);
               if(gD.creaGiornale(g))
               {
                   view=getServletContext().getRequestDispatcher(GESTIONE);
                   view.forward(req,resp);
               }
               else {
                   view=getServletContext().getRequestDispatcher(MODIFICA);
                   view.forward(req,resp);
               }



       }
   }
}
