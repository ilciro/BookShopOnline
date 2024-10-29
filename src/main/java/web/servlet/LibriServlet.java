package web.servlet;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

import laptop.database.LibroDao;
import laptop.model.raccolta.Libro;
import web.bean.LibroBean;
import web.bean.SystemBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LibriServlet")
public class LibriServlet extends HttpServlet implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final transient LibroBean lB=new LibroBean();
    private static final  String LIBRI="/libri.jsp";
    private final transient LibroDao lD=new LibroDao();
    private final transient Libro l=new Libro();
    private static final String BEANL="beanL";
    private final transient SystemBean sB=SystemBean.getInstance();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String i=req.getParameter("procedi");
        String g=req.getParameter("genera lista");
        String a=req.getParameter("annulla");
        String id=req.getParameter("idOgg");

       try {


           if (g != null && g.equals("genera lista")) {


               lB.setElencoLibriB(lD.getLibri());
               req.setAttribute(BEANL, lB);
               RequestDispatcher view = getServletContext().getRequestDispatcher(LIBRI);
               view.forward(req, resp);


           }
           if (i != null && i.equals("procedi")) {
               sB.setTypeAsBook();

               int idO = Integer.parseInt(id);
               if ((idO >= 1) && (idO <= lD.getLibri().size())) {


                   lB.setIdB(Integer.parseInt(id));
                   l.setId(lB.getIdB());
                   lB.setTitoloB(lD.getLibroByIdTitoloAutoreLibro(l).get(0).getTitolo());
                   SystemBean.getInstance().setIdB(lB.getIdB());
                   SystemBean.getInstance().setTitoloB(lB.getTitoloB());
                   req.setAttribute(BEANL, lB);
                   req.setAttribute("bean1", SystemBean.getInstance());

                   RequestDispatcher view = getServletContext().getRequestDispatcher("/acquista.jsp");
                   view.forward(req, resp);
               }


           }
           if (a != null && a.equals("indietro")) {
               RequestDispatcher view = getServletContext().getRequestDispatcher("/index.jsp");
               view.forward(req, resp);
           }
       }catch (ServletException |IOException e)
       {
           RequestDispatcher view = getServletContext().getRequestDispatcher(LIBRI);
           view.forward(req, resp);
       }


    }


}