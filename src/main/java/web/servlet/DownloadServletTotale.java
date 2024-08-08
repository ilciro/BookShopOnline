package web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.database.*;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import web.bean.DownloadBean;
import web.bean.SystemBean;

import java.io.*;
import java.sql.SQLException;
import java.util.ResourceBundle;

@WebServlet("/DownloadServletTotale")
public class DownloadServletTotale extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final DownloadBean dB=new DownloadBean();
    private static final SystemBean sB= SystemBean.getInstance();
    private static final Libro l=new Libro();
    private static final PagamentoDao pD=new PagamentoDao();
    private  final LibroDao lD=new LibroDao();
    private static final ContrassegnoDao fDao=new ContrassegnoDao();
    private static final ResourceBundle rbTitoli=ResourceBundle.getBundle("configurations/titles");
    private static final String INDEX="/index.jsp";
    private static final Giornale g=new Giornale();
    private static final GiornaleDao gD=new GiornaleDao();
    private static final Rivista r=new Rivista();
    private  final RivistaDao rD=new RivistaDao();
    private static final String LIBRO="libro";
    private static final String GIORNALE="giornale";
    private static final String RIVISTA="rivista";
    public DownloadServletTotale() throws IOException {
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String invia = req.getParameter("downloadB");
        String annulla = req.getParameter("annullaB");
        String hp = req.getParameter("homePage");
        RequestDispatcher view=null;

        try{
            if(invia!=null && invia.equals("scarica e leggi"))
            {
                switch (sB.getTypeB())
                {
                    case LIBRO:
                        switch (sB.getIdB()) {
                            case 1 -> dB.setTitoloB(rbTitoli.getString("titolo1"));
                            case 2 -> dB.setTitoloB(rbTitoli.getString("titolo2"));
                            case 3 -> dB.setTitoloB(rbTitoli.getString("titolo3"));
                            case 4 -> dB.setTitoloB(rbTitoli.getString("titolo4"));
                            case 5 -> dB.setTitoloB(rbTitoli.getString("titolo5"));
                            case 7 -> dB.setTitoloB(rbTitoli.getString("titolo7"));
                            default -> dB.setTitoloB(rbTitoli.getString("titolo12"));
                        }
                        performTask(req,resp,dB.getTitoloB());

                    case GIORNALE:
                        dB.setTitoloB(rbTitoli.getString("titolo13"));
                        performTask(req,resp,dB.getTitoloB());
                    case RIVISTA:
                        dB.setTitoloB(rbTitoli.getString("titolo15"));
                        performTask(req,resp,dB.getTitoloB());
                }
                view = getServletContext().getRequestDispatcher(INDEX);
                view.forward(req, resp);
            }
            if (annulla != null && annulla.equals("annulla")) {
                switch (sB.getTypeB())
                {
                    case LIBRO:
                        annullaLibro(req,resp,view);
                    case GIORNALE:
                        annullaGiornale(req,resp,view);

                    case RIVISTA:
                        annullaRivista(req,resp,view);
                }

            }
            if (hp != null && hp.equals("home page")) {
                view = getServletContext().getRequestDispatcher(INDEX);
                view.forward(req, resp);
            }

        }catch(SQLException e) {
            req.setAttribute("bean", dB);
            view = getServletContext().getRequestDispatcher(INDEX);
            view.forward(req, resp);


        }



    }
    private void performTask(HttpServletRequest request, HttpServletResponse response, String titoloB) throws ServletException,
            IOException {

        String pdfFileName = "libri/"+titoloB;
        String contextPath = getServletContext().getRealPath(File.separator);
        File pdfFile = new File(contextPath + pdfFileName);

        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
        response.setContentLength((int) pdfFile.length());

        FileInputStream fileInputStream = new FileInputStream(pdfFile);
        OutputStream responseOutputStream = response.getOutputStream();
        int bytes;
        while ((bytes = fileInputStream.read()) != -1) {
            responseOutputStream.write(bytes);
        }


    }private void annullaLibro(HttpServletRequest req, HttpServletResponse resp,RequestDispatcher view ) throws SQLException, ServletException, IOException {
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
    private void annullaGiornale(HttpServletRequest request, HttpServletResponse response,RequestDispatcher view) throws SQLException, ServletException, IOException {
        boolean statusF;
        boolean statusP;

        String metodoP = sB.getMetodoPB();

        int idF = fDao.retUltimoOrdineF(); //ultimo elemento (preso con count)
        statusF = fDao.annullaOrdineF(idF);

        int idP = pD.retUltimoOrdinePag();
        statusP = pD.annullaOrdinePag(idP);


        if (statusF && statusP && metodoP.equals("cash")) {

            g.setId(sB.getIdB());
            gD.aggiornaDisponibilita(g);


            request.setAttribute("bean", dB);
            view = getServletContext().getRequestDispatcher(INDEX);
            view.forward(request, response);


        }
        if (statusP && metodoP.equals("cCredito")) {

            g.setId(sB.getIdB());
            gD.aggiornaDisponibilita(g);
            request.setAttribute("bean", dB);
            view = getServletContext().getRequestDispatcher(INDEX);
            view.forward(request, response);


        }
    }
    private void annullaRivista(HttpServletRequest request, HttpServletResponse response,RequestDispatcher view) throws SQLException, ServletException, IOException {
        boolean statusF;
        boolean statusP;

        String metodoP=sB.getMetodoPB();

        int idF=fDao.retUltimoOrdineF(); //ultimo elemento (preso con count)
        statusF=fDao.annullaOrdineF(idF);

        int idP=pD.retUltimoOrdinePag();
        statusP=pD.annullaOrdinePag(idP);


        if(statusF && statusP && metodoP.equals("cash"))
        {

            r.setId(sB.getIdB());
            rD.aggiornaDisponibilita(r);


            request.setAttribute("bean",dB);
            view = getServletContext().getRequestDispatcher(INDEX);
            view.forward(request,response);


        }
        if( statusP && metodoP.equals("cCredito"))
        {

            r.setId(sB.getIdB());
            rD.aggiornaDisponibilita(r);
            request.setAttribute("bean",dB);
            view = getServletContext().getRequestDispatcher(INDEX);
            view.forward(request,response);




        }
    }

}
