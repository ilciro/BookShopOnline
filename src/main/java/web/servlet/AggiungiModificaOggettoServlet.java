package web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.bean.LibroBean;
import web.bean.SystemBean;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/AggiungiModificaOggettoServlet")
public class AggiungiModificaOggettoServlet extends HttpServlet {
    private static final SystemBean sB=SystemBean.getInstance();
    private static final LibroBean lB=new LibroBean();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String inserisci=req.getParameter("inserisci");
        String indietro=req.getParameter("indietro");
        RequestDispatcher view;

        if(inserisci!=null && inserisci.equals("inserisci"))
        {
            switch (sB.getTypeB()){
                case "libro"->inserisciLibro(req,resp);
                //case "giornale"->inserisciGiornale();
               // case "rivista"->inserisciRivista();
                default -> Logger.getLogger("inserisci").log(Level.SEVERE," type is wrong !!");
            }
        }

        if(indietro!=null && indietro.equals("indietro"))
        {
            view=getServletContext().getRequestDispatcher("/gestioneOggetti.jsp");
            view.forward(req,resp);
        }
    }

    private boolean inserisciLibro(HttpServletRequest req,HttpServletResponse resp) {
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


        return true;




    }
}
