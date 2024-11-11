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
import web.bean.ModificaOggettoBean;
import web.bean.ReportBean;
import web.bean.SystemBean;

import java.io.IOException;
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
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tipo=req.getParameter("tipoOgg");
        String genera=req.getParameter("genera");
        String inserisci=req.getParameter("inserisci");
        RequestDispatcher view;

        if(genera!=null && genera.equals("genera"))
        {
            switch (tipo)
            {
                case "libro"->
                {
                    rB.setElencoReportB(lD.getLibri());
                    sB.setTypeAsBook();
                }
                case "giornale"->{
                    rB.setElencoReportB(gD.getGiornali());
                    sB.setTypeAsDaily();
                }
                case "rivista"->{
                    rB.setElencoReportB(rD.getRiviste());
                    sB.setTypeAsMagazine();
                }
                default -> Logger.getLogger("genera").log(Level.SEVERE," type is wrong !!");
            }
            req.setAttribute("beanS",sB);
            req.setAttribute("beanRepo",rB);
            view=getServletContext().getRequestDispatcher("/gestioneOggetti.jsp");
            view.forward(req,resp);
        }
        if (inserisci!=null && inserisci.equals("inserisci"))
        {
            sB.setTypeB(tipo);
            sB.setTypeOfModif("inserisci");
            switch (sB.getTypeB()) {
                case "libro" -> mOB.setListaCategorieB(categorieL());
                case "giornale" -> mOB.setListaCategorieB(categorieG());
                case "rivista" -> mOB.setListaCategorieB(categorieR());
                default -> Logger.getLogger("insierisci").log(Level.SEVERE," type is wrong !!");
            }
            req.setAttribute("beanS",sB);
            req.setAttribute("beanMob",mOB);
            view=getServletContext().getRequestDispatcher("/aggiungiModificaOggetto.jsp");
            view.forward(req,resp);
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
}
