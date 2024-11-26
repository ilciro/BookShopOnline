package web.servlet;

import java.io.IOException;
import java.io.Serial;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.DocumentException;

import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Rivista;
import web.bean.DownloadBean;

import web.bean.FatturaBean;
import web.bean.PagamentoBean;
import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import laptop.model.raccolta.Libro;

@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet  {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    private static final  DownloadBean dB=new DownloadBean();
    private static final SystemBean sB=SystemBean.getInstance();
    private static final Libro l=new Libro();
    private static final FatturaBean fBean=new FatturaBean();
    private static final PagamentoBean pBean=new PagamentoBean();
    private static final Giornale g=new Giornale();
    private static final Rivista r=new Rivista();



    private static final String INDEX="/index.jsp";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String invia=req.getParameter("downloadB");
        String annulla=req.getParameter("annullaB");

        try {
            if(invia!=null && invia.equals("scarica e leggi") )

            {
                dB.setIdB(sB.getIdB());
                dB.setTitoloB(sB.getTitoloB());

                switch (sB.getTypeB())
                {
                    case "libro"->{
                        l.setId(sB.getIdB());
                        l.scarica(sB.getIdB());
                        l.leggi(l.getId());

                    }
                    case "giornale"->
                    {
                        g.setId(sB.getIdB());
                        g.scarica(sB.getIdB());
                        g.leggi(g.getId());
                    }
                    case "rivista"->
                    {
                        r.setId(sB.getIdB());
                        r.scarica(sB.getIdB());
                        r.leggi(r.getId());
                    }
                    default -> Logger.getLogger(" scarica e leggi").log(Level.SEVERE, " error !! could not open file");
                }




                req.setAttribute("bean",dB);
                RequestDispatcher view = getServletContext().getRequestDispatcher(INDEX);
                view.forward(req,resp);
            }
            if(annulla!=null && annulla.equals("annulla"))
            {


                req.setAttribute("fBean",fBean);
                req.setAttribute("pBean",pBean);
                req.setAttribute("bean1",SystemBean.getInstance());

                RequestDispatcher view = getServletContext().getRequestDispatcher("/annullaPagamento.jsp");
                view.forward(req,resp);



            }


        }catch( DocumentException |URISyntaxException  e)
        {
            req.setAttribute("bean",dB);
            RequestDispatcher view = getServletContext().getRequestDispatcher(INDEX);
            view.forward(req,resp);

        }

    }


}







