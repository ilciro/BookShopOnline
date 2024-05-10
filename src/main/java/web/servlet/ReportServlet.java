package web.servlet;


import jakarta.servlet.RequestDispatcher;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.database.*;

import web.bean.TextAreaBean;

import java.io.*;


import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet("/ReportServlet")

public class ReportServlet extends HttpServlet {



    private static final TextAreaBean tAB = new TextAreaBean();



    private static final String LIBRO="libro";
    private static final String GIORNALE="giornale";
    private static final String RIVISTA="rivista";
    private static final String UTENTE="utente";
    private static final String TOTALE="totale";
    private static final String RACCOLTA="raccolta";


    public ReportServlet() throws IOException {
        Logger.getLogger("initialize ").log(Level.INFO, "costruttore");

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String totale=req.getParameter("buttonT");
        String libro=req.getParameter("buttonL");
        String giornale=req.getParameter("buttonG");
        String rivista=req.getParameter("buttonR");
        String raccolta=req.getParameter("raccoltaB");
        String indietro = req.getParameter("buttonI");

        RequestDispatcher view;
        GenerateDaoReportClass gRC=new GenerateDaoReportClass();

    try{
            if(totale!=null && totale.equals(TOTALE))
            {

                tAB.setScriviB(gRC.getReportView(LIBRO) +
                        "\n"+
                        gRC.getReportView(GIORNALE) +
                        "\n"+
                        gRC.getReportView(RIVISTA) +
                        "\n"+
                        gRC.getReportView(UTENTE));
            }
            if(libro!=null && libro.equals(LIBRO))
                tAB.setScriviB(gRC.getReportView(LIBRO));
            if(giornale!=null && giornale.equals(GIORNALE))
                tAB.setScriviB(gRC.getReportView(GIORNALE));
            if(rivista!=null && rivista.equals(RIVISTA))
                tAB.setScriviB(gRC.getReportView(RIVISTA));
            if(raccolta!=null && raccolta.equals(RACCOLTA))
            {
                tAB.setScriviB(gRC.getReportView(LIBRO)
                        +"\n"
                        + gRC.getReportView(GIORNALE)
                        +"\n"
                        + gRC.getReportView(RIVISTA));
            }
            if (indietro!=null && indietro.equals("indietro")) {
                view = getServletContext().getRequestDispatcher("/admin.jsp");
                view.forward(req, resp);
            }
            req.setAttribute("beanTA", tAB);
            view = getServletContext().getRequestDispatcher("/report.jsp");
            view.forward(req, resp);

        } catch (SQLException ex) {
        Logger.getLogger("exception").log(Level.SEVERE,"eccezione ottenuta", ex);

    }
    }




}




