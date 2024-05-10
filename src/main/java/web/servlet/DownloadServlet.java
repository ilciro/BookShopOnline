package web.servlet;

import java.io.*;
import java.sql.SQLException;
import java.util.ResourceBundle;

import web.bean.DownloadBean;

import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.database.ContrassegnoDao;
import laptop.database.LibroDao;
import laptop.database.PagamentoDao;
import laptop.model.raccolta.Libro;

@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet{

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    private static final DownloadBean dB=new DownloadBean();
    private static final SystemBean sB=SystemBean.getInstance();
    private static final Libro l=new Libro();
    private static final PagamentoDao pD=new PagamentoDao();
    private  final LibroDao lD=new LibroDao();
    private static final ContrassegnoDao fDao=new ContrassegnoDao();
    private static final ResourceBundle rbTitoli=ResourceBundle.getBundle("configurations/titles");




    private static final String INDEX="/index.jsp";

    public DownloadServlet() throws IOException {
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String invia = req.getParameter("downloadB");
        String annulla = req.getParameter("annullaB");
        String hp=req.getParameter("homePage");
        RequestDispatcher view;


        try {
            if (invia != null && invia.equals("scarica e leggi")) {


                switch (sB.getIdB())
                {
                    case 1-> dB.setTitoloB(rbTitoli.getString("titolo1"));
                    case 2-> dB.setTitoloB(rbTitoli.getString("titolo2"));
                    case 3-> dB.setTitoloB(rbTitoli.getString("titolo3"));
                    case 4-> dB.setTitoloB(rbTitoli.getString("titolo4"));
                    case 5-> dB.setTitoloB(rbTitoli.getString("titolo5"));
                    case 6-> dB.setTitoloB(rbTitoli.getString("titolo6"));
                    case 7-> dB.setTitoloB(rbTitoli.getString("titolo7"));
                    case 8-> dB.setTitoloB(rbTitoli.getString("titolo8"));
                    case 9-> dB.setTitoloB(rbTitoli.getString("titolo9"));
                    case 10-> dB.setTitoloB(rbTitoli.getString("titolo10"));
                    case 11->dB.setTitoloB(rbTitoli.getString("titolo11"));
                    default -> dB.setTitoloB(rbTitoli.getString("titolo12"));
                }
                req.setAttribute("beanS",sB);
                req.setAttribute("beanD",dB);

                view=getServletContext().getRequestDispatcher("/download.jsp");
                view.forward(req,resp);
            }
            if (annulla != null && annulla.equals("annulla")) {

                //split
                boolean statusF;
                boolean statusP;

                String metodoP = sB.getMetodoPB();

                int idF = fDao.retUltimoOrdineF(); //ultimo elemento (preso con count)
                statusF = fDao.annullaOrdineF(idF);

                int idP = pD.retUltimoOrdinePag();
                statusP = pD.annullaOrdinePag(idP);


                if (statusF && statusP && metodoP.equals("cash")) {

                    l.setId(sB.getIdB());
                    lD.aggiornaDisponibilita(l);


                    req.setAttribute("bean", dB);
                     view = getServletContext().getRequestDispatcher(INDEX);
                    view.forward(req, resp);


                }
                if (statusP && metodoP.equals("cCredito")) {

                    l.setId(sB.getIdB());
                    lD.aggiornaDisponibilita(l);
                    req.setAttribute("bean", dB);
                     view = getServletContext().getRequestDispatcher(INDEX);
                    view.forward(req, resp);


                }


            }
            if(hp!=null && hp.equals("home page"))
            {
                view=getServletContext().getRequestDispatcher(INDEX);
                view.forward(req,resp);
            }


        } catch (SQLException  e) {
            req.setAttribute("bean", dB);
            view = getServletContext().getRequestDispatcher(INDEX);
            view.forward(req, resp);


        }
    }




}












