package web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import laptop.database.ContrassegnoDao;
import laptop.database.PagamentoDao;
import laptop.database.RivistaDao;
import laptop.model.raccolta.Rivista;
import web.bean.DownloadBean;
import web.bean.SystemBean;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.ResourceBundle;

@WebServlet("/DownloadServletR")
public class DownloadServletR extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final DownloadBean dB=new DownloadBean();
    private static final SystemBean sB=SystemBean.getInstance();
    private static final Rivista r=new Rivista();
    private static final PagamentoDao pD=new PagamentoDao();
    private  final RivistaDao rD=new RivistaDao();
    private static final ContrassegnoDao fDao=new ContrassegnoDao();
    private static final String INDEX="/index.jsp";
    private static final ResourceBundle rbTitoli=ResourceBundle.getBundle("configurations/titles");


    public DownloadServletR() throws IOException {
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String invia=request.getParameter("downloadB");
        String annulla=request.getParameter("annullaB");
        String hp=request.getParameter("homePage");

        RequestDispatcher view;

        try {
            if(invia!=null && invia.equals("scarica e leggi") )

            {

                dB.setTitoloB(rbTitoli.getString("titolo15"));

                request.setAttribute("beanD",dB);
                request.setAttribute("beanS",sB);

                view = getServletContext().getRequestDispatcher("/download.jsp");
                view.forward(request,response);
            }
            if(annulla!=null && annulla.equals("annulla"))
            {

                //split
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
            if(hp!=null && hp.equals("home page"))
            {
                view=getServletContext().getRequestDispatcher(INDEX);
                view.forward(request,response);
            }


        }catch(SQLException  | IOException e)
        {
            request.setAttribute("bean",dB);
             view = getServletContext().getRequestDispatcher(INDEX);
            view.forward(request,response);

        }


    }
}
